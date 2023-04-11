

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.alex.entities.Entity;
import com.alex.entities.Player;
import com.alex.main.Game;

public class UI {

	public void render(Graphics g) {
		
		if(Player.life == 3) {
			g.drawImage(Entity.HEART, 6*Game.SCALE, 6*Game.SCALE, 2*Game.SCALE, 3*Game.SCALE, null);
			g.drawImage(Entity.HEART, 9*Game.SCALE, 6*Game.SCALE, 2*Game.SCALE, 3*Game.SCALE, null);
			g.drawImage(Entity.HEART, 12*Game.SCALE, 6*Game.SCALE, 2*Game.SCALE, 3*Game.SCALE, null);
		}else if(Player.life == 2) {
			g.drawImage(Entity.HEART, 6*Game.SCALE, 6*Game.SCALE, 2*Game.SCALE, 3*Game.SCALE, null);
			g.drawImage(Entity.HEART, 9*Game.SCALE, 6*Game.SCALE, 2*Game.SCALE, 3*Game.SCALE, null);
		}else {
			g.drawImage(Entity.HEART, 6*Game.SCALE, 6*Game.SCALE, 2*Game.SCALE, 3*Game.SCALE, null);
		}
		//g.setColor(Color.white);
		//g.drawRect(10, 30, 200, 30);
		g.setColor(Color.cyan);
		g.setFont(new Font("Arial", Font.BOLD, 3*Game.SCALE));
		g.drawString("Moedas: "+ Player.currentCoins + " / " + Player.maxCoins, 40*Game.SCALE,8*Game.SCALE);
	}

}
