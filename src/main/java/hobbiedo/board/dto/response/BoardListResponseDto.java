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

	private Boolean isNext;
	private List<BoardResponseDto> boardResponseDtoList;

	public static BoardListResponseDto boardDtoToBoardListResponseDto(Boolean isNext,
		List<BoardResponseDto> boardResponseDtoList) {

		return BoardListResponseDto.builder()
			.isNext(isNext)
			.boardResponseDtoList(boardResponseDtoList)
			.build();
	}
}
