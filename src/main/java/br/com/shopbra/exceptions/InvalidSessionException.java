package br.com.shopbra.exceptions;

public class InvalidSessionException extends RuntimeException{

	private static final long serialVersionUID = 9147405446177236740L;

	public InvalidSessionException(String message) {
		super(message);
	}

}
