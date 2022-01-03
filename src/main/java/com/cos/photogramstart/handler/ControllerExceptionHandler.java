package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.web.dto.CMRespDto;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	//런타임 Exception이 발동하는 모든 것에 대해서 
	public CMRespDto<Map<String, String>> validationException(CustomValidationException e) {
		//여기서는 errorMap을 담아야 하므로 CMRespDto의 제네릭을 map으로 설정해 준다.
			return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
			//만약 CMRespDto의 세번째 인자에다가 String을 넣고 싶다면?
			//함수의 반환타입을 CMResDto<String> 타입으로 설정해주면 된다.
			//그리고 아무거나 넣고 싶다면 CMRespDto<?>를 써주면 된다.
	}
}
