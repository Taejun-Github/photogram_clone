package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	//리턴이 되면 자동으로 세션을 만들어준다.
	//패스워드는 알아서 체킹하므로 신경쓸 필요가 없다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// .loginProcessingUrl("/auth/signin")를 Config에서 설정하면 IoC 컨테이너의 UserDetailsService에서 처리한다.
		//그런 처리 과정을 여기서 오버라이딩해서 구현하는 것이다.
		//PricipalDetailsService라는 타입이 UserDetailsService를 구현했기 때문에 처리 과정에서 UserDetailsService를 쓰지 않고
		//여기에서 만든  PrincipalDetailsService를 사용한다.
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		} else {
			return new PrincipalDetails(userEntity);
		}
	}
	
}
