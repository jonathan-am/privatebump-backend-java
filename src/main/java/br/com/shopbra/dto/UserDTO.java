package br.com.shopbra.dto;

import java.io.Serializable;
import java.util.List;

import br.com.shopbra.constants.UserTypes;
import br.com.shopbra.entity.user.CustomProperty;
import br.com.shopbra.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = 1673183296901463394L;
	
	private String id;
	private String username;
	private String login;
	private String email;
	private UserTypes type;
	private List<CustomProperty> customProperties;
	
	public UserDTO(User user) {
		this.email = user.getEmail();
		this.type = user.getType();
		this.customProperties = user.getCustomProperties();
		this.id = user.getId();
		this.login = user.getLogin();
		this.username = user.getUsername();
	}

}
