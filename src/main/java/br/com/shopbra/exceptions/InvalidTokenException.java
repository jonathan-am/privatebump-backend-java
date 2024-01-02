package br.com.shopbra.exceptions;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = 8486775490175378389L;
	
	public InvalidTokenException(String message) {
		super(message);
	}

}
