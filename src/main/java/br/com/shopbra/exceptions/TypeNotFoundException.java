package br.com.shopbra.exceptions;

public class TypeNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 935462251826777722L;

	public TypeNotFoundException(String msg) {
		super(msg);
	}

}
