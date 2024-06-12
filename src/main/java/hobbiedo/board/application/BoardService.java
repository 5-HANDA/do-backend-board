package hobbiedo.board.application;

import java.util.List;

import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.dto.response.BoardDetailsResponseDto;
import hobbiedo.board.dto.response.BoardResponseDto;

public interface BoardService {

	void createPostWithImages(Long crewId, String uuid,
		BoardUploadRequestDto boardUploadRequestDto);

	BoardDetailsResponseDto getPost(Long boardId);

	List<BoardResponseDto> getPostList(Long crewId);

	void updatePostWithImages(Long boardId, String uuid,
		BoardUploadRequestDto boardUpdateRequestDto);
}
