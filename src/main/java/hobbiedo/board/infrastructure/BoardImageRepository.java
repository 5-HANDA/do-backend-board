package hobbiedo.board.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.BoardImage;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

	List<BoardImage> findByBoardId(Long boardId);

	void deleteByBoardId(Long boardId);
}
