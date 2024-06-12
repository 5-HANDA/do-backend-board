package hobbiedo.board.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.board.application.BoardService;
import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import hobbiedo.board.dto.response.BoardResponseDto;
import hobbiedo.board.vo.request.BoardUploadRequestVo;
import hobbiedo.board.vo.response.BoardDetailsResponseVo;
import hobbiedo.board.vo.response.BoardListResponseVo;
import hobbiedo.global.api.ApiResponse;
import hobbiedo.global.api.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/crew")
@Tag(name = "Board", description = "소모임 게시판 서비스")
public class BoardController {

	private final BoardService boardService;

	@PostMapping("/{crewId}/board/create-post")
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

	@GetMapping("{crewId}/board-list")
	@Operation(summary = "게시글 목록 조회", description = "해당 소모임의 게시글 목록을 조회합니다.")
	public ApiResponse<BoardListResponseVo> getPostList(
			@PathVariable("crewId") Long crewId) {

		List<BoardResponseDto> boardListResponseDto = boardService.getPostList(crewId);

		return ApiResponse.onSuccess(
				SuccessStatus.GET_POST_LIST_SUCCESS,
				BoardListResponseVo.boardListToVo(boardListResponseDto)
		);
	}

	@GetMapping("/board/{boardId}")
	@Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
	public ApiResponse<BoardDetailsResponseVo> getPost(
			@PathVariable("boardId") Long boardId) {

		BoardDetailsResponseDto boardResponseDto = boardService.getPost(boardId);

		return ApiResponse.onSuccess(
				SuccessStatus.GET_POST_SUCCESS,
				BoardDetailsResponseVo.boardDtoToDetailsVo(boardResponseDto)
		);
	}

}
