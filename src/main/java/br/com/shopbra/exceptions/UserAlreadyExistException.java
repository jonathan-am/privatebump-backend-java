package br.com.shopbra.exceptions;

public class UserAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1371481720969942925L;
	
	public UserAlreadyExistException(String message) {
		super(message);
	}

}
