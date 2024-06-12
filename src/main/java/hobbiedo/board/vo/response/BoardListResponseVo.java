package hobbiedo.board.vo.response;

import java.util.ArrayList;
import java.util.List;

import hobbiedo.board.dto.response.BoardResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 목록 조회 응답")
public class BoardListResponseVo {

	List<BoardResponseVo> boardList; // 게시글 id 목록

	public BoardListResponseVo(List<BoardResponseVo> boardList) {
		this.boardList = boardList;
	}

	public static BoardListResponseVo boardListToVo(List<BoardResponseDto> boardListDto) {

		List<BoardResponseVo> boardList = new ArrayList<>();

		for (BoardResponseDto boardDto : boardListDto) {
			boardList.add(BoardResponseVo.boardToVo(boardDto));
		}

		return new BoardListResponseVo(boardList);
	}
}
