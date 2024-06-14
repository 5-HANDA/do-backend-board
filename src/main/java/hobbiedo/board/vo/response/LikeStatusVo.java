package hobbiedo.board.vo.response;

import hobbiedo.board.dto.response.LikeStatusDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 좋아요 상태 조회 응답")
public class LikeStatusVo {

	private Long boardId;
	private String userUuid;
	private Boolean isLiked;

	public LikeStatusVo(Long boardId, boolean liked, String userUuid) {
		this.boardId = boardId;
		this.isLiked = liked;
		this.userUuid = userUuid;
	}

	public static LikeStatusVo likeStatusToVo(LikeStatusDto likeStatusDto) {
		return new LikeStatusVo(
			likeStatusDto.getBoardId(),
			likeStatusDto.isLiked(),
			likeStatusDto.getUserUuid()
		);
	}
}
