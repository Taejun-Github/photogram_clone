package com.cos.photogramstart.web.dto.user;


import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	private String name; //필수
	private String password; //필수
	private String website; //선택
	private String bio; //선택
	private String phone; //선택 
	private String gender; //선택
	
	
	//선택과 필수를 구분하기 위해 코드 수정이 필요하다.
	public User toEntity() {
		return User.builder()
				.name(name) //이름을 기재하지 않은 경우에 대비해서 validation 체크가 필요하다.
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
