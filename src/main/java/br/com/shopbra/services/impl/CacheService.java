package br.com.shopbra.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.shopbra.constants.ErrorMessages;
import br.com.shopbra.dto.UserDTO;
import br.com.shopbra.entity.user.User;
import br.com.shopbra.exceptions.UserNotFoundException;
import br.com.shopbra.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CacheService {
	
	
	@Autowired
	private UserRepository repo;
	
	@Cacheable(value = "user", key = "#id")
	public UserDTO getUserById(String id) {
		log.info("UserService.getUserById - start - id: [{}]", id);
		User u = repo.getUserById(id).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND_EXCEPTION));
		UserDTO response = new UserDTO(u);
		log.info("UserService.getUserById - end - id: [{}], user: [{}]", id, response);
		return response;
	}
	
	@CacheEvict(value = "user", key="#id")
	public UserDTO removeUserFromCache(String id) {
		return null;
	}
	
}
