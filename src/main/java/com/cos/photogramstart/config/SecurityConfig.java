package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity //해당 파일로 시큐리티를 활성화한다는 뜻의 어노테이션이다.
@Configurable // IoC 메모리에 등록한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//pom.xml에서 security를 dependency에 추가하면 여기에 따로 허용하는 페이지를 만들기 전까지 모든 페이지를 login 페이지로 리다이렉트한다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		//super를 없애면 - 기존 시큐리티가 가지고 있는 기능이 전부 비활성화된다. 즉, 리다이렉트를 하지 않는다.
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")
			.defaultSuccessUrl("/");
		//어떤 뜻? antMatchers에서 지정한 링크는 로그인을 요구하고 나머지 링크는 허용한다.
		//그리고 로그인으로 할 페이지는 /auth/signin 페이지로 간다.
		//그리고 로그인이 정상적으로 되면 / 페이지로 이동한다.
	}
}
