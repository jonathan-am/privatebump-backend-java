package br.com.shopbra.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.shopbra.entity.user.Afiliado;
import br.com.shopbra.exceptions.UserNotFoundException;
import br.com.shopbra.repository.AffiliateRepository;

@Service
public class AffiliateUserService {
	
	@Autowired
	private AffiliateRepository repo;
	
	@Cacheable(value = "afiliado", key="#id")
	public Afiliado getAfiliadoById(String id) {
		return repo.findById(id).orElseThrow(() -> new UserNotFoundException("Afiliado nao encontrado"));
	}
	
	@CacheEvict(value = "afiliado", key="#afiliado.id")
	public void updateAfiliado(Afiliado afiliado) {
		repo.saveAndFlush(afiliado);
	}

	public Afiliado createAfiliado(Afiliado afiliado) {
		return repo.save(afiliado);
	}

}
