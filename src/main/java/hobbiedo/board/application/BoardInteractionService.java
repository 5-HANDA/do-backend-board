package hobbiedo.board.application;

import org.springframework.data.domain.Pageable;

import hobbiedo.board.dto.request.CommentUploadRequestDto;
import hobbiedo.board.dto.response.CommentListResponseDto;

public interface BoardInteractionService {

	void createComment(Long boardId, String uuid, CommentUploadRequestDto commentUploadRequestDto);

	CommentListResponseDto getCommentList(Long boardId, Pageable page);

	void deleteComment(Long commentId, String uuid);

	void createLike(Long boardId, String uuid);
}
