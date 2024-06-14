package hobbiedo.board.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponseDto {

	private Boolean isLast;
	private List<CommentResponseDto> commentResponseDtoList;

	public static CommentListResponseDto commentDtoToCommentListResponseDto(Boolean isLast,
		List<CommentResponseDto> commentResponseDtoList) {

		return CommentListResponseDto.builder()
			.isLast(isLast)
			.commentResponseDtoList(commentResponseDtoList)
			.build();
	}
}
