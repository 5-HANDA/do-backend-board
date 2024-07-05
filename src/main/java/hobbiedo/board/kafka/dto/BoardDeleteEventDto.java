package hobbiedo.board.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeleteEventDto {

	private Long boardId; // 게시글 번호
	private Long crewId; // 크루 번호
}
