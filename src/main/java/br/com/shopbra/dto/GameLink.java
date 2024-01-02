package br.com.shopbra.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameLink implements Serializable{
	private static final long serialVersionUID = -1507193533959281366L;
	private String plataform;
	private String redirectUrl;
}
