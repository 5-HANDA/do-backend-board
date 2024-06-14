package hobbiedo.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeStatusDto {

	private Long boardId;
	private boolean liked;
	private String userUuid;
}
