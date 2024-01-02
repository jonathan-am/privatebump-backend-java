package br.com.shopbra.threads;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.shopbra.constants.UserThreads;
import br.com.shopbra.constants.UserTypes;
import br.com.shopbra.dto.LoginSessionDTO;
import br.com.shopbra.entity.user.CustomProperty;
import br.com.shopbra.entity.user.User;
import br.com.shopbra.repository.UserRepository;

@Component
public class CustomerExpireSystem {
	
	@Autowired
	private UserRepository repo;
	
	@SuppressWarnings("deprecation")
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
	public void verifyAndExpireCustomers() {
		for(User user : repo.findAllByType(UserTypes.CUSTOMER)) {
			if(user.getCustomProperties().size() > 0) {
				for(CustomProperty property : user.getCustomProperties()) {
					if(property.getId().equals("expireCustomer")) {
						int dia = Integer.valueOf(((String)property.getValue()).split("-")[0]);
						int hour = Integer.valueOf(((String)property.getValue()).split("-")[1]);
						Date d = new Date();
						if(dia==d.getDay() && hour==d.getHours()) {
							updateUser(user.getId());
						}
					}
				}
			}else {
				updateUser(user.getId());
			}
		}
	}
	
	
	private void updateUser(String id) {
		validateAndDeleteIfExistsSessionByUserId(id);
		User user = repo.getUserById(id).get();
		user.getCustomProperties().removeIf((value)-> value.getId().equals("expireCustomer"));
		user.setType(UserTypes.DEFAULT);
		repo.saveAndFlush(user);
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

}
