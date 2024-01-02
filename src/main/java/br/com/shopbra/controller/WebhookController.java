package br.com.shopbra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopbra.dto.WebhookEventMessageDTO;
import br.com.shopbra.services.impl.WebhookService;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebhookController {
	
	@Autowired
	private WebhookService service;
	
	@PostMapping(value = "/v1/webhook/payment")
	public void receiveWebhookEvent(@RequestBody WebhookEventMessageDTO request) {
		service.processWebhook(request);
	}

}
