package br.com.shopbra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.shopbra.dto.MercadoPagoResponse;

@FeignClient(name = "MercadoPagoClient", url = "https://api.mercadopago.com")
public interface MercadoPagoClient {
	
	@GetMapping(value = "/v1/payments/{id}")
	public MercadoPagoResponse getPaymentFromMP(@PathVariable("id") String id, @RequestHeader("Authorization") String token);

}
