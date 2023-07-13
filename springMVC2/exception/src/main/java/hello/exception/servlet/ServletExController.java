package hello.exception.servlet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Slf4j
@Controller
public class ServletExController {

    /**
     * 서블릿 컨테이너가 기본으로 제공하는 예외 처리 화면
     */

    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!"); // Exception 터지면 서블릿 컨테이너는 500으로 처리한다.
    }

    // http 상태 코드와 메세지를 직접 담아서 처리하고 싶을 때
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 에러"); // sendError 는 상태코드를 지정할 수 있다.
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500, "500 에러");
    }
}
