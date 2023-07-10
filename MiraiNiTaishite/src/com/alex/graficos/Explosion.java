package com.alex.graficos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.main.Game;

public class Explosion {

	public int x,y;
	public static BufferedImage[] explosion;
	
	public int curFrames = 0, maxFrames = 10, maxAnim = 2, curAnim = 0;
	
	public Explosion(int x,int y) {
		this.x = x;
		this.y = y;
		explosion = new BufferedImage[4];
		explosion[0] = Game.spriteEnemies.getSprite(0, 0, 16, 16);
		explosion[1] = Game.spriteEnemies.getSprite(16, 0, 16, 16);
		explosion[2] = Game.spriteEnemies.getSprite(32, 0, 16, 16);
		explosion[3] = Game.spriteEnemies.getSprite(48, 0, 16, 16);
	}
	
	public void tick() {
		curFrames++;
		if(curFrames == maxFrames) {
			curAnim++;
			if(curAnim == maxAnim) {
				curAnim = 0;
				Game.explosion.remove(this);
			}
			curFrames = 0;
			
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(explosion[curAnim], (int)(x), (int)(y), 32, 32, null);
	}
	
}
