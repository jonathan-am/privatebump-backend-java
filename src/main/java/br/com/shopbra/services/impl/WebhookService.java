package br.com.shopbra.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.shopbra.client.MercadoPagoClient;
import br.com.shopbra.constants.UserThreads;
import br.com.shopbra.constants.UserTypes;
import br.com.shopbra.dto.LoginSessionDTO;
import br.com.shopbra.dto.MercadoPagoResponse;
import br.com.shopbra.dto.WebhookEventMessageDTO;
import br.com.shopbra.entity.user.CustomProperty;
import br.com.shopbra.entity.user.User;
import br.com.shopbra.exceptions.UserNotFoundException;
import br.com.shopbra.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class WebhookService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private MercadoPagoClient client;
	
	private String token = "";
	
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	@Async
	public void processWebhook(WebhookEventMessageDTO request) {
		log.info("Starting process webhook. Request[{}]", request);
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				if (request.getAction().equals("payment.updated")) {
					try {
						MercadoPagoResponse response = client.getPaymentFromMP(request.getData().getId(), token);
						if (response.getStatus().equals("approved") && response.getStatusDetail().equals("accredited")) {
							String id = response.getExternalReference();
							updateUser(id);
						}
					}catch(Exception e) {
						log.error("Error while processing webhook... msg - [{}]", e.getMessage());
					}
				}
			}
		}, 1, TimeUnit.MINUTES);
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
	
	@SuppressWarnings("deprecation")
	private void updateUser(String id) {
		User user = repo.getUserById(id).orElseThrow(() -> new UserNotFoundException("User not found."));
		validateAndDeleteIfExistsSessionByUserId(id);
		user.setType(UserTypes.CUSTOMER);
		CustomProperty dateToExpire = new CustomProperty();
		dateToExpire.setId("expireCustomer");
		Date d = new Date();
		dateToExpire.setValue((d.getDay()==0 ? 6 : d.getDay() - 1 ) + "-" + (d.getHours()));
		if(user.getCustomProperties()==null) {
			user.setCustomProperties(new ArrayList<>());
		}
		user.getCustomProperties().add(dateToExpire);
		repo.saveAndFlush(user);

	}

}
