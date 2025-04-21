package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * @Configuration 
 * - 해당 클래스가 설정용 클래시임을 명시
 * + 객체로 생성해서 내부코드를 서버 실행시 모두 수행
 * */
@Configuration
public class SecurityCongif {

	@Bean // 개발자가 수동으로 생성한 객체의 관리를 스프링에게 위임
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
