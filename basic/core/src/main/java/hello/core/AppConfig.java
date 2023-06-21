package hello.core;

import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 구현체를 대신 갈아 끼워주는 AppConfig
 * DIP, OCP 원칙을 모두 지킬 수 있다.
 * 관심사 분리
 * 애플리케이션이 어떻게 동작해야 할지 전체 구성을 책임진다
 * @Configuration : 설정 정보라는 것을 나타냄
 * @Bean : @Bean 이라고 적힌 메서드를 모두 호출하여 반환된 객체를 스프링 컨테이너에 등록 -> 스프링 빈
 */
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * 메서드로 빼냄
     * 이 메서드만 변경하면 됨
     */
    @Bean
    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository(); // 여기만 변경
    }

    // 할인 정책은 고정 할인을 쓴는구나라고 바로 알 수 있음
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy(); // 여기만 변경
    }

}
