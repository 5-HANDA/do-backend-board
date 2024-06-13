package hobbiedo.board.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.board.application.BoardInteractionService;
import hobbiedo.board.dto.request.CommentUploadRequestDto;
import hobbiedo.board.dto.response.CommentListResponseDto;
import hobbiedo.board.vo.request.CommentUploadRequestVo;
import hobbiedo.board.vo.response.CommentListResponseVo;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/crew/board-interaction")
@Tag(name = "Board-Interaction", description = "소모임 게시판 상호작용 서비스")
public class BoardInteractionController {

	private final BoardInteractionService boardInteractionService;

	// 게시글 댓글 생성
	@PostMapping("/{boardId}/comment")
	@Operation(summary = "게시글 댓글 생성", description = "게시글에 댓글을 생성합니다.")
	public ApiResponse<Void> createComment(
		@PathVariable("boardId") Long boardId,
		@RequestHeader(name = "Uuid") String uuid,
		@RequestBody CommentUploadRequestVo commentUploadRequestVo) {

		CommentUploadRequestDto commentUploadRequestDto = CommentUploadRequestDto
			.commentUploadVoToDto(commentUploadRequestVo);

		boardInteractionService.createComment(boardId, uuid, commentUploadRequestDto);

		return ApiResponse.onSuccess(
			SuccessStatus.CREATE_COMMENT_SUCCESS
		);
	}

	// 게시글 댓글 리스트 조회
	@GetMapping("/{boardId}/comment")
	@Operation(summary = "게시글 댓글 조회", description = "게시글에 등록된 댓글들을 조회합니다.")
	public ApiResponse<CommentListResponseVo> getCommentList(
		@PathVariable("boardId") Long boardId,
		@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable page) {

		CommentListResponseDto commentListResponseDto = boardInteractionService.getCommentList(
			boardId, page);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_COMMENT_LIST_SUCCESS,
			CommentListResponseVo.commentListToVo(commentListResponseDto)
		);
	}
}