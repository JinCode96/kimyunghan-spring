package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

// 필터 적용
@Configuration
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer 인터셉터 적용시킬 때 사용

    // 의존관계 주입을 통해서 설정할 수 있음
    private final LoginCheckInterceptor loginCheckInterceptor;

    public WebConfig(LoginCheckInterceptor loginCheckInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
    }

    // ArgumentResolver 사용
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    // 인터셉터 등록 방식
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") // 필터 서블릿과 패턴이 조금 다름
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 이 패턴들은 로그를 남기지 않음

        registry.addInterceptor(loginCheckInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"); // 인터셉터의 장점
    }

    @Bean
    public FilterRegistrationBean logFiler() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 등록할 필터를 지정
        filterRegistrationBean.setOrder(1); // 필터는 체인으로 동작한다. 따라서 순서가 필요. 낮을 수록 먼저 동작
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 url 패턴에 적용할 건가 (모든 url)
        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean logCheckFiler() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); 
        filterRegistrationBean.setOrder(2); 
        filterRegistrationBean.addUrlPatterns("/*"); // LoginCheckFilter 에서 whiteList 로 거를 것이다. 성능저하는 거의 없다고 봐도 됨
        return filterRegistrationBean;
    }

}
