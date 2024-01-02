package br.com.shopbra.dto;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
	
	private String login;
	private String password;

}
