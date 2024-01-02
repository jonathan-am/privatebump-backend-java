package br.com.shopbra.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data 
public class GameInfoDTO {
	
	@JsonProperty("game_title")
	private String gameTitle;
	
	@JsonProperty("game_plxr")
	private String gamePlxr;
	
	@JsonProperty("game_rounds")
	private List<String> gameRounds;
	
	@JsonProperty("global_atack")
	private String globalAtack;

}
