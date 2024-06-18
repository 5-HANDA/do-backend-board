package hobbiedo.board.kafka.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardCreateScoreDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteScoreDto;
import hobbiedo.board.kafka.dto.BoardLikeUpdateDto;

@Service
public class KafkaProducerService {

	// 통계 테이블 생성 이벤트용 토픽
	private static final String BOARD_CREATE_TOPIC = "board-create-topic";

	// 통계 테이블 삭제 이벤트용 토픽
	private static final String BOARD_DELETE_TOPIC = "board-delete-topic";

	// 통계 테이블 댓글 수 증가 이벤트용 토픽
	private static final String BOARD_COMMENT_UPDATE_TOPIC = "board-comment-update-topic";

	// 통계 테이블 댓글 수 감소 이벤트용 토픽
	private static final String BOARD_COMMENT_DELETE_TOPIC = "board-comment-delete-topic";

	// 통계 테이블 좋아요 수 증가 이벤트용 토픽
	private static final String BOARD_LIKE_UPDATE_TOPIC = "board-like-update-topic";

	// 통계 테이블 좋아요 수 감소 이벤트용 토픽
	private static final String BOARD_LIKE_DELETE_TOPIC = "board-like-delete-topic";

	// 게시글 생성 시 소모임 점수 증가 이벤트용 토픽
	private static final String CREW_SCORE_INCREASE_TOPIC = "crew-score-increase-topic";

	// 게시글 삭제 시 소모임 점수 감소 이벤트용 토픽
	private static final String CREW_SCORE_DECREASE_TOPIC = "crew-score-decrease-topic";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	// 게시글 생성 이벤트 메시지 전송
	public void sendCreateTableMessage(BoardCreateEventDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_CREATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 삭제 이벤트 메시지 전송
	public void sendDeleteTableMessage(BoardDeleteEventDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_DELETE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 댓글 수 업데이트 이벤트 메시지 전송
	public void sendUpdateCommentCountMessage(BoardCommentUpdateDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_COMMENT_UPDATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 댓글 수 감소 이벤트 메시지 전송
	public void sendDeleteCommentCountMessage(BoardCommentUpdateDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_COMMENT_DELETE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 좋아요 수 업데이트 이벤트 메시지 전송
	public void sendUpdateLikeCountMessage(BoardLikeUpdateDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_LIKE_UPDATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 좋아요 수 감소 이벤트 메시지 전송
	public void sendDeleteLikeCountMessage(BoardLikeUpdateDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_LIKE_DELETE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 생성 시 소모임 점수 증가 이벤트 메시지 전송
	public void sendIncreaseCrewScoreMessage(BoardCreateScoreDto eventDto) {
		try {

			kafkaTemplate.send(CREW_SCORE_INCREASE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 삭제 시 소모임 점수 감소 이벤트 메시지 전송
	public void sendDecreaseCrewScoreMessage(BoardDeleteScoreDto eventDto) {
		try {

			kafkaTemplate.send(CREW_SCORE_DECREASE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
