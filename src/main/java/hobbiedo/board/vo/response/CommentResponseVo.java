package hobbiedo.board.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 댓글 조회 응답")
public class CommentResponseVo {

	private String writerUuid; // 작성자 uuid
	private String content; // 댓글 내용
	private Boolean isInCrew; // 소모임 회원 여부

	public CommentResponseVo(String writerUuid, String content, Boolean isInCrew) {
		this.writerUuid = writerUuid;
		this.content = content;
		this.isInCrew = isInCrew;
	}

}
