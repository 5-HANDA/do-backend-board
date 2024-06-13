package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.board.domain.Board;
import hobbiedo.board.domain.Comment;
import hobbiedo.board.dto.request.CommentUploadRequestDto;
import hobbiedo.board.dto.response.CommentListResponseDto;
import hobbiedo.board.dto.response.CommentResponseDto;
import hobbiedo.board.infrastructure.BoardRepository;
import hobbiedo.board.infrastructure.CommentRepository;
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
			.sorted(Comparator.comparing(Comment::getCreatedAt)
				.reversed()) // 최신순 정렬
			.map(comment -> CommentResponseDto.builder()
				.commentId(comment.getId())
				.writerUuid(comment.getWriterUuid())
				.content(comment.getContent())
				.isInCrew(comment.getIsInCrew())
				.createdAt(comment.getCreatedAt())
				.build())
			.collect(Collectors.toList());

		return CommentListResponseDto.commentDtoToCommentListResponseDto(comments.isLast(),
			commentResponseDtoList);
	}
}
