package br.com.shopbra.dto;

import br.com.shopbra.entity.user.CustomProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO {
	
	private String email;
	private CustomProperty customProperty;

}
