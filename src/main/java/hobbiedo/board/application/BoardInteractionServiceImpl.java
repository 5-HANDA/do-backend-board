package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.board.domain.Board;
import hobbiedo.board.domain.Comment;
import hobbiedo.board.dto.request.CommentUploadRequestDto;
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

		// 댓글 내용이 비어있을 경우 예외 처리
		if (content == null || content.trim().isEmpty()) {

			throw new BoardExceptionHandler(CREATE_COMMENT_CONTENT_EMPTY);
		}

		// 게시글이 존재하지 않을 경우 예외 처리
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardExceptionHandler(GET_POST_NOT_FOUND));

		Comment comment = Comment.builder()
			.board(board)
			.writerUuid(uuid)
			.content(commentUploadRequestDto.getContent())
			.build();

		commentRepository.save(comment);
	}
}
