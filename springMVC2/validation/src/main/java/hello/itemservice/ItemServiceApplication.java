package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	/*
		검증기 글로벌 설정
		implements WebMvcConfigurer

		@Override
		public Validator getValidator(){
			return new ItemValidator;
		}
	 */
}

