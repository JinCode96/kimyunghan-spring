package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    private final static String SESSION_COOKIE_NAME = "mySessionId";

    // 키 : 임의의 값, 값 : 사용자 정보
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    // 세션 생성
    public void createSession(Object value, HttpServletResponse response) {
        
        // 임의의 세션 Id 생성
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성, 키 : "mySessionId", 값 : 임의의 값
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    // 세션 조회
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        
        // 쿠키가 없으면 null 리턴
        if (sessionCookie == null) {
            return null;
        }
        
        // 쿠키가 있다면 임의의 값을 가지고 세션 스토어의 사용자 정보를 리턴
        return sessionStore.get(sessionCookie.getValue());
    }

    // 세션 만료
    public void expire(HttpServletRequest request) {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);

        // 쿠키값이 없으면 세션 저장소에 있는 사용자 정보를 만료한다.
        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
    }

    // 쿠키 조회 메서드
    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        // 쿠키가 없으면 null 반환
        if (request.getCookies() == null) {
            return null;
        }

        // 쿠키를 가져와서 스트림으로 변환 후 쿠키 이름이 "mySessionId"인 것들 중 아무거나 반환. 없으면 null 반환
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }


    }
