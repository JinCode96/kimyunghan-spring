package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        // 테스트 할 수 있도록 스프링에서 지원하는 Mock (httpServletRequest 는 인터페이스라 테스트하기 힘들다.)
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 세션 생성
        Member member = new Member();
        sessionManager.createSession(member, response); // 세션 저장소에 멤버 정보가 담긴다. response 에도 담김

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); // response 에 담겨있는 uuid 값을 request 에 담음

        // 세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expire = sessionManager.getSession(request);
        assertThat(expire).isNull();
    }

}