package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Formatter
 * 문자에 특화(객체 문자, 문자 객체) + 현지화(Locale)
 * Converter 의 특별한 버전
 * 포맷터( Formatter )는 객체를 문자로 변경하고, 문자를 객체로 변경하는 두 가지 기능을 모두 수행한다.
 */
@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    /**
     * "1,000" 처럼 숫자 중간의 쉼표를 적용하려면 자바가 기본으로 제공하는 NumberFormat 객체를 사용하면 된다.
     * 이 객체는 Locale 정보를 활용해서 나라별로 다른 숫자 포맷을 만들어준다.
     * parse() 를 사용해서 문자를 숫자로 변환한다. 참고로 Number 타입은 Integer , Long 과 같은 숫자 타입의 부모 클래스이다.
     * print() 를 사용해서 객체를 문자로 변환한다.
     */

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text); // "1,000" -> 1000,  Long 타입
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object); //  1000 -> "1,000"
    }
}
