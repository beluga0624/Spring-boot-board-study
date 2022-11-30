package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoogerInterceptor;

//WebMvcConfigurer : @EnableWebMvc 어노테이션에서 제공하는 Bean을 개발자가 커스터마이징 할 수 있는 기능을 제공하는 인터페이스
//@EnableWebMvc : 어노테이션 기반의 스프링 MVC를 간단하게 성정할 수 있는 어노테이션으로 자동으로 MVC설정 관련 Bean들이 등록된다.
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//개발자가 작성한 인터셉터를 동작시키기 위해 등록 처리
		registry.addInterceptor(new LoogerInterceptor());
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		//인코딩 방식 선언
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		//파일 업로드시 한개당 최대 크리 지정(5MB)
		commonsMultipartResolver.setMaxUploadSize(5*1024*1024);
		return commonsMultipartResolver;
	}	
}
