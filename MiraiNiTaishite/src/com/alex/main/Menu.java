package com.alex.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	private String[] options = { "inicio", "controles", "opcoes", "creditos", "sair" };

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
	}

	public void render(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		g2.setColor(new Color(0, 0, 0, 80));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		g.setColor(new Color(255, 91, 76));
		g.setFont(new Font("Arial", Font.BOLD, 16 * Game.SCALE));
		g.drawString("Mirai ni Taishite", 60 * Game.SCALE, 24 * Game.SCALE);
		
		g.setColor(new Color(110, 255, 255));
		g.setFont(new Font("Monospaced", Font.BOLD, 16 * Game.SCALE));
		if (isRun == false) {
			g.drawString("Iniciar", 80 * Game.SCALE, 60 * Game.SCALE);
		} else {
			g.drawString("Continuar", 80 * Game.SCALE, 60 * Game.SCALE);
		}

		g.drawString("Controles", 74 * Game.SCALE, 76 * Game.SCALE);
		g.drawString("Opções", 90 * Game.SCALE, 92 * Game.SCALE);
		g.drawString("Créditos", 80 * Game.SCALE, 108 * Game.SCALE);
		g.drawString("Sair", 95 * Game.SCALE, 124 * Game.SCALE);
		
		g.setColor(Color.white);
		
		if (options[currentOption] == "inicio") {
			if (isRun == false) {
				g.fillRect(70*Game.SCALE, 53*Game.SCALE, 5*Game.SCALE, 5*Game.SCALE);
			} else {
				g.fillRect(70*Game.SCALE, 53*Game.SCALE, 5*Game.SCALE, 5*Game.SCALE);
			}
		} else if (options[currentOption] == "controles") {
			g.fillRect(64*Game.SCALE, 69*Game.SCALE, 5*Game.SCALE, 5*Game.SCALE);
		} else if (options[currentOption] == "opcoes") {
			g.fillRect(80*Game.SCALE, 85*Game.SCALE, 5*Game.SCALE, 5*Game.SCALE);
		} else if (options[currentOption] == "creditos") {
			g.fillRect(70*Game.SCALE, 101*Game.SCALE, 5*Game.SCALE, 5*Game.SCALE);
		} else if (options[currentOption] == "sair") {
			g.fillRect(85*Game.SCALE, 117*Game.SCALE, 5*Game.SCALE, 5*Game.SCALE);
		}

	}

}
