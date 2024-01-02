package br.com.shopbra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSessionDTO {
	
	private String sessionId;
	
	private String userId;
	
	private String userType;
	
	private String loginAt;
	
	private String affiliateId;

}
