package br.com.shopbra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.shopbra.entity.user.Afiliado;

@Repository
public interface AffiliateRepository extends JpaRepository<Afiliado, String>{
	
}
