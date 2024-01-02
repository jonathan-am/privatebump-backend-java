package br.com.shopbra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopbra.dto.LoginSessionDTO;
import br.com.shopbra.dto.UpdateUserRequestDTO;
import br.com.shopbra.dto.UserDTO;
import br.com.shopbra.dto.UserLoginRequestDTO;
import br.com.shopbra.dto.UserLoginResponseDTO;
import br.com.shopbra.entity.user.User;
import br.com.shopbra.services.TokenService;
import br.com.shopbra.services.UserService;
import br.com.shopbra.services.impl.CacheService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping(value="v1/sessions/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginSessionDTO> getLoginSessionBySessionId(@PathVariable("sessionId") String sessionId) {
		return ResponseEntity.ok(service.getLoginSession(sessionId));
	}
	
	@GetMapping(value = "v1/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) {
		UserDTO response = cacheService.getUserById(userId);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@RequestBody User request) {
		log.info("UserController.createUser - start - id[{}]", request.getId());
		UserDTO response = service.createUser(request);
		log.info("UserController.createUser - end - response[{}]", response);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
//	@PutMapping(value = "v1/user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserRequestDTO request,
//			@PathVariable("userId") String userId,
//			@RequestParam("token") String token) {
//		tokenService.validateToken(token, userId);
//		log.info("UserController.updateUser - start - id[{}]", userId);
//		UserDTO response = service.updateUser(userId, request);
//		log.info("UserController.updateUser - end - response[{}]", response);
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
//	}
//	
//	@PutMapping(value = "v2/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<UserDTO> updateUserPassword(@RequestParam("newPassword") String newPassword,
//			@PathVariable("userId") String userId,
//			@RequestParam("token") String token) {
//		tokenService.validateToken(token, userId);
//		log.info("UserController.updateUserPassword - start - id[{}]", userId);
//		UserDTO response = service.updateUser(userId, newPassword);
//		log.info("UserController.updateUserPassword - end - response[{}]", response);
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
//	}
	
	@PostMapping(value = "v1/user/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO request) {
		UserLoginResponseDTO performLogin = service.validateAndPerformLogin(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(performLogin);
	}
	
//	@PostMapping(value = "v1/user/token", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Void> createThreadToken(@RequestParam("userId") String userId) {
//		log.info("UserController.createThreadToken - start - id[{}]", userId);
//		tokenService.createUserChangeTokenThread(userId);
//		log.info("UserController.createThreadToken - end - userId[{}]", userId);
//		return ResponseEntity.status(HttpStatus.CREATED).build();
//	}

}
