package br.com.shopbra.threads;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.shopbra.constants.UserThreads;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class UserThread {
	
	private String token;
	
	@Async
	public void deleteUpdateUserThread() {
			new Thread(token) {
				@Override
				public void run() {
					try {
						sleep(UserThreads.timeToDeleteThread);
					} catch (InterruptedException e) {
						log.error("Error while running thread.");
					}
					log.info("UserThread.deleteUpdateUserThread - Start deleting userThread - token: [{}]", token);
					if(UserThreads.aliveChanges.containsKey(token)) UserThreads.aliveChanges.remove(token);
					log.info("UserThread.deleteUpdateUserThread - End with sucess deleting userThread - token: [{}]", token);
				};
			}.run();
	}
	
	public ChangeUserThreadObject createUserThread(String id, String token, String userId) {
		log.info("UserThread.createUserThread - Start - id: [{}], token: [{}], userId: [{}]", id, token, userId);
		this.token = token;
		ChangeUserThreadObject th = new ChangeUserThreadObject(id, token, userId);
		UserThreads.aliveChanges.put(token, th);
		log.info("UserThread.createUserThread - End - id: [{}], UserThreads: [{}]", id, UserThreads.aliveChanges);
		return th;
	}

}
