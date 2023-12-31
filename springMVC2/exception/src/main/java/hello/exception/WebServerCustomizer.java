package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 서블릿이 제공하는 예외 처리. 잘 사용하지 않는다
 * 예외가 발생하게 되면  : 컨트롤러 -> 서블릿 -> WAS
 * 해당 경로로 페이지 호출 :  WAS -> 서블릿 -> 컨트롤러
 */
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        ErrorPage errorPageEx = new ErrorPage(Exception.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPage500);
    }
}
