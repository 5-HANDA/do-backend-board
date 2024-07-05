package hobbiedo.board.vo.response;

import java.time.LocalDateTime;
import java.util.List;

import hobbiedo.board.dto.response.CommentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 댓글 조회 응답")
public class CommentResponseVo {

	private Long commentId; // 댓글 번호
	private String writerUuid; // 작성자 uuid
	private String content; // 댓글 내용
	private Boolean isInCrew; // 소모임 회원 여부
	private LocalDateTime createdAt; // 작성일

	public CommentResponseVo(Long commentId, String writerUuid, String content, Boolean isInCrew,
		LocalDateTime createdAt) {
		this.commentId = commentId;
		this.writerUuid = writerUuid;
		this.content = content;
		this.isInCrew = isInCrew;
		this.createdAt = createdAt;
	}

	public static List<CommentResponseVo> commentDtoToCommentListResponseVo(
		List<CommentResponseDto> commentResponseDtoList) {

		return commentResponseDtoList.stream()
			.map(commentResponseDto -> new CommentResponseVo(
				commentResponseDto.getCommentId(),
				commentResponseDto.getWriterUuid(),
				commentResponseDto.getContent(),
				commentResponseDto.getIsInCrew(),
				commentResponseDto.getCreatedAt()))
			.toList();
	}
}
