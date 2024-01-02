package br.com.shopbra.entity.user;

import java.io.Serializable;
import java.util.List;

import br.com.shopbra.dto.Game;
import br.com.shopbra.dto.Plataform;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Afiliado implements Serializable {
	
	private static final long serialVersionUID = 7237739581621798559L;
	@Id
	private String id;
	private String pageConfig;
	private List<Plataform> plataforms;
	private List<Game> games;

}
