package br.com.shopbra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MercadoPagoResponse {
	
	//	"external_reference": "27130637013",
	//	"status": "approved",
	//	"status_detail": "accredited",
	@JsonProperty("external_reference")
	private String externalReference;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("status_detail")
	private String statusDetail;

}
