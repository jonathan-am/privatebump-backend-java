package br.com.shopbra.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.shopbra.constants.UserThreads;
import br.com.shopbra.dto.UserDTO;
import br.com.shopbra.exceptions.InvalidTokenException;
import br.com.shopbra.exceptions.UserNotFoundException;
import br.com.shopbra.services.TokenService;
import br.com.shopbra.services.UserService;
import br.com.shopbra.threads.ChangeUserThreadObject;
import br.com.shopbra.threads.UserThread;
import br.com.shopbra.utils.Utils;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserThread userThread;
	

	@Override
	public void createUserChangeTokenThread(String id) {
		log.info("TokenServiceImpl.createUserChangeToken - Start - id: [{}]", id);
		String randomToken = Utils.generateToken();
		ChangeUserThreadObject thread = null;
		
		try {
			UserDTO user = userService.getUserById(id);
			log.info("TokenServiceImpl.createUserChangeToken - Found user with id - id: [{}]", id);
			thread = userThread.createUserThread(id, randomToken, user.getId());
		}catch(UserNotFoundException e) {
			log.info("TokenServiceImpl.createUserChangeToken - User not found, may a new user creation... - id: [{}]", id);
			thread = userThread.createUserThread(id, randomToken, id);
		} finally {
			userThread.deleteUpdateUserThread();
		}
		log.info("TokenServiceImpl.createUserChangeToken - End - id: [{}], thread: [{}]", id, thread);
	}

	@Override
	public void validateToken(String token, String userId) {
		log.info("TokenServiceImpl.validateToken - start - token: [{}], userId: [{}]", token, userId);
		log.info("TokenServiceImpl.validateToken - List all tokens -: [{}]",UserThreads.aliveChanges);
		log.info("TokenServiceImpl.validateToken - Retrieving -: [{}]",UserThreads.aliveChanges.get(token));
		if(UserThreads.aliveChanges.containsKey(token)) {
			if(UserThreads.aliveChanges.get(token).getId().equals(userId)) {
				UserThreads.aliveChanges.remove(token);
				log.info("TokenServiceImpl.validateToken - end - is Valid! - token: [{}], userId: [{}]", token, userId);
				return;
			}
		}
		log.info("TokenServiceImpl.validateToken - end - Not Valid - token: [{}], userId: [{}]", token, userId);
		throw new InvalidTokenException("Token not valid or already used!");
	}
	
}
