package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	//런타임 Exception이 발동하는 모든 것에 대해서 
	public Map<String, String> validationException(CustomValidationException e) {
			return e.getErrorMap();
	}
}
