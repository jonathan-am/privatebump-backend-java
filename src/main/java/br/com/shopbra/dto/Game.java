package br.com.shopbra.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Game implements Serializable {
	
	private static final long serialVersionUID = -2741117744258984142L;
	private Integer gameId;
	private String gameTitle;
	private String gameImage;
	private List<GameLink> gameLinks;

}
