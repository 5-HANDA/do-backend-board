package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.board.domain.Board;
import hobbiedo.board.domain.Comment;
import hobbiedo.board.domain.Likes;
import hobbiedo.board.dto.request.CommentUploadRequestDto;
import hobbiedo.board.dto.response.CommentListResponseDto;
import hobbiedo.board.dto.response.CommentResponseDto;
import hobbiedo.board.dto.response.LikeStatusDto;
import hobbiedo.board.infrastructure.BoardRepository;
import hobbiedo.board.infrastructure.CommentRepository;
import hobbiedo.board.infrastructure.LikesRepository;
import hobbiedo.board.kafka.application.KafkaProducerService;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.board.kafka.dto.BoardLikeUpdateDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
import hobbiedo.global.api.exception.handler.BoardExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardInteractionServiceImpl implements BoardInteractionService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	private final LikesRepository likesRepository;

	// kafka producer service 추가
	private final KafkaProducerService kafkaProducerService;

	/**
	 * 댓글 생성
	 * @param boardId
	 * @param uuid
	 * @param commentUploadRequestDto
	 */
	@Override
	@Transactional
	public void createComment(Long boardId, String uuid,
		CommentUploadRequestDto commentUploadRequestDto) {

		String content = commentUploadRequestDto.getContent();

		Boolean isInCrew = commentUploadRequestDto.getIsInCrew();

		// 댓글 내용이 비어있을 경우, isInCrew 가 비어있을 경우 예외 처리
		if (content == null || content.trim().isEmpty()) {

			throw new BoardExceptionHandler(CREATE_COMMENT_CONTENT_EMPTY);
		} else if (isInCrew == null) {

			throw new BoardExceptionHandler(CREATE_COMMENT_IS_IN_CREW_EMPTY);
		}

		// 게시글이 존재하지 않을 경우 예외 처리
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		Comment comment = Comment.builder()
			.board(board)
			.writerUuid(uuid)
			.content(commentUploadRequestDto.getContent())
			.isInCrew(commentUploadRequestDto.getIsInCrew())
			.build();

		commentRepository.save(comment);

		// 댓글 생성 시 통계 테이블에 댓글 수 증가 이벤트 메시지 전송
		BoardCommentUpdateDto eventDto = BoardCommentUpdateDto.builder()
			.boardId(boardId)
			.build();

		kafkaProducerService.sendUpdateCommentCountMessage(eventDto);
	}

	/**
	 * 댓글 리스트 조회
	 * @param boardId
	 * @param page
	 * @return
	 */
	@Override
	public CommentListResponseDto getCommentList(Long boardId, Pageable page) {

		// 게시글이 존재하는지 확인
		boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 게시글에 속한 댓글 리스트 조회
		Page<Comment> comments = commentRepository.findByBoardId(boardId, page);

		// 댓글 리스트가 비어있어도 예외 처리하지 않음
		List<CommentResponseDto> commentResponseDtoList = comments.stream()
			.sorted(Comparator.comparing(Comment::getCreatedAt))
			.map(comment -> CommentResponseDto.builder()
				.commentId(comment.getId())
				.writerUuid(comment.getWriterUuid())
				.content(comment.getContent())
				.isInCrew(comment.getIsInCrew())
				.createdAt(comment.getCreatedAt())
				.build())
			.toList();

		return CommentListResponseDto.commentDtoToCommentListResponseDto(comments.isLast(),
			commentResponseDtoList);
	}

	/**
	 * 댓글 삭제
	 * @param commentId
	 * @param uuid
	 */
	@Override
	@Transactional
	public void deleteComment(Long commentId, String uuid) {

		// 댓글이 존재하는지 확인
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_COMMENT_NOT_FOUND));

		// 댓글 작성자와 요청자가 다를 경우 예외 처리
		if (!comment.getWriterUuid().equals(uuid)) {

			throw new BoardExceptionHandler(DELETE_COMMENT_NOT_WRITER);
		}

		commentRepository.deleteById(commentId);

		// 댓글 삭제 시 통계 테이블에 댓글 수 감소 이벤트 메시지 전송
		BoardCommentUpdateDto eventDto = BoardCommentUpdateDto.builder()
			.boardId(comment.getBoard().getId())
			.build();

		kafkaProducerService.sendDeleteCommentCountMessage(eventDto);
	}

	/**
	 * 좋아요 생성
	 * @param boardId
	 * @param uuid
	 */
	@Override
	@Transactional
	public void createLike(Long boardId, String uuid) {

		// 게시글이 존재하는지 확인
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 좋아요가 이미 존재하는지 확인
		if (likesRepository.findByBoardIdAndUserUuid(boardId, uuid).isPresent()) {
			throw new BoardExceptionHandler(CREATE_LIKE_ALREADY_EXIST);
		}

		// 좋아요 생성
		Likes likes = Likes.builder()
			.board(board)
			.userUuid(uuid)
			.build();

		likesRepository.save(likes);

		// 좋아요 생성 시 통계 테이블에 좋아요 수 증가 이벤트 메시지 전송
		BoardLikeUpdateDto eventDto = BoardLikeUpdateDto.builder()
			.boardId(boardId)
			.build();

		kafkaProducerService.sendUpdateLikeCountMessage(eventDto);
	}

	/**
	 * 좋아요 여부 조회
	 * @param boardId
	 * @param uuid
	 * @return
	 */
	@Override
	public LikeStatusDto getLikeStatus(Long boardId, String uuid) {

		// 게시글이 존재하는지 확인
		boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 좋아요가 존재하는지 확인
		boolean isLike = likesRepository.findByBoardIdAndUserUuid(boardId, uuid).isPresent();

		return LikeStatusDto.builder()
			.boardId(boardId)
			.liked(isLike)
			.userUuid(uuid)
			.build();
	}

	/**
	 * 좋아요 취소
	 * @param boardId
	 * @param uuid
	 */
	@Override
	@Transactional
	public void deleteLike(Long boardId, String uuid) {

		// 게시글이 존재하는지 확인
		boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 좋아요가 존재하는지 확인, 존재하지 않을 경우 예외 처리
		Likes likes = likesRepository.findByBoardIdAndUserUuid(boardId, uuid)
			.orElseThrow(() -> new BoardExceptionHandler(DELETE_LIKE_NOT_EXIST));

		likesRepository.delete(likes);

		// 좋아요 삭제 시 통계 테이블에 좋아요 수 감소 이벤트 메시지 전송
		BoardLikeUpdateDto eventDto = BoardLikeUpdateDto.builder()
			.boardId(boardId)
			.build();

		kafkaProducerService.sendDeleteLikeCountMessage(eventDto);
	}

	/**
	 * 게시글 고정
	 * @param boardId
	 */
	@Override
	@Transactional
	public void pinPost(Long boardId) {

		// 게시글이 존재하는지 확인
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 게시글이 이미 고정되어 있는지 확인
		if (board.isPinned()) {
			throw new BoardExceptionHandler(PIN_POST_ALREADY_EXIST);
		}

		// 기존에 고정된 게시글이 있는지 확인
		Board pinnedBoard = boardRepository.findByIsPinnedTrueAndCrewId(board.getCrewId())
			.orElse(null);

		log.info("pinnedBoard {}", pinnedBoard);

		// 기존에 고정된 게시글이 있는 경우 고정 해제하는 메서드 호출
		if (pinnedBoard != null) {
			unpinPostMethod(pinnedBoard);

			// 게시글의 고정 해제 이벤트 메시지 전송
			BoardUnPinEventDto unpinEventDto = BoardUnPinEventDto.builder()
				.boardId(pinnedBoard.getId())
				.build();

			kafkaProducerService.sendUnpinTableMessage(unpinEventDto);
		}

		boardRepository.save(

			Board.builder()
				.id(board.getId())
				.content(board.getContent())
				.writerUuid(board.getWriterUuid())
				.crewId(board.getCrewId())
				.isPinned(true)
				.isUpdated(board.isUpdated())
				.build()
		);

		// 게시글의 고정 이벤트 메시지 전송
		BoardPinEventDto eventDto = BoardPinEventDto.builder()
			.boardId(boardId)
			.build();

		kafkaProducerService.sendPinTableMessage(eventDto);
	}

	/**
	 * 게시글 고정 해제
	 * @param boardId
	 */
	@Override
	@Transactional
	public void unpinPost(Long boardId) {

		// 게시글이 존재하는지 확인
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		// 게시글이 이미 고정해제 되어 있는지 확인
		if (!board.isPinned()) {
			throw new BoardExceptionHandler(UNPIN_POST_NOT_EXIST);
		}

		// 게시글 고정 해제하는 메서드 호출
		unpinPostMethod(board);

		// 게시글의 고정 해제 이벤트 메시지 전송
		BoardUnPinEventDto unpinEventDto = BoardUnPinEventDto.builder()
			.boardId(boardId)
			.build();

		kafkaProducerService.sendUnpinTableMessage(unpinEventDto);
	}

	// 게시글 고정 해제 메서드
	private void unpinPostMethod(Board board) {

		boardRepository.save(

			Board.builder()
				.id(board.getId())
				.content(board.getContent())
				.writerUuid(board.getWriterUuid())
				.crewId(board.getCrewId())
				.isPinned(false)
				.isUpdated(board.isUpdated())
				.build()
		);
	}

}
