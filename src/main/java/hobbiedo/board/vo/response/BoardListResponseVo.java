package hobbiedo.board.vo.response;

import java.util.List;

import hobbiedo.board.dto.response.BoardListResponseDto;
import hobbiedo.board.dto.response.BoardResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 목록 조회 응답")
public class BoardListResponseVo {

	private Boolean isLast; // 마지막 페이지 여부
	private List<BoardResponseVo> boardList; // 게시글 id 목록

	public BoardListResponseVo(Boolean isLast, List<BoardResponseDto> boardList) {
		this.isLast = isLast;
		this.boardList = BoardResponseVo.boardDtoToBoardListResponseVo(boardList);
	}

	public static BoardListResponseVo boardListToVo(BoardListResponseDto boardListDto) {

		return new BoardListResponseVo(
			boardListDto.getIsNext(),
			boardListDto.getBoardResponseDtoList()
		);
	}
}
