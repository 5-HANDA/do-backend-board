package hobbiedo.board.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findByCrewId(Long crewId, Pageable pageable);

	Optional<Board> findByIsPinnedTrueAndCrewId(Long crewId);
}
