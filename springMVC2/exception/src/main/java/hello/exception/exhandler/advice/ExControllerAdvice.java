package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러를 호출한 결과 IllegalArgumentException 예외가 컨트롤러 밖으로 던져진다.
 * 예외가 발생했으로 ExceptionResolver 가 작동한다. 가장 우선순위가 높은 ExceptionHandlerExceptionResolver 가 실행된다.
 * ExceptionHandlerExceptionResolver 는 해당 컨트롤러에 IllegalArgumentException 을 처리할 수 있는
 * @ExceptionHandler 가 있는지 확인한다.
 * illegalExHandle() 를 실행한다.
 * @RestController 이므로 illegalExHandle() 에도
 * @ResponseBody 가 적용된다. 따라서 HTTP 컨버터가 사용되고, 응답이 다음과 같은 JSON으로 반환된다.
 * @ResponseStatus(HttpStatus.BAD_REQUEST) 를 지정했으므로 HTTP 상태 코드 400으로 응답한다.
 */

/**
 * @RestControllerAdvice -> @ControllerAdvice 에 ResponseBody 가 붙은 것.
 * @ControllerAdvice 는 대상으로 지정한 컨트롤러에 ExceptionHandler 기능을 부여
 */

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    /**
     * 이 컨트롤러에서 IllegalArgumentException 이 발생하면 이 메서드가 잡는다.
     * 밑의 로직을 호출하고, RestController 이기 때문에 ErrorResult 가 Json 으로 반환된다.
     * @ResponseStatus 로 상태코드를 변환할 수 있다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    /**
     * ResponseEntity 사용
     * UserException 파라미터 타입과 같으면 생략 가능
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * 최상위 예외 Exception 을 받기 때문에 그 하위 예외들은 모두 여기서 처리. 공통처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
