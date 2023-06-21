package hello.core;

import hello.core.member.MemberService;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService(); // MemberServiceImpl

        /**
         * ApplicationContext -> 스프링 컨테이너라고 볼 수 있음. @Bean 관리
         * AnnotationConfigApplicationContext -> 어노테이션 기반
         * AppConfig 정보를 가지고 스프링 컨테이너에 담아서 관리해준다.
         * 컨테이너에 등록된 스프링 빈들 중에서 getBean 으로 골라서 사용
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);// ("이름", 타입)

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new Member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
