package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
//basePackages로 선언된 board 패키지에서 JPA Entity로 선언된 클래스들을 검색하여 날짜와 관련된 JSR310 API를 사용하도록 한다.
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, basePackages = {"board"})
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}


