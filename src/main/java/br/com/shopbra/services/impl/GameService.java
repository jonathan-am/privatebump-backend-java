package br.com.shopbra.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.shopbra.constants.GameThreads;
import br.com.shopbra.dto.Game;
import br.com.shopbra.dto.GameDTO;
import br.com.shopbra.dto.GameInfoDTO;
import br.com.shopbra.dto.GameLink;
import br.com.shopbra.dto.Plataform;
import br.com.shopbra.entity.user.Afiliado;
import br.com.shopbra.utils.JsonUtils;

@Service
public class GameService {
	
	@Autowired
	private AffiliateUserService service;
	
	public List<Game> getGames(String id) {
		Afiliado afiliado = service.getAfiliadoById(id);
		return afiliado.getGames();
	}
	
	public List<Plataform> getPlataforms(String id) {
		Afiliado afiliado = service.getAfiliadoById(id);
		return afiliado.getPlataforms();
	}
	
	public GameLink getCurrentGame(String affiliateId, String gameid, String plataformid) {
		Afiliado afiliado = service.getAfiliadoById(affiliateId);
		for(int i =0; i< afiliado.getGames().size(); i++) {
			if(afiliado.getGames().get(i).getGameId().equals(Integer.valueOf(gameid))) {
				for(int x = 0; x < afiliado.getGames().get(i).getGameLinks().size(); x++) {
					if(afiliado.getGames().get(i).getGameLinks().get(x).getPlataform().equals(plataformid)) {
						return afiliado.getGames().get(i).getGameLinks().get(x);
					}
				}
			}
		}
		for(int z = 0; z < afiliado.getPlataforms().size(); z++) {
			if(afiliado.getPlataforms().get(z).getId().equals(Integer.valueOf(plataformid))) {
				return new GameLink(plataformid, afiliado.getPlataforms().get(z).getAccessLink());
			}
		}
		return null;
	}

	public GameInfoDTO getActualHackedGame(Integer gameId) throws IOException {
		if (GameThreads.alreadyHacked.containsKey(gameId)) {
			return GameThreads.alreadyHacked.get(gameId);
		}

		List<GameDTO> lista = getGameList();

		GameInfoDTO gameInfo = new GameInfoDTO();

		for (GameDTO game : lista) {
			if (game.getGameId().equals(gameId)) {
				int prob = (int) ((Math.floor(Math.random() * 6) + 44) + game.getPercentage() - 48);
				List<String> rounds = new ArrayList<>();

				if (prob > 80) {
					rounds.add("1ª Normal: " + (int) (Math.floor(Math.random() * 6) + 14));
					rounds.add("2ª Turbo: " + (int) (Math.floor(Math.random() * 8) + 16));
					rounds.add("3ª Normal: " + (int) (Math.floor(Math.random() * 7) + 13));
				} else if (prob < 60) {
					rounds.add("1ª Normal: " + (int) (Math.floor(Math.random() * 26) + 19));
					rounds.add("2ª Turbo: " + (int) (Math.floor(Math.random() * 28) + 18));
					rounds.add("3ª Normal: " + (int) (Math.floor(Math.random() * 27) + 17));
				} else {
					rounds.add("1ª Normal: " + (int) (Math.floor(Math.random() * 16) + 14));
					rounds.add("2ª Turbo: " + (int) (Math.floor(Math.random() * 18) + 16));
					rounds.add("3ª Normal: " + (int) (Math.floor(Math.random() * 17) + 13));
				}

				int d = new Date().getHours();
				int m = new Date().getMinutes();
				if (d == 1 && m > 10 || d > 1 && d < 3) {
					GameThreads.nextAtack = "03";
				} else if (d == 3 && m > 10 || d > 3 && d < 6) {
					GameThreads.nextAtack = "06";
				} else if (d == 6 && m > 10 || d > 6 && d < 9) {
					GameThreads.nextAtack = "09";
				} else if (d == 9 && m > 10 || d > 9 && d < 12) {
					GameThreads.nextAtack = "12";
				} else if (d == 12 && m > 10) {
					GameThreads.nextAtack = "13";
				} else if (d == 13 && m > 10 || d > 13 && d < 15) {
					GameThreads.nextAtack = "15";
				} else if (d == 15 && m > 10 || d > 15 && d < 17) {
					GameThreads.nextAtack = "17";
				} else if (d == 17 && m > 10) {
					GameThreads.nextAtack = "18";
				} else if (d == 18 && m > 10 || d > 18 && d < 21) {
					GameThreads.nextAtack = "21";
				} else if (d == 21 && m > 10) {
					GameThreads.nextAtack = "01";
				}

				gameInfo.setGamePlxr(prob + "%");
				gameInfo.setGameTitle(game.getGameTitle());
				gameInfo.setGameRounds(rounds);
				gameInfo.setGlobalAtack(GameThreads.nextAtack + ":00");

				GameThreads.alreadyHacked.put(gameId, gameInfo);

				deleteAlreadyHacked(gameId);

				return gameInfo;
			}
		}
		return null;
	}

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Async
	private void deleteAlreadyHacked(Integer gameId) {
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				if (GameThreads.alreadyHacked.containsKey(gameId))
					GameThreads.alreadyHacked.remove(gameId);
			}
		}, 3, TimeUnit.MINUTES);

	}

	private List<GameDTO> getGameList() throws IOException {
		List<GameDTO> dsa = JsonUtils.getObjectMapper().readValue(new ClassPathResource("GameList.json").getInputStream(),new TypeReference<List<GameDTO>>() {});

		Date ds = new Date();
		int horaAtual = ds.getHours();
		int diaDaSemana = ds.getDay();
		int diaDoMes = ds.getDate();

		int percentage = ((horaAtual > 0 && horaAtual <= 7) || (horaAtual == 12 || horaAtual == 13) || (horaAtual == 15)
				|| (horaAtual >= 18 && horaAtual <= 23)) ? 70 : 55;
		int percentPerSemana = (diaDaSemana == 0 || diaDaSemana == 5 || diaDaSemana == 6) ? 15 : 5;
		int percentPerMonth = ((diaDoMes >= 1 && diaDoMes < 10) || (diaDoMes >= 20 && diaDoMes <= 23)
				|| (diaDoMes >= 29 && diaDoMes <= 31)) ? 5 : 0;
		int percentageFinal = percentage + percentPerSemana + percentPerMonth;

		dsa.forEach((v) -> {
			v.setPercentage((int) (percentageFinal + (Math.floor(Math.random() * 8))));
		});
		return dsa;
	}

}
