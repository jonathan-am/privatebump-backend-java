package br.com.shopbra.services;

public interface TokenService {
	
	public void createUserChangeTokenThread(String id);
	
	public void validateToken(String token, String userId);

}
