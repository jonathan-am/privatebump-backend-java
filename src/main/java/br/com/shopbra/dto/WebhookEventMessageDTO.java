package br.com.shopbra.dto;

import lombok.Data;

@Data
public class WebhookEventMessageDTO {
	
	private String action;
	private DataDTO data;
	
}
