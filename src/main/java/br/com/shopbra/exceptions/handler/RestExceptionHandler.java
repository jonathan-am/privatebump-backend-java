package br.com.shopbra.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.shopbra.exceptions.InvalidPasswordException;
import br.com.shopbra.exceptions.InvalidSessionException;
import br.com.shopbra.exceptions.InvalidTokenException;
import br.com.shopbra.exceptions.TypeNotFoundException;
import br.com.shopbra.exceptions.UpdateUserException;
import br.com.shopbra.exceptions.UserAlreadyExistException;
import br.com.shopbra.exceptions.UserNotFoundException;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler  extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("500", "Internal Error.");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public final ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("400", "Invalid token or expired.");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	
	@ExceptionHandler(TypeNotFoundException.class)
	public final ResponseEntity<Object> handleTypeNotFoundException(TypeNotFoundException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("400", "Type not found.");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	
	@ExceptionHandler(UpdateUserException.class)
	public final ResponseEntity<Object> handleUpdateUserException(UpdateUserException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("500", "Error while updating user.");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public final ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("400", "User already exists.");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("404", "User not found.");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public final ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("400", "Invalid password.");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(InvalidSessionException.class)
	public final ResponseEntity<Object> handleInvalidSessionException(InvalidSessionException ex, WebRequest request) {
		log.error("An unexpected error occurred: [{}]", ex);
		ExceptionResponse response = new ExceptionResponse("404", "Session not found.");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

}
