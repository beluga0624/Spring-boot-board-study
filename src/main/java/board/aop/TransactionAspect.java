package board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
	//트랜잭션 설정시 사용되는 설정값을 상수로 선언
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	//서비스 클래스를 생성시 매번 @Transactional을 적용해야 하는데 이것을 Pointcut을 이용하여 Impl로 끝나는 클래스 모두에 대해 트랜잭션 처리
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* board..service.*Impl.*(..))";
	
	@Autowired
	private TransactionManager transactionManager;
	
	//TransactionInterceptor : 선언적 트랜잭션 관리를 제공하는 AOP Interceptor
	//선언적 트랜잭션 관리 : 트랜잭션 처리를 하는 코드를 Business Logic으로 부터 분리하여 별도 처리하는 기법
	@Bean
	public TransactionInterceptor transactionAdvice() {
		//트랜잭션 속성을 지정하는 객체 선언
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		//특정 예외가 발생할 경우 Rollback 규칙을 적용하여 Rollback 처리를 해야하는지 확인하는 TransactionAttribute 구현
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		//모든 메서드에 대해서 트랙잭션 처리
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		//모든 예외 발생시 rollback 규칙을 적용하되 싱글톤 패턴을 적용하여 한번만 인스턴스를 생성하고 다음부터는 재사용
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		//트랙잭션 속성을 필드에 대입
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	//Advisor : Advice(공통기능) + Pointcut(공통기능을 어느 타겟의 joinPoint에 적용시킬지 결정하는 선언)
	@Bean
	public Advisor transactionAdviceAdvisor() {
		//AspectJExpressionPointcut : Pointcut 표현식을 지원하는 객체
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		//DefaultPointcutAdvisor : Pointcut과 Advice(공통관심사를 모듈화 한 것)를 결합시켜주는 기능을 하는 객체
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}
