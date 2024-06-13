package hobbiedo.board.domain;

import org.hibernate.annotations.ColumnDefault;

import hobbiedo.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 게시글 내용
	@Column(nullable = false, length = 3000)
	@Size(max = 1001, message = "게시글 내용은 반드시 1000자 이내여야 합니다")
	private String content;

	// 작성자
	@Column(nullable = false, length = 255)
	private String writerUuid;

	// 소모임 id
	@Column(nullable = false)
	private Long crewId;

	// 고정 여부, default = false
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean isPinned;

	// 수정 여부, default = false
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean isUpdated;

	@Builder
	public Board(Long id, String content, String writerUuid, Long crewId, boolean isPinned,
		boolean isUpdated) {
		this.id = id;
		this.content = content;
		this.writerUuid = writerUuid;
		this.crewId = crewId;
		this.isPinned = isPinned;
		this.isUpdated = isUpdated;
	}

}
