package hobbiedo.board.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "댓글 업로드 요청")
public class CommentUploadRequestVo {

	private String content;

	public CommentUploadRequestVo(String content) {
		this.content = content;
	}
}
