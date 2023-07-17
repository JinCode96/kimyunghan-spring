package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ResponseStatus 어노테이션
 * ResponseStatusExceptionResolver 가 동작하여 상태코드 변경하고, 메세지 출력 후, 모델엔 뷰를 반환하여 정상 동작하도록 한다.
 * 결과적으로 response.sendError()를 보낸다.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException{
}
