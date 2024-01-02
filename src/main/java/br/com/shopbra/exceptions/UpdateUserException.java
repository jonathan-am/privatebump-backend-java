package br.com.shopbra.exceptions;

public class UpdateUserException extends RuntimeException {

	private static final long serialVersionUID = 594413799838223538L;
	
	public UpdateUserException(String message) {
		super(message);
	}

}
