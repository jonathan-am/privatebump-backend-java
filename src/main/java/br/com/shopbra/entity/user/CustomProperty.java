package br.com.shopbra.entity.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomProperty implements Serializable {
	
	private static final long serialVersionUID = -810665614630715442L;
	private String id;
	private Object value;

}
