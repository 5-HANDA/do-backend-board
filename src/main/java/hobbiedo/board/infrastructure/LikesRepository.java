package hobbiedo.board.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

	Optional<Likes> findByBoardIdAndUserUuid(Long boardId, String userUuid);
}