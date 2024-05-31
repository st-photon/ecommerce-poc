package com.photon.user.exceptions;

public class UserException extends RuntimeException {

	public UserException() {
		super("USER NOT FOUND");
	}

	public UserException(String mess) {
		super(mess);
	} 
}
