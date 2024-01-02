package br.com.shopbra.exceptions.handler;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse implements Serializable{

	private static final long serialVersionUID = -6118390182783068423L;
	
	private String status;
	private String message;
	
	

}
