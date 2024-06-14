package hobbiedo.board.application;

import org.springframework.data.domain.Pageable;

import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import hobbiedo.board.dto.response.BoardListResponseDto;

public interface BoardService {

	void createPostWithImages(Long crewId, String uuid,
		BoardUploadRequestDto boardUploadRequestDto);

	BoardDetailsResponseDto getPost(Long boardId);

	BoardListResponseDto getPostList(Long crewId, Pageable page);

	void updatePostWithImages(Long boardId, String uuid,
		BoardUploadRequestDto boardUpdateRequestDto);

	void deletePost(Long boardId, String uuid);
}
