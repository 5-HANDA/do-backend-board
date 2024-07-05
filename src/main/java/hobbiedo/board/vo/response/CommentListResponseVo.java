package hobbiedo.board.vo.response;

import java.util.List;

import hobbiedo.board.dto.response.CommentListResponseDto;
import hobbiedo.board.dto.response.CommentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 댓글 목록 조회 응답")
public class CommentListResponseVo {

	private Boolean isLast; // 마지막 페이지 여부
	private List<CommentResponseVo> commentList; // 댓글 목록

	public CommentListResponseVo(Boolean isLast, List<CommentResponseDto> commentList) {
		this.isLast = isLast;
		this.commentList = CommentResponseVo.commentDtoToCommentListResponseVo(commentList);
	}

	public static CommentListResponseVo commentListToVo(CommentListResponseDto commentListDto) {

		return new CommentListResponseVo(
			commentListDto.getIsLast(),
			commentListDto.getCommentResponseDtoList()
		);
	}
}
