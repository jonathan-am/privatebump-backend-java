package br.com.shopbra.exceptions;

public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1206596374675564518L;

	public UserNotFoundException(String msg) {
		super(msg);
	}

}
