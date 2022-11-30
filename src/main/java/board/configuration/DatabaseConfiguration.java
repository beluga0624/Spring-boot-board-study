package board.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration //자바 기반의 설정 파일임을 선언하는 어노테이션
@PropertySource("classpath:/application.properties")//설정파일의 정보를 읽어오는 어노테이션
//트랜잭션 처리를 활성화 하는 어노테이션으로 DataSourceTransactionManager Bean 을 찾아 TransactionManager로 사용
@EnableTransactionManagement
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean // 수정할 수 없는 기존 싵스템에 개발되어있는 객체를 빈등록시 사용
	//@Component 개발자가 작성한 객체를 빈 등록시 사용
	//application.properties 파일 속성중 문자열이 spring.datasource.hikari로 시작하는 설정을 가져온다
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	//데이터베이스 연동 DataSource 빈 등록
	@Bean
	public DataSource dataSource() throws Exception{
		//hikariCP를 이용하여 데이터베이스 연결 처리
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean
	//application.properties 파일에서 jpa 관련 설정만 가져와 Properties 빈 등록
	@ConfigurationProperties(prefix="spring.jpa")
	public Properties hibernateConfig(){
		return new Properties();
	}
	
	//SqlSessionFactory : SqlSession 생성을 담당하는 객체로 데이터베이스와 sql 실행에 대한 모든 정보를 가지고 있고 
	// SqlSessionFactory를 생성해주는 SqlSessionFactoryBean 설정
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		//데이터베이스 연결 선언을 필드에 대입
		sqlSessionFactoryBean.setDataSource(dataSource);
		//myBatis 사용시 xml 파일의 위치를 필드에 대입
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	//SqlSessionTemplate : MyBatis와 스프링 연동 모듈의 핵심으로 SqlSession을 구현하고 SqlSession의 대체 역할을 한다
	//스레드에 안전한 여러개의 DAO나 mapper에서 공유 가능 필요한 시점에 Session Close, Commit, Rollback을 포함한 Session의 생명주기를 담당
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		return new DataSourceTransactionManager(dataSource());
	}
}
