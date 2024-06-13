package hobbiedo.board.domain;

import hobbiedo.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 게시글 id
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;

	// 작성자 uuid
	@Column(nullable = false, length = 255)
	private String writerUuid;

	// 댓글 내용
	@Column(nullable = false, length = 1500) // 댓글은 최대 500자
	@Size(max = 501, message = "댓글 내용은 반드시 500자 이내여야 합니다")
	private String content;

	// 소모임 회원 여부
	@Column(nullable = false)
	private Boolean isInCrew;

	@Builder
	public Comment(Long id, Board board, String writerUuid, String content, boolean isInCrew) {
		this.id = id;
		this.board = board;
		this.writerUuid = writerUuid;
		this.content = content;
		this.isInCrew = isInCrew;
	}
}
