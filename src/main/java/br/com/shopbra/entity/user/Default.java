package br.com.shopbra.entity.user;

import java.sql.Date;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class Default extends User{
	
	private static final long serialVersionUID = -5184136838460328800L;
	private String name;
	private Date lastAcquisition;
	
}
