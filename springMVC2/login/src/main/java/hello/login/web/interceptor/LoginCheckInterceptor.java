package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// default 로 되어있어서 이미 구현되어 있음. 그래서 구현하고 싶은 것만 구현하면 된다.
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    // 인증 체크는 preHandle 만 있으면 된다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            // 로그인으로 redirect
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }


        return true;
    }
}
