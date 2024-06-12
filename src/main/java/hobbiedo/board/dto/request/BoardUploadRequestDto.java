package hobbiedo.board.dto.request;

import java.util.List;

import hobbiedo.board.vo.request.BoardUploadRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUploadRequestDto {

	private String content;
	private List<String> imageUrls; // 이미지 URL 필드 추가

	// Vo 객체를 Dto 객체로 변환
	public static BoardUploadRequestDto boardUploadVoToDto(
			BoardUploadRequestVo boardUploadRequestVo) {

		return BoardUploadRequestDto.builder()
				.content(boardUploadRequestVo.getContent())
				.imageUrls(boardUploadRequestVo.getImageUrls())
				.build();
	}
}
