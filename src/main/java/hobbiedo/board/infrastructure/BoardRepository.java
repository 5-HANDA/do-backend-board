package hobbiedo.board.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	List<Board> findByCrewId(Long crewId);
}
