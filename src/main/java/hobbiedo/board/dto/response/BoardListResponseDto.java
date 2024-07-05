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
public class BoardListResponseDto {

	private Boolean isLast;
	private List<BoardResponseDto> boardResponseDtoList;

	public static BoardListResponseDto boardDtoToBoardListResponseDto(Boolean isLast,
		List<BoardResponseDto> boardResponseDtoList) {

		return BoardListResponseDto.builder()
			.isLast(isLast)
			.boardResponseDtoList(boardResponseDtoList)
			.build();
	}
}
