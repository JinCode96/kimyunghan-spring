package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 로그인 정책이 바뀌더라도 여기 부분만 바꾸면 된다. controller 같은 건 안 바꿔도 됨
 */
@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            // 화이트 리스트가 아니면 로직 시작
            if (!isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession();

                // 미인증 사용자일 시
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI); // 로그인 후 다시 요청 url 로 보내기 위함
                    return; // 미인증 사용자는 다음으로 넘기지 않고 여기서 끝
                }
            }
            
            // 화이트 리스트가 맞다면 다음 필터 넘김
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외 로깅 가능하지만, 톰캣까지 예외를 보내주어야함
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    // 화이트 리스트의 경우 인증 필터 X, 화이트 리스트가 맞다면 true, 아니면 false
    public boolean isLoginCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }


}
