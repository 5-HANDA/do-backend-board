package hobbiedo.board.vo.response;

import java.util.List;

import hobbiedo.board.dto.response.BoardResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 id 조회 응답")
public class BoardResponseVo {

	private Long boardId; // 게시글 번호
	private boolean pinned; // 고정 여부

	public BoardResponseVo(Long boardId, boolean pinned) {
		this.boardId = boardId;
		this.pinned = pinned;
	}

	// 리스트 Dto -> 리스트 Vo
	public static List<BoardResponseVo> boardDtoToBoardListResponseVo(
		List<BoardResponseDto> boardResponseDtoList) {

		return boardResponseDtoList.stream()
			.map(boardResponseDto -> new BoardResponseVo(
				boardResponseDto.getBoardId(),
				boardResponseDto.isPinned()))
			.toList();
	}

	// 단일 Dto -> 단일 Vo
	public static BoardResponseVo boardDtoToBoardResponseVo(BoardResponseDto boardResponseDto) {
		return new BoardResponseVo(
			boardResponseDto.getBoardId(),
			boardResponseDto.isPinned()
		);
	}
}
