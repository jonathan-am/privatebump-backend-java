package br.com.shopbra.dto;

import lombok.Data;

@Data
public class UserLoginResponseDTO {
	
	private Boolean sucess;
	private String sessionId;
	private String affiliateId;

}
