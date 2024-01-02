package br.com.shopbra.exceptions;

public class InvalidPasswordException extends RuntimeException{
	
	private static final long serialVersionUID = -4605295992350001944L;

	public InvalidPasswordException(String message) {
		super(message);
	}

}
