package com.photon.user.entity;

import org.springframework.http.HttpStatus;

public class ResponseApi {

	
private String message;
private  HttpStatus  statusCode;
public ResponseApi(String message, HttpStatus statusCode) {
	super();
	this.message = message;
	this.statusCode = statusCode;
}
public ResponseApi() {
	super();
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public HttpStatus getStatusCode() {
	return statusCode;
}
public void setStatusCode(HttpStatus statusCode) {
	this.statusCode = statusCode;
}

}
