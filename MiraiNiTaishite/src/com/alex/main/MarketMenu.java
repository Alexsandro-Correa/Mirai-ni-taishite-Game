package com.alex.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.alex.entities.Player;

public class MarketMenu {
	
	private String[] options = { "arma1", "arma2", "granada", "vida", "/" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public boolean up, down, enter;

	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		
		enter = false;
		if (options[currentOption] == "inicio") {
			Game.gameState = "INICIO";

		} else if (options[currentOption] == "controles") {
			Game.gameState = "CONTROLES";

		} else if (options[currentOption] == "opcoes") {
			Game.gameState = "OPCOES";

		} else if (options[currentOption] == "creditos") {
			Game.gameState = "CREDITOS";

		} else if (options[currentOption] == "sair") {
			Game.gameState = "SAIR";
		}

	
	}
		
	
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		
		g.drawImage(Game.suitcase.getSprite(0, 0, 352, 208), 0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE,
				null);

		g2.setColor(new Color(0, 0, 0, 80));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		
		g.drawImage(Game.guns.getSprite(31, 1, 29, 12), 264 * Game.SCALE, 64 * Game.SCALE, 56 * Game.SCALE,
				24 * Game.SCALE, null);

		/*g.setColor(new Color(255, 91, 76));
		g.setFont(new Font("Arial", Font.BOLD, 16 * Game.SCALE));
		g.drawString("Loja de Itens", 120 * Game.SCALE, 36 * Game.SCALE);
		
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial",Font.ITALIC, 10*Game.SCALE));
		g.drawString("Moedas - " + Player.currentCoins, 255*Game.SCALE,30*Game.SCALE);

		g.setColor(new Color(110, 255, 255));
		g.setFont(new Font("Monospaced", Font.BOLD, 12 * Game.SCALE));

		g.drawString("Arma1 - 10 coins", 120 * Game.SCALE, 76 * Game.SCALE);
		g.drawString("Arma2 - 20 coins", 120 * Game.SCALE, 92 * Game.SCALE);
		g.drawString("Granada - 20 coins", 120 * Game.SCALE, 108 * Game.SCALE);
		g.drawString("Vida - 5 coins", 120 * Game.SCALE, 124 * Game.SCALE);
		g.drawString("////////////////////", 120 * Game.SCALE, 140 * Game.SCALE);
		
		
		g.setColor(Color.white);
		g.fillRect(260*Game.SCALE,66*Game.SCALE, 32*Game.SCALE, 14*Game.SCALE);
		g.fillRect(260*Game.SCALE,82*Game.SCALE, 32*Game.SCALE, 14*Game.SCALE);
		g.fillRect(260*Game.SCALE,98*Game.SCALE, 32*Game.SCALE, 14*Game.SCALE);
		g.fillRect(260*Game.SCALE,114*Game.SCALE, 32*Game.SCALE, 14*Game.SCALE);
		
		g.drawImage(Game.guns.getSprite(0, 1, 29, 12), 262 * Game.SCALE, 67 * Game.SCALE, 28 * Game.SCALE,
				12 * Game.SCALE, null);*/
		
	}
}
