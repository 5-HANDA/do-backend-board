package hobbiedo.board.vo.response;

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

	public static BoardResponseVo boardToVo(BoardResponseDto boardDto) {

		return new BoardResponseVo(

				boardDto.getBoardId(),
				boardDto.isPinned()
		);
	}
}
