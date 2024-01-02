package br.com.shopbra.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.shopbra.dto.Game;
import br.com.shopbra.dto.Plataform;
import br.com.shopbra.entity.user.Afiliado;

@Service
public class AffiliateService {

	@Autowired
	private AffiliateUserService service;

	public void createOrUpdatePlataform(String affiliateId, Plataform plataform, boolean delete) {
		Afiliado afiliado = service.getAfiliadoById(affiliateId);
		if (delete) {
			afiliado.getPlataforms().remove(plataform);
		} else {
			if (plataform.getId() != null) {
				for (int i = 0; i < afiliado.getPlataforms().size(); i++) {
					if (afiliado.getPlataforms().get(i).getId().equals(plataform.getId())) {
						afiliado.getPlataforms().remove(i);
					}
				}
			} else {
				plataform.setId(Integer.valueOf("" + (int) Math.floor(Math.random() * 10) + ""
						+ (int) Math.floor(Math.random() * 10) + "" + (int) Math.floor(Math.random() * 10) + ""
						+ (int) Math.floor(Math.random() * 10) + "" + (int) Math.floor(Math.random() * 10) + ""
						+ (int) Math.floor(Math.random() * 10) + "" + (int) Math.floor(Math.random() * 10)));
			}
			afiliado.getPlataforms().add(plataform);
		}
		service.updateAfiliado(afiliado);
	}

	public void updateConfig(String affiliateId, String value) {
		Afiliado afiliado = service.getAfiliadoById(affiliateId);
		afiliado.setPageConfig(value);
		service.updateAfiliado(afiliado);
	}

	public String getConfig(String affiliateId) {
		Afiliado afiliado = service.getAfiliadoById(affiliateId);
		return afiliado.getPageConfig();
	}

	public void createOrUpdateGame(String affiliateId, Game game, boolean delete) {
		Afiliado afiliado = service.getAfiliadoById(affiliateId);
		if (delete) {
			afiliado.getGames().remove(game);
		} else {
			if (game.getGameId() != null) {
				for (int i = 0; i < afiliado.getGames().size(); i++) {
					if (afiliado.getGames().get(i).getGameId().equals(game.getGameId())) {
						afiliado.getGames().remove(i);
					}
				}
			} else {
				game.setGameId(Integer.valueOf("" + (int) Math.floor(Math.random() * 10) + ""
						+ (int) Math.floor(Math.random() * 10) + "" + (int) Math.floor(Math.random() * 10) + ""
						+ (int) Math.floor(Math.random() * 10) + "" + (int) Math.floor(Math.random() * 10) + ""
						+ (int) Math.floor(Math.random() * 10) + "" + (int) Math.floor(Math.random() * 10)));
			}
			afiliado.getGames().add(game);
		}
		service.updateAfiliado(afiliado);
	}

}
