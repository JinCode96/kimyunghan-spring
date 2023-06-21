package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller -> String 을 return 하면 뷰 네임이 반환됨
@Slf4j
@RestController // 문자를 return 하면 String 이 반환된다. http 바디에 집어넣음
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        // + 로 하면 연산이 일어나게 된다. 메모리, cpu 사용. trace 를 사용하지도 않는데 연산이 일어나게 됨
        log.info("info log = " + name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
    }
}
