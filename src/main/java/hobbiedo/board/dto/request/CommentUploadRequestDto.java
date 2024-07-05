package hobbiedo.board.dto.request;

import hobbiedo.board.vo.request.CommentUploadRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUploadRequestDto {

	private String content;

	// Vo 객체를 Dto 객체로 변환
	public static CommentUploadRequestDto commentUploadVoToDto(
		CommentUploadRequestVo commentUploadRequestVo) {

		return CommentUploadRequestDto.builder()
			.content(commentUploadRequestVo.getContent())
			.build();
	}
}
