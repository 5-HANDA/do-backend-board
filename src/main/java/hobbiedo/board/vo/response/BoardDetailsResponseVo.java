package hobbiedo.board.vo.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 조회 응답")
public class BoardDetailsResponseVo {

	private Long boardId;
	private String content;
	private String writerUuid;
	private boolean pinned; // 고정 여부
	private String createdAt; // 작성일
	private boolean updated; // 수정 여부
	private List<String> imageUrls;

	public BoardDetailsResponseVo(Long boardId, String content, String writerUuid,
		boolean pinned, LocalDateTime createdAt, boolean updated, List<String> imageUrls) {
		this.boardId = boardId;
		this.content = content;
		this.writerUuid = writerUuid;
		this.pinned = pinned;

		// 날짜, 시간 형식 지정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd HH:mm");

		this.createdAt = createdAt.format(formatter); // 작성일 (날짜, 시간 형식으로 변환)
		this.updated = updated;
		this.imageUrls = imageUrls;
	}

	public static BoardDetailsResponseVo boardDtoToDetailsVo(BoardDetailsResponseDto boardDto) {
		return new BoardDetailsResponseVo(
			boardDto.getBoardId(),
			boardDto.getContent(),
			boardDto.getWriterUuid(),
			boardDto.isPinned(),
			boardDto.getCreatedAt(),
			boardDto.isUpdated(),
			boardDto.getImageUrls()
		);
	}
}
