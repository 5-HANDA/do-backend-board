package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.board.domain.Board;
import hobbiedo.board.domain.BoardImage;
import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import hobbiedo.board.dto.response.BoardListResponseDto;
import hobbiedo.board.dto.response.BoardResponseDto;
import hobbiedo.board.infrastructure.BoardImageRepository;
import hobbiedo.board.infrastructure.BoardRepository;
import hobbiedo.board.infrastructure.CommentRepository;
import hobbiedo.global.api.exception.handler.BoardExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;
	private final CommentRepository commentRepository;

	/**
	 * 게시글 생성
	 * 이미지 url 리스트가 비어있을 경우 게시글만 생성
	 * @param crewId
	 * @param uuid
	 * @param boardUploadRequestDto
	 */
	@Override
	@Transactional
	public void createPostWithImages(Long crewId, String uuid,
		BoardUploadRequestDto boardUploadRequestDto) {

		Board createdBoard = createPost(crewId, uuid, boardUploadRequestDto);

		// 이미지 업로드 기능
		List<String> imageUrls = boardUploadRequestDto.getImageUrls();

		// 이미지 url 리스트가 5개를 초과할 경우 예외 처리
		if (imageUrls.size() > 5) {
			throw new BoardExceptionHandler(CREATE_POST_IMAGE_SIZE_EXCEED);
		}

		// 이미지 url 리스트가 null 이 아니고 비어있지 않을 경우 이미지 엔티티 생성
		if (imageUrls != null && !imageUrls.isEmpty()) {

			IntStream.range(0, imageUrls.size())
				.forEach(i -> {
					String imageUrl = imageUrls.get(i);

					boardImageRepository.save(
						BoardImage.builder()
							.board(createdBoard) // 게시글 엔티티를 설정
							.imageUrl(imageUrl) // URL 사용
							.orderIndex(i) // 리스트 순서를 이미지의 순서로 설정
							.build()
					);
				});
		}
	}

	/**`
	 * 게시글 조회
	 * 이미지 url 리스트를 생성하여 반환
	 * 이미지가 존재하지 않을 경우 null 반환
	 * @param boardId
	 * @return
	 */
	@Override
	public BoardDetailsResponseDto getPost(Long boardId) {

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		List<BoardImage> boardImages = boardImageRepository.findByBoardId(boardId);

		// 이미지가 존재할 경우 이미지 url 리스트를 생성
		List<String> imageUrls = null;

		if (boardImages != null && !boardImages.isEmpty()) {

			imageUrls = boardImages.stream()
				.map(BoardImage::getImageUrl)
				.toList();
		}

		return BoardDetailsResponseDto.builder()
			.boardId(board.getId())
			.content(board.getContent())
			.writerUuid(board.getWriterUuid())
			.pinned(board.isPinned())
			.createdAt(board.getCreatedAt())
			.updated(board.isUpdated())
			.imageUrls(imageUrls)
			.build();
	}

	/**
	 * 게시글 리스트 조회
	 * @param crewId
	 * @return
	 */
	@Override
	public BoardListResponseDto getPostList(Long crewId, Pageable page) {

		// 소모임에 속한 게시글 리스트 조회
		Page<Board> boards = boardRepository.findByCrewId(crewId, page);

		// 게시글 리스트가 비어있어도 예외 처리하지 않음
		List<BoardResponseDto> boardResponseDtoList = boards.stream()
			.filter(board -> !board.isPinned()) // pinned 가 false 인 게시글만 포함
			.sorted(Comparator.comparing(Board::getCreatedAt)
				.reversed()) // 최신순으로 정렬
			.map(board -> BoardResponseDto.builder()
				.boardId(board.getId())
				.pinned(board.isPinned())
				.build())
			.toList();

		return BoardListResponseDto.boardDtoToBoardListResponseDto(boards.isLast(),
			boardResponseDtoList);
	}

	/**
	 * 게시글 수정
	 * 이미지 url 리스트가 비어있을 경우 게시글만 수정
	 * @param boardId
	 * @param uuid
	 * @param boardUpdateRequestDto
	 */
	@Override
	@Transactional
	public void updatePostWithImages(Long boardId, String uuid,
		BoardUploadRequestDto boardUpdateRequestDto) {

		// 게시글 수정
		Board updateedBoard = updatePost(boardId, uuid, boardUpdateRequestDto);

		// 이미지 url 리스트가 5개를 초과할 경우 예외 처리
		List<String> imageUrls = boardUpdateRequestDto.getImageUrls();

		if (imageUrls.size() > 5) {
			throw new BoardExceptionHandler(CREATE_POST_IMAGE_SIZE_EXCEED);
		}

		// 기존 이미지가 있을 경우 삭제
		boardImageRepository.deleteByBoardId(boardId);

		// 새 이미지 추가
		if (imageUrls != null && !imageUrls.isEmpty()) {

			IntStream.range(0, imageUrls.size())
				.forEach(i -> {
					String imageUrl = imageUrls.get(i);

					boardImageRepository.save(
						BoardImage.builder()
							.board(updateedBoard)
							.imageUrl(imageUrl)
							.orderIndex(i)
							.build()
					);
				});
		}
	}

	/**
	 * 게시글 삭제
	 * @param boardId
	 * @param uuid
	 */
	@Override
	@Transactional
	public void deletePost(Long boardId, String uuid) {

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 작성자가 아닐 경우 예외 처리
		if (!board.getWriterUuid().equals(uuid)) {
			throw new BoardExceptionHandler(DELETE_POST_NOT_WRITER);
		}

		// // 게시글에 속한 이미지 삭제
		// boardImageRepository.deleteByBoardId(boardId);
		//
		// // 게시글 댓글 삭제
		// commentRepository.deleteByBoardId(boardId);

		// 게시글 삭제
		boardRepository.deleteById(boardId);
	}

	// 게시글 생성
	private Board createPost(Long crewId, String uuid,
		BoardUploadRequestDto boardUploadRequestDto) {

		String content = boardUploadRequestDto.getContent();

		// 게시글 내용이 비어있을 경우 예외 처리
		if (content == null || content.trim().isEmpty()) {

			throw new BoardExceptionHandler(CREATE_POST_CONTENT_EMPTY);
		}

		return boardRepository.save(
			Board.builder()
				.crewId(crewId)
				.writerUuid(uuid)
				.content(boardUploadRequestDto.getContent())
				.build()
		);
	}

	// 게시글 수정
	private Board updatePost(Long boardId, String uuid,
		BoardUploadRequestDto boardUpdateRequestDto) {

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 작성자가 아닐 경우 예외 처리
		if (!board.getWriterUuid().equals(uuid)) {
			throw new BoardExceptionHandler(UPDATE_POST_NOT_WRITER);
		}

		// 업데이트할 게시글 내용을 가져온다.
		String content = boardUpdateRequestDto.getContent();

		// 게시글 내용이 비어있을 경우 예외 처리
		if (content == null || content.trim().isEmpty()) {

			throw new BoardExceptionHandler(CREATE_POST_CONTENT_EMPTY);
		}

		return boardRepository.save(
			Board.builder()
				.id(board.getId())
				.content(boardUpdateRequestDto.getContent())
				.writerUuid(board.getWriterUuid())
				.crewId(board.getCrewId())
				.isPinned(board.isPinned())
				.isUpdated(true)
				.build()
		);
	}
}
