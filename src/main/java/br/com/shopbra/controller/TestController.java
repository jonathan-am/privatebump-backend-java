package br.com.shopbra.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class TestController {
	
//	@Autowired
//	private JDAService service;
	
	@GetMapping(value = "ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> teste() {
		log.debug("Health Check . . . ");
		return ResponseEntity.ok("Pong!");
	}

}
