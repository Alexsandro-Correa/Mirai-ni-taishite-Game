package com.alex.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pause {

	private String[] options = { "continuar", "controles", "opcoes", "creditos", "sair" };
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public boolean up, down, enter;
	public static boolean isRun = false;

	public void tick() {
		if (up) {
			up = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
		}

		if (down) {
			down = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if (enter) {
			enter = false;
			if (options[currentOption] == "continuar") {
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
	}

	public void render(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		g.drawImage(Game.background.getSprite(0, 0, 352, 208), 0, 0, Game.WIDTH * Game.SCALE,
				Game.HEIGHT * Game.SCALE, null);

		g2.setColor(new Color(0, 0, 0, 80));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		g.setColor(new Color(255, 91, 76));
		g.setFont(new Font("Arial", Font.BOLD, 16 * Game.SCALE));
		g.drawString("//////////////////////////", 120 * Game.SCALE, 36 * Game.SCALE);

		g.setColor(new Color(110, 255, 255));
		g.setFont(new Font("Monospaced", Font.BOLD, 16 * Game.SCALE));

		g.drawString("Continuar", 134 * Game.SCALE, 76 * Game.SCALE);
		g.drawString("Controles", 134 * Game.SCALE, 92 * Game.SCALE);
		g.drawString("Opções", 150 * Game.SCALE, 108 * Game.SCALE);
		g.drawString("Créditos", 140 * Game.SCALE, 124 * Game.SCALE);
		g.drawString("Sair", 155 * Game.SCALE, 140 * Game.SCALE);

		if (options[currentOption] == "continuar") {
			g.drawImage(Game.guns.getSprite(0, 1, 29, 12), 114 * Game.SCALE, 69 * Game.SCALE, 14 * Game.SCALE,
					6 * Game.SCALE, null);
		} else if (options[currentOption] == "controles") {
			g.drawImage(Game.guns.getSprite(0, 1, 29, 12), 114 * Game.SCALE, 85 * Game.SCALE, 14 * Game.SCALE,
					6 * Game.SCALE, null);
		} else if (options[currentOption] == "opcoes") {
			g.drawImage(Game.guns.getSprite(0, 1, 29, 12), 130 * Game.SCALE, 101 * Game.SCALE, 14 * Game.SCALE,
					6 * Game.SCALE, null);
		} else if (options[currentOption] == "creditos") {
			g.drawImage(Game.guns.getSprite(0, 1, 29, 12), 120 * Game.SCALE, 117 * Game.SCALE, 14 * Game.SCALE,
					6 * Game.SCALE, null);
		} else if (options[currentOption] == "sair") {
			g.drawImage(Game.guns.getSprite(0, 1, 29, 12), 135 * Game.SCALE, 133 * Game.SCALE, 14 * Game.SCALE,
					6 * Game.SCALE, null);
		}

	}
}
