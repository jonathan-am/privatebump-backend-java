package br.com.shopbra.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.shopbra.constants.ErrorMessages;
import br.com.shopbra.constants.UserThreads;
import br.com.shopbra.constants.UserTypes;
import br.com.shopbra.dto.LoginSessionDTO;
import br.com.shopbra.dto.UpdateUserRequestDTO;
import br.com.shopbra.dto.UserDTO;
import br.com.shopbra.dto.UserLoginRequestDTO;
import br.com.shopbra.dto.UserLoginResponseDTO;
import br.com.shopbra.entity.user.User;
import br.com.shopbra.exceptions.InvalidSessionException;
import br.com.shopbra.exceptions.TypeNotFoundException;
import br.com.shopbra.exceptions.UpdateUserException;
import br.com.shopbra.exceptions.UserAlreadyExistException;
import br.com.shopbra.exceptions.UserNotFoundException;
import br.com.shopbra.repository.UserRepository;
import br.com.shopbra.services.UserService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserLoginResponseDTO validateAndPerformLogin(UserLoginRequestDTO request) {
		String login = request.getLogin().toLowerCase();
		String password = request.getPassword().toLowerCase();

		User user = repo.getUserByLogin(login)
				.orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND_EXCEPTION));

		UserLoginResponseDTO response = new UserLoginResponseDTO();
		response.setSucess(false);

		if (user.getPassword().equals(password)) {
			response.setSucess(true);
			String sessionId = UUID.randomUUID().toString();
			response.setSessionId(user.getId()+":"+sessionId);
			generateLoginSession(user.getId(), sessionId, user.getType().toString(), (String)user.getCustomProperties().get(0).getValue());
			response.setAffiliateId((String)user.getCustomProperties().get(0).getValue());
		}

		return response;
	}

	@Override
	public UserDTO getUserById(String id) {
		log.info("UserService.getUserById - start - id: [{}]", id);
		User u = repo.getUserById(id).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND_EXCEPTION));
		UserDTO response = new UserDTO(u);
		log.info("UserService.getUserById - end - id: [{}], user: [{}]", id, response);
		return response;
	}

	@Override
	public UserDTO createUser(User ref) {
		if(repo.existsById(ref.getId().toLowerCase())) {
			throw new UserAlreadyExistException("This user already exists.");
		}
		if(repo.existsByLogin(ref.getLogin().toLowerCase())) {
			throw new UserAlreadyExistException("This user already exists. With this login");
		}
		if(repo.existsByEmail(ref.getEmail().toLowerCase())) {
			throw new UserAlreadyExistException("This user already exists. With this email");
		}
		User toSave = new User(ref.getId().replace(".", "").replace("-", ""), generateUserName(), ref.getEmail().toLowerCase(), ref.getLogin().toLowerCase(), ref.getPassword().toLowerCase(), UserTypes.DEFAULT, null, ref.getCustomProperties());
		log.info("UserService.createUser - start - id: [{}]", ref.getId());
		UserDTO response = new UserDTO(repo.save(toSave));
		log.info("UserService.createUser - end sucess - response: [{}]", response);
		return response;
	}
	
	private String generateUserName() {
		return "a"+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10)
	    +""+(int)Math.floor(Math.random() * 10);
	}

	@Override
	public UserDTO updateUser(String id, UpdateUserRequestDTO request) {
		log.info("UserService.updateUser - start - id: [{}], request: [{}]", id, request);
		User user = repo.getUserById(id)
				.orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND_EXCEPTION));

		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			user.setEmail(request.getEmail());
		}
		if ((request.getCustomProperty() != null && request.getCustomProperty().getId() != null
				&& request.getCustomProperty().getValue() != null)) {
			if (user.getCustomProperties().contains(request.getCustomProperty())) {
				user.getCustomProperties().forEach(v -> {
					if (v == request.getCustomProperty())
						user.getCustomProperties().remove(v);
				});
			}
			user.getCustomProperties().add(request.getCustomProperty());
		}
		repo.saveAndFlush(user);
		UserDTO response = new UserDTO(user);
		log.info("UserService.updateUser - end - id: [{}], response: [{}]", id, response);
		return response;
	}

	@Override
	public UserDTO updateUser(String id, String password) {
		log.info("UserService.updateUser - start - id: [{}], password: [{}]", id, hidePassword(password));
		User user = repo.getUserById(id)
				.orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND_EXCEPTION));

		if (password != null && !password.isEmpty()) {
			user.setPassword(password);
		} else {
			throw new UpdateUserException("Password cannot be used.");
		}

		repo.saveAndFlush(user);
		UserDTO response = new UserDTO(user);

		return response;
	}

	@Override
	public LoginSessionDTO getLoginSession(String sessionId) {
		if(UserThreads.loggedUsers.containsKey(sessionId)) {
			return UserThreads.loggedUsers.get(sessionId);
		}
		throw new InvalidSessionException("Session not found!");
	}

	private void validateAndDeleteIfExistsSessionByUserId(String userId) {
		String key = "";
		for(LoginSessionDTO s : UserThreads.loggedUsers.values()) {
			if(s.getUserId().equals(userId)) {
				key = s.getUserId() + ":" + s.getSessionId();
			}
		}
		UserThreads.loggedUsers.remove(key);
	}

	public void generateLoginSession(String userId, String sessionId, String userType, String affiliateId) {
		validateAndDeleteIfExistsSessionByUserId(userId);
		LoginSessionDTO session = new LoginSessionDTO(sessionId, userId, userType,
				LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), affiliateId);
		UserThreads.loggedUsers.put(userId + ":" + sessionId, session);
		System.out.println(session);
	}

	private String hidePassword(String password) {
		String[] old = password.split("(?!^)");
		String result = "";
		for (int i = 0; i < 6; i++) {
			old[i] = "*";
		}
		for (int x = 0; x < old.length; x++) {
			result = result + old[x];
		}
		return result;
	}

	private void validateCustomerType(String customerType) {
		switch (customerType) {
		case "ADMIN":
			break;
		case "CUSTOMER":
			break;
		case "DEFAULT":
			break;
		default:
			log.error("UserService.createUser - error - message: `Type Not Found` customerType: [{}]", customerType);
			throw new TypeNotFoundException(ErrorMessages.TYPE_NOT_FOUND_EXCEPTION);
		}
	}

}
