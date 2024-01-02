package br.com.shopbra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GameDTO {
	
	@JsonProperty("game_title")
	private String gameTitle;
	
	@JsonProperty("game_id")
	private Integer gameId;
	
	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("percentage")
	private Integer percentage;

}
