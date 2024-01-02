package br.com.shopbra.threads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserThreadObject {
	
	private String id;
	private String token;
	private String userId;

}
