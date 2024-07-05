package hobbiedo.board.kafka.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.board.kafka.dto.BoardCommentDeleteDto;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardLikeUpdateDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;

@Service
public class KafkaProducerService {

	// 게시글 생성 이벤트용 토픽
	private static final String BOARD_CREATE_TOPIC = "board-create-topic";

	// 게시글 삭제 이벤트용 토픽
	private static final String BOARD_DELETE_TOPIC = "board-delete-topic";

	// 게시글 수정 이벤트용 토픽
	private static final String BOARD_UPDATE_TOPIC = "board-update-topic";

	// 게시글 고정 이벤트용 토픽
	private static final String BOARD_PIN_TOPIC = "board-pin-topic";

	// 게시글 고정 해제 이벤트용 토픽
	private static final String BOARD_UNPIN_TOPIC = "board-unpin-topic";

	// 게시글 댓글 작성 이벤트용 토픽
	private static final String BOARD_COMMENT_UPDATE_TOPIC = "board-comment-update-topic";

	// 게시글 댓글 삭제 이벤트용 토픽
	private static final String BOARD_COMMENT_DELETE_TOPIC = "board-comment-delete-topic";

	// 게시글 좋아요 수 증가 이벤트용 토픽
	private static final String BOARD_LIKE_UPDATE_TOPIC = "board-like-update-topic";

	// 게시글 좋아요 수 감소 이벤트용 토픽
	private static final String BOARD_LIKE_DELETE_TOPIC = "board-like-delete-topic";

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

	// 게시글 수정 이벤트 메시지 전송
	public void sendUpdateTableMessage(BoardUpdateEventDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_UPDATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 고정 이벤트 메시지 전송
	public void sendPinTableMessage(BoardPinEventDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_PIN_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 고정 해제 이벤트 메시지 전송
	public void sendUnpinTableMessage(BoardUnPinEventDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_UNPIN_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 댓글 작성 이벤트 메시지 전송
	public void sendUpdateCommentCountMessage(BoardCommentUpdateDto eventDto) {
		try {

			kafkaTemplate.send(BOARD_COMMENT_UPDATE_TOPIC, eventDto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 게시글 댓글 삭제 이벤트 메시지 전송
	public void sendDeleteCommentCountMessage(BoardCommentDeleteDto eventDto) {
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
}
