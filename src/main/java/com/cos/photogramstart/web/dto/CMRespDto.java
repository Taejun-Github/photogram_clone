package com.cos.photogramstart.web.dto;

import java.util.Map;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
	private int code; //1은 성공, -1은 실패이다.
	private String message;
	private T data;
	//왜 제네릭을 써주는 것일까? 만약 정상적으로 데이터가 입력될 시에는 User로 응답해 주어야 하고
	//정상적이지 않은 경우에는 errorMap으로 응답해 주어야 하기 때문이다. 이것은 공통응답 DTO이므로 이렇게 해야 한다.
}
