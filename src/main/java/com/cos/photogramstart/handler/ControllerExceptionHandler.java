package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	// 런타임 Exception이 발동하는 모든 것에 대해서
	public String validationException(CustomValidationException e) {

		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		} else {
			return Script.back(e.getMessage().toString());
		}

		// 공통응답 dto로 응답하는 방법은 이렇다.
		// return new CMRespDto<Map<String, String>>(-1, e.getMessage(),
		// e.getErrorMap());
		// 만약 CMRespDto의 세번째 인자에다가 String을 넣고 싶다면?
		// 함수의 반환타입을 CMResDto<String> 타입으로 설정해주면 된다.
		// 그리고 아무거나 넣고 싶다면 CMRespDto<?>를 써주면 된다.

		// 정리
		// CMRespDto, Script를 비교하면?
		// 1. 클라이언트에게 응답할 때는 Script가 좋다.
		// 2. Ajax 통신 - CMRespDto가 좋다
		// 3. Android 통신 - CMRespDto가 좋다.
	}

	@ExceptionHandler(CustomValidationApiException.class)
	// 런타임 Exception이 발동하는 모든 것에 대해서
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(CustomApiException.class)
	// 런타임 Exception이 발동하는 모든 것에 대해서
	public ResponseEntity<?> validationApiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);

	}
}
