package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoogerInterceptor implements HandlerInterceptor{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("========================== START ==========================");
		log.debug("Request URI \t:  " + request.getRequestURI());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	//Controller 수행후 결과를 View로 전달하기전 실행되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("========================== END ==========================");
	}
	
}
