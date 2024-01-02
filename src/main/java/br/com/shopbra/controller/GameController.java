package br.com.shopbra.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopbra.dto.Game;
import br.com.shopbra.dto.GameInfoDTO;
import br.com.shopbra.dto.GameLink;
import br.com.shopbra.dto.Plataform;
import br.com.shopbra.services.impl.GameService;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {
	
	@Autowired
	private GameService service;
	
	@GetMapping(value = "/game/info/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameInfoDTO> getActualHackedGame(@PathVariable("gameId") Integer gameId) throws IOException {
		return ResponseEntity.ok(service.getActualHackedGame(gameId));
	}
	
	@GetMapping(value = "/game/list/{affiliateId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Game>> getGameList(@PathVariable("affiliateId") String affiliateId) {
		List<Game> games = service.getGames(affiliateId);
		return ResponseEntity.ok(games);
	}
	
	@GetMapping(value = "/plataform/list/{affiliateId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Plataform>> getPlataformList(@PathVariable("affiliateId") String affiliateId) {
		List<Plataform> plataforms = service.getPlataforms(affiliateId);
		return ResponseEntity.ok(plataforms);
	}
	
	@GetMapping(value = "/game/current", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameLink> getCurrentGame(@RequestHeader("affiliateid") String affiliateId,  @RequestHeader("gameid") String gameid, @RequestHeader("plataformid") String plataformid) {
		return ResponseEntity.ok(service.getCurrentGame(affiliateId, gameid, plataformid));
	}

}
