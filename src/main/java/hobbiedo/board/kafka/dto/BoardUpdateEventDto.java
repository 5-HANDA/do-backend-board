package hobbiedo.board.kafka.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateEventDto {

	private Long boardId; // 게시글 번호
	private Long crewId; // 크루 번호
	private String content; // 내용
	private String writerUuid; // 작성자 uuid
	private boolean pinned; // 고정 여부
	private LocalDateTime createdAt; // 작성일
	private boolean updated; // 수정 여부
	private List<String> imageUrls; // 이미지 url 목록
}
