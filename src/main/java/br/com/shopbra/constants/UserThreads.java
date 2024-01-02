package br.com.shopbra.constants;

import java.util.HashMap;
import java.util.Map;

import br.com.shopbra.dto.LoginSessionDTO;
import br.com.shopbra.threads.ChangeUserThreadObject;

public class UserThreads {
	
	public static Map<String, ChangeUserThreadObject> aliveChanges = new HashMap<>();
	public static Map<String, LoginSessionDTO> loggedUsers = new HashMap<>();
	public static Long timeToDeleteThread = (long) 180000;

}
