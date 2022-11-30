package board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//해당 클래스는 예외 발생시 전역에 대해 처리하는 객체임을 선언
@ControllerAdvice
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//모든 예외 발생시 defaultExceptionHandler() 메서드를 실행
	//defaultExceptionHandler({ , }) 를 이용해 여러가지 나열 가능
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mv = new ModelAndView("/error/error_default");
		//예외 메세지를 속성으로 지정하여 view 까지 전달
		mv.addObject("exception", exception);
		
		log.error("exception", exception);
		
		return mv;
	}
}
