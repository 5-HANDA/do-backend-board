package hobbiedo.board.vo.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 업로드 요청")
public class BoardUploadRequestVo {

	private String content;
	private List<String> imageUrls;

	public BoardUploadRequestVo(String content, List<String> imageUrls) {
		this.content = content;
		this.imageUrls = imageUrls;
	}
}
