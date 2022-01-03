package com.cos.photogramstart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //DI를 할 때 사용하는 lombok 어노테이션이다. 생성자 역할을 한다.
@Controller //IoC에 등록이 되었다는 의미, 파일을 리턴하는 컨트롤러가 된다(Rest는 데이터를 리턴)
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//회원가입 버튼 클릭시 /auth/signup으로 이동한다. 그리고 회원가입 후에 /auth/signin으로 리턴된다.
	@PostMapping("/auth/signup")
	public String signup(SignupDto signupDto) {
		//해당 dto를 받을 때 기본적으로 x-www-form-urlencoded 형태로 받는다.
		log.info(signupDto.toString());
		//이렇게 로그로 할 수 있다.
		User user = signupDto.toEntity(); //이렇게 가져온 dto를 도메인으로 변환하는 작업이 필요하다. 
		//변환하는 함수는 dto 내에 정의한다.
		log.info(user.toString());
		User userEntity = authService.회원가입(user);
		System.out.println(userEntity);
		return "auth/signin";
	}
}
