package board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component : 개발자가 작성한 클래스 빈등록 처리
//@Aspect : 해당 클래스가 AOP개념을 사용하는 클래스임을 선언 
@Component
@Aspect
public class LoggerAspect {
	//logback을 사용하여 로그 출력
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//@Around : 공통기능을 핵심기능(메서드)의 앞 뒤에서 모두 적용
	//"execution(패키지..하위패키지.*Controller.*(..))" : PointCut(공통기능을 어느 Target(객체)에 적용시킬 것인지 문법에 의해 결정
	@Around("execution(* board..controller.*Controller.*(..)) or "
			+ "	execution(* board..service.*Impl.*(..)) or "
			+ "	execution(* board..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		String type = "";
		//getSignature() : 호출되는 메서드의 모든 정보를 리턴
		//getDeclaringTypeName() : 공통기능이 적용되는 메서드를 포함하는 클래스명
		String name = joinPoint.getSignature().getDeclaringTypeName();
		
		if(name.indexOf("Controller") > -1) {
			type = "Controller \t:  ";
		}else if(name.indexOf("Service") > -1) {
			type = "ServiceImpl \t:  ";
		}else if(name.indexOf("Mapper") > -1) {
			type = "Mapper \t\t:  ";
		}
		
		//getName() : joinPoint 되는 메서드명
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		
		//proceed()를 기준으로 before, after
		return joinPoint.proceed();
	}
}
