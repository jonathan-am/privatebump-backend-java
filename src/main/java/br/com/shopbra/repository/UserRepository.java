package br.com.shopbra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.shopbra.constants.UserTypes;
import br.com.shopbra.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	
	Optional<User> getUserById(String id);
	
	Optional<User> getUserByLogin(String login);
	
	Boolean existsByLogin(String login);
	
	Boolean existsByEmail(String email);
	
	List<User> findAllByType(UserTypes type);
	
	
}
