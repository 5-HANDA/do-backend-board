package hobbiedo.global.api.code.status;

import org.springframework.http.HttpStatus;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
	VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "GLOBAL400", "데이터베이스 유효성 에러"),
	EXAMPLE_EXCEPTION(HttpStatus.BAD_REQUEST, "EXAMPLE400", "샘플 에러 메시지입니다"),

	// 게시글이 내용이 비어있을 경우
	CREATE_POST_CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "BOARD401", "게시글 내용이 비어있습니다."),
	// 이미지 업로드가 5개를 초과할 경우
	CREATE_POST_IMAGE_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "BOARD402", "이미지 업로드는 최대 5개까지 가능합니다."),
	// 게시글이 존재하지 않을 경우
	GET_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD403", "게시글을 찾을 수 없습니다."),
	// 게시글의 작성자와 요청자가 다를 경우
	UPDATE_POST_NOT_WRITER(HttpStatus.FORBIDDEN, "BOARD405", "게시글 작성자만 수정할 수 있습니다."),
	DELETE_POST_NOT_WRITER(HttpStatus.FORBIDDEN, "BOARD406", "게시글 작성자만 삭제할 수 있습니다."),
	// 댓글 내용이 비어있을 경우
	CREATE_COMMENT_CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "BOARD407", "댓글 내용이 비어있습니다."),
	// 댓글의 작성자의 소모임 소속 여부가 비어있을 경우
	CREATE_COMMENT_IS_IN_CREW_EMPTY(HttpStatus.BAD_REQUEST, "BOARD408",
		"댓글의 작성자의 소모임 소속 여부가 비어있습니다."),
	// 댓글이 존재하지 않을 경우
	GET_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD409", "댓글을 찾을 수 없습니다."),
	// 댓글의 작성자와 요청자가 다를 경우
	DELETE_COMMENT_NOT_WRITER(HttpStatus.FORBIDDEN, "BOARD410", "댓글 작성자만 삭제할 수 있습니다."),
	// 좋아요가 이미 존재할 경우
	CREATE_LIKE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BOARD411", "이미 좋아요를 누른 게시글입니다."),
	// 좋아요가 존재하지 않을 경우
	DELETE_LIKE_NOT_EXIST(HttpStatus.NOT_FOUND, "BOARD412", "좋아요를 누르지 않은 게시글입니다."),
	// 이미 고정된 게시글일 경우
	PIN_POST_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BOARD413", "이미 고정된 게시글입니다.");

	private final HttpStatus httpStatus;
	private final String status;
	private final String message;

	/**
	 * getReason()함수를  사용해야 할 때
	 * 1. 간결성과 보안: API 응답에서 꼭 필요한 정보만을 포함시키기 위해 사용(ex: API 응답의 크기 감소, 민감한 데이터 제한)
	 * 2. 내부 처리용: API 응답 전송 전,내부 로그나 모니터링 시스템에 오류 정보를 기록할때 사용 로그 데이터의 크기를 줄이거나 처리를 단순화
	 * 3. HTTP 상태 코드 분리: getReason()을 사용하여 오류 코드와 메시지만을 전달하고, HTTP 상태 코드는 별도로 처리 가능
	 * 4. 성능 최적화: 성능이 중요한 환경에서는 가능한 한 응답을 간단하게 유지하여 이러한 오버헤드를 최소화
	 * 5. API 설계의 일관성: 오류 처리를 통합하여 여러 다른 API에서 일관된 방식으로 오류를 보고하고 싶을 때 유용
	 */
	@Override
	public ErrorReasonDto getReason() {
		return ErrorReasonDto
			.builder()
			.code(status)
			.message(message)
			.build();
	}

	@Override
	public ErrorReasonDto getReasonHttpStatus() {
		return ErrorReasonDto
			.builder()
			.httpStatus(httpStatus)
			.code(status)
			.message(message)
			.build();
	}
}
