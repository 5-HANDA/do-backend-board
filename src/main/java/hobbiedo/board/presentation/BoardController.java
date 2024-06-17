package hobbiedo.board.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.board.application.BoardService;
import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import hobbiedo.board.dto.response.BoardListResponseDto;
import hobbiedo.board.dto.response.BoardResponseDto;
import hobbiedo.board.vo.request.BoardUploadRequestVo;
import hobbiedo.board.vo.response.BoardDetailsResponseVo;
import hobbiedo.board.vo.response.BoardListResponseVo;
import hobbiedo.board.vo.response.BoardResponseVo;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/crew/board")
@Tag(name = "Board", description = "소모임 게시판 서비스")
public class BoardController {

	private final BoardService boardService;

	// 게시글 생성
	@PostMapping("/{crewId}")
	@Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
	public ApiResponse<Void> createPost(
		@PathVariable("crewId") Long crewId,
		@RequestHeader(name = "Uuid") String uuid,
		@RequestBody BoardUploadRequestVo boardUploadRequestVo) {

		BoardUploadRequestDto boardUploadRequestDto = BoardUploadRequestDto
			.boardUploadVoToDto(boardUploadRequestVo);

		boardService.createPostWithImages(crewId, uuid, boardUploadRequestDto);

		return ApiResponse.onSuccess(
			SuccessStatus.CREATE_POST_SUCCESS
		);
	}

	// 게시글 목록 조회
	@GetMapping("/{crewId}/board-list")
	@Operation(summary = "게시글 목록 조회", description = "해당 소모임의 게시글 목록을 조회합니다.")
	public ApiResponse<BoardListResponseVo> getPostList(
		@PathVariable("crewId") Long crewId,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable page) {

		BoardListResponseDto boardListResponseDto = boardService.getPostList(crewId, page);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_POST_LIST_SUCCESS,
			BoardListResponseVo.boardListToVo(boardListResponseDto)
		);
	}

	// 게시글 상세 조회
	@GetMapping("/{boardId}")
	@Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
	public ApiResponse<BoardDetailsResponseVo> getPost(
		@PathVariable("boardId") Long boardId) {

		BoardDetailsResponseDto boardResponseDto = boardService.getPost(boardId);

		return ApiResponse.onSuccess(
			SuccessStatus.GET_POST_SUCCESS,
			BoardDetailsResponseVo.boardDtoToDetailsVo(boardResponseDto)
		);
	}

	// 게시글 수정
	@PutMapping("/{boardId}")
	@Operation(summary = "게시글 수정", description = "작성자가 게시글을 수정합니다.")
	public ApiResponse<Void> updatePost(
		@PathVariable("boardId") Long boardId,
		@RequestHeader(name = "Uuid") String uuid,
		@RequestBody BoardUploadRequestVo boardUploadRequestVo) {

		BoardUploadRequestDto boardUpdateRequestDto = BoardUploadRequestDto
			.boardUploadVoToDto(boardUploadRequestVo);

		boardService.updatePostWithImages(boardId, uuid, boardUpdateRequestDto);

		return ApiResponse.onSuccess(
			SuccessStatus.UPDATE_POST_SUCCESS
		);
	}

	// 게시글 삭제
	@DeleteMapping("/{boardId}")
	@Operation(summary = "게시글 삭제", description = "작성자가 게시글을 삭제합니다.")
	public ApiResponse<Void> deletePost(
		@PathVariable("boardId") Long boardId,
		@RequestHeader(name = "Uuid") String uuid) {

		boardService.deletePost(boardId, uuid);

		return ApiResponse.onSuccess(
			SuccessStatus.DELETE_POST_SUCCESS
		);
	}

	// 고정 게시글 조회
	@GetMapping("/{crewId}/pinned")
	@Operation(summary = "고정 게시글 조회", description = "해당 소모임의 고정 게시글을 조회합니다.")
	public ApiResponse<BoardResponseVo> getPinnedPost(
		@PathVariable("crewId") Long crewId) {

		BoardResponseDto boardResponseDto = boardService.getPinnedPost(crewId);

		if (boardResponseDto == null) {
			return ApiResponse.onSuccess(
				SuccessStatus.GET_PINNED_POST_SUCCESS,
				null
			);
		}

		return ApiResponse.onSuccess(
			SuccessStatus.GET_PINNED_POST_SUCCESS,
			BoardResponseVo.boardDtoToBoardResponseVo(boardResponseDto)
		);
	}
}
