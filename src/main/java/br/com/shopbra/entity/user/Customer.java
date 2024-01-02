package br.com.shopbra.entity.user;

import java.sql.Date;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User{
	
	private static final long serialVersionUID = 4796470016004472433L;
	@CreatedDate
	private Date acquisition;

}
