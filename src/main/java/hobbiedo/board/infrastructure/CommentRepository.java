package hobbiedo.board.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hobbiedo.board.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findByBoardId(Long boardId, Pageable pageable);

	// 특정 게시글의 댓글을 모두 삭제
	void deleteByBoardId(Long boardId);
}
