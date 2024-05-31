package com.photon.infrastructure.exceptions;


import com.photon.user.entity.ResponseApi;
import com.photon.user.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler  {

	@ExceptionHandler(UserException.class)
	public ResponseApi exceptionHandler(UserException ex ) {
		ResponseApi res = new ResponseApi();
		res.setMessage(ex.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND);
		return null;
		
		
	}
}
