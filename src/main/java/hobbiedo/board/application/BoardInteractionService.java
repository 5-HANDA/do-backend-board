package hobbiedo.board.application;

import hobbiedo.board.dto.request.CommentUploadRequestDto;

public interface BoardInteractionService {

	void createComment(Long boardId, String uuid, CommentUploadRequestDto commentUploadRequestDto);
}
