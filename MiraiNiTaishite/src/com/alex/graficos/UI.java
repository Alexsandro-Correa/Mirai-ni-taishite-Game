package com.alex.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.alex.entities.Entity;
import com.alex.entities.Player;
import com.alex.main.Game;

public class UI {

	public void render(Graphics g) {
		
		if(Player.life == 3) {
			g.drawImage(Entity.HEART, 6*Game.SCALE, 6*Game.SCALE, 4*Game.SCALE, 4*Game.SCALE, null);
			g.drawImage(Entity.HEART, 10*Game.SCALE, 6*Game.SCALE, 4*Game.SCALE, 4*Game.SCALE, null);
			g.drawImage(Entity.HEART, 14*Game.SCALE, 6*Game.SCALE, 4*Game.SCALE, 4*Game.SCALE, null);
		}else if(Player.life == 2) {
			g.drawImage(Entity.HEART, 10*Game.SCALE, 6*Game.SCALE, 4*Game.SCALE, 4*Game.SCALE, null);
			g.drawImage(Entity.HEART, 6*Game.SCALE, 6*Game.SCALE, 4*Game.SCALE, 4*Game.SCALE, null);
		}else {
			g.drawImage(Entity.HEART, 6*Game.SCALE, 6*Game.SCALE, 4*Game.SCALE, 4*Game.SCALE, null);
		}
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial", Font.BOLD, 3*Game.SCALE));
		g.drawString("Coin: " + Player.currentCoins ,  75*Game.SCALE,9*Game.SCALE);
	}

}