package br.com.shopbra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopbra.dto.Game;
import br.com.shopbra.dto.Plataform;
import br.com.shopbra.services.impl.AffiliateService;
import br.com.shopbra.services.impl.AffiliateUserService;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PanelController {
	
	@Autowired
	private AffiliateService service;
	
	@Autowired
	private AffiliateUserService userService;
	
	@GetMapping(value="/panel/plataforms", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Plataform>> getPlataforms(@RequestHeader("affiliateid") String affiliateId) {
		return ResponseEntity.ok(userService.getAfiliadoById(affiliateId).getPlataforms());
	}
	
	@GetMapping(value="/panel/games", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Game>> getGames(@RequestHeader("affiliateid") String affiliateId) {
		return ResponseEntity.ok(userService.getAfiliadoById(affiliateId).getGames());
	}
	
	@PostMapping(value="/panel/plataforms", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createOrUpdatePlataform(@RequestHeader("affiliateid") String affiliateId, @RequestBody Plataform plataform) {
		service.createOrUpdatePlataform(affiliateId, plataform, false);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/panel/games", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createOrUpdateGame(@RequestHeader("affiliateid") String affiliateId, @RequestBody Game game) {
		service.createOrUpdateGame(affiliateId, game, false);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/panel/plataforms/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deletePlataform(@RequestHeader("affiliateid") String affiliateId, @RequestBody Plataform plataform) {
		service.createOrUpdatePlataform(affiliateId, plataform, true);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/panel/games/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteGame(@RequestHeader("affiliateid") String affiliateId, @RequestBody Game game) {
		service.createOrUpdateGame(affiliateId, game, true);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/panel/configuration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateConfiguration(@RequestHeader("affiliateid") String affiliateId, @RequestBody String config) {
		service.updateConfig(affiliateId, config);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value="/panel/configuration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getConfiguration(@RequestHeader("affiliateid") String affiliateId) {
		return ResponseEntity.ok(service.getConfig(affiliateId));
	}
}
