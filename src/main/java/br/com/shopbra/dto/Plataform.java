package br.com.shopbra.dto;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Plataform implements Serializable {
	
	private static final long serialVersionUID = 3428019351213689437L;
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String name;
	private String accessLink;
	private String image;
	private Boolean hot;

}
