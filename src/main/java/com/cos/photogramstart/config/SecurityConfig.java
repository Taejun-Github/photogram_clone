package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity //해당 파일로 시큐리티를 활성화한다는 뜻의 어노테이션이다.
@Configuration // IoC 메모리에 등록한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final OAuth2DetailsService oauth2DetailsService;
	
	//IoC 컨테이너가 들고있게 된다.
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	//pom.xml에서 security를 dependency에 추가하면 여기에 따로 허용하는 페이지를 만들기 전까지 모든 페이지를 login 페이지로 리다이렉트한다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		//super를 없애면 - 기존 시큐리티가 가지고 있는 기능이 전부 비활성화된다. 즉, 리다이렉트를 하지 않는다.
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**, ", "/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin") //GET으로 접근했을 때
			.loginProcessingUrl("/auth/signin") //POST로 접근했을 때 스프링 시큐리티가 로그인 프로세스를 진행한다.
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login() //form로그인도 하는데, oauth2 로그인도 한다는 뜻이다.
			.userInfoEndpoint() // oauth2 로그인을 하면 최종 응답을 회원정보를 바로 받을 수 있다.
			.userService(oauth2DetailsService);
		//어떤 뜻? antMatchers에서 지정한 링크는 로그인을 요구하고 나머지 링크는 허용한다.
		//그리고 로그인으로 할 페이지는 /auth/signin 페이지로 간다.
		//그리고 로그인이 정상적으로 되면 / 페이지로 이동한다.
	}
}
