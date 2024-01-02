package br.com.shopbra.entity.user;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Administrator extends User {
	
	private static final long serialVersionUID = 3692909553455397630L;
	private String name;
	private Boolean isOp;

}
