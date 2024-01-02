package br.com.shopbra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopbra.entity.user.Afiliado;
import br.com.shopbra.services.impl.AffiliateUserService;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AffiliateController {
	
	@Autowired
	private AffiliateUserService service;
	
	@GetMapping("/afiliado/{affiliateId}")
	public ResponseEntity<Afiliado> getAfiliadoById(@PathVariable("affiliateId") String affiliateId) {
		return ResponseEntity.ok(service.getAfiliadoById(affiliateId));
	}
	
	@PostMapping("/afiliado")
	public ResponseEntity<Afiliado> createAfiliado(@RequestBody Afiliado afiliado) {
		return ResponseEntity.ok(service.createAfiliado(afiliado));
	}
	
	@PutMapping("/afiliado")
	public ResponseEntity<Void> updateAfiliado(@RequestBody Afiliado afiliado) {
		service.updateAfiliado(afiliado);
		return ResponseEntity.ok().build();
	}

}
