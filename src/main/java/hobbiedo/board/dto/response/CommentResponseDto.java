package hobbiedo.board.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

	private Long commentId; // 댓글 번호
	private String writerUuid; // 작성자 uuid
	private String content; // 댓글 내용
	private Boolean isInCrew; // 소모임 회원 여부
	private LocalDateTime createdAt; // 작성일
}
