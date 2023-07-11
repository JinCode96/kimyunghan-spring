package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        // 회원 정보가 없다면 home 으로 리턴
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 세션이 있으면 가져오기, 없으면 새로 생성하지 않는다.
        HttpSession session = request.getSession(false);

        // 세션이 없으면 home
        if (session == null) {
            return "home";
        }
        
        // 세션이 있으면 회원 정보 가져오기
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /**
     * @SessionAttribute : request 에서 세션을 찾아올 필요 없이 스프링에서 제공해준다.
     */
//    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // 세션에 회원 정보가 없으면 null
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /**
     * @Login 어노테이션을 만들어야 컴파일 오류가 사라진다.
     * @Login 이 있으면 직접 만든 LoginMemberArgumentResolver 가 동작하여 자동으로 세션에 있는 로그인 회원을 찾아주고
     * 없으면 null 을 반환하도록 개발
     */
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

        // 세션에 회원 정보가 없으면 null
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}