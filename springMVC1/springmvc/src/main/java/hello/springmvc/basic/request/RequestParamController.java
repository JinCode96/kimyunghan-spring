package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");

    }

    /**
     * @RequestParam("xxx")
     */
    @ResponseBody
    @RequestMapping("request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * 요청 파라미터 변수명과 같으면 ("xxx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 단순 타입이면 @RequestParam 생략 가능 (String, int, Integer)
     * 너무 없는 것도 과하다고 생각
     */
    @ResponseBody
    @RequestMapping("request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * required 속성을 사용하여 값 넣을지 말지 결정할 수 있다
     * false -> 파라미터 값이 안 넘어와도 됨
     * but 기본형에서 객체타입으로 바꿔줘야한다.
     * 요청 파라미터에 username= 라고 보내면 null이 아니라 빈 문자라고 인식
     * "ok" 가 뜬다
     */
    @ResponseBody
    @RequestMapping("request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * defaultValue 값이 없으면 기본값으로 설정
     * 빈 문자까지 처리
     */
    @ResponseBody
    @RequestMapping("request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 요청파라미터 Map 으로 받기
     */
    @ResponseBody
    @RequestMapping("request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * 여러개의 username 으로 넘어올 때, MultiValueMap 을 활용
     */
    @ResponseBody
    @RequestMapping("request-param-multi")
    public String requestParamMultiValueMap(@RequestParam MultiValueMap<String, Object> multiValueMap) {
        log.info("username={}, age={}", multiValueMap.get("username"), multiValueMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute
     * 1. HelloData 객체 생성
     * 2. 요청 파라미터 이름으로 HelloData 객체의 프로퍼티를 찾는다.
     * 3. 해당 프로퍼티의 setter 를 호출하여 파라미터의 값을 입력(바인딩) 한다.
     * ex) 파라미터 이름이 'username'이면 setUsername() 메서드를 찾아 호출하면서 입력한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("hellodata={}", helloData);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("hellodata={}", helloData);
        return "ok";
    }


}
