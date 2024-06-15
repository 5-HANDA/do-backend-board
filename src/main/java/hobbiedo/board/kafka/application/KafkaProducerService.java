package hobbiedo.board.kafka.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;

@Service
public class KafkaProducerService {

	// 통계 테이블 생성 이벤트용 토픽
	private static final String BOARD_CREATE_TOPIC = "board-create-topic";

	// 통계 테이블 삭제 이벤트용 토픽
	private static final String BOARD_DELETE_TOPIC = "board-delete-topic";

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
}
