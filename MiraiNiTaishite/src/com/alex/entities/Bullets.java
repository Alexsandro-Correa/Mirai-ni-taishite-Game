package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.main.Game;
import com.alex.world.Camera;
import com.alex.world.World;

public class Bullets extends Entity {

	private double dx;
	private double dy;
	private double spd = 4;

	public static BufferedImage bulletSprite;
	public static BufferedImage bulletRight;
	public static BufferedImage bulletLeft;
	
	public static boolean right,left;

	public static int[] pixelsBullet;

	public Bullets(int x, int y, int width, int height, double speed, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, speed, sprite);
		this.dx = dx;
		this.dy = dy;

		bulletRight = Game.guns.getSprite(70, 3, 6, 4);
		bulletLeft = Game.guns.getSprite(77, 3, 6, 4);

	}

	public void tick() {


		if (bulletSprite == bulletRight) {
			if (World.isFreeDynamic((int) (x + 8 + (dx * spd)), (int) (y - 8 + (dy * spd)), 6, 4)) {
				x -= dx * spd;
				y += dy * spd;
				if (x <= 0 || x > Game.player.getX() + 320) {
					Game.bullets.remove(this);
				}
			} else {
				Game.bullets.remove(this);
				// World.generateParticles(5,(int) x,(int) y);
			}
		} else if (bulletSprite == bulletLeft) {
			if (World.isFreeDynamic((int) (x - 6 + (dx * spd)), (int) (y - 8 + (dy * spd)), 6, 4)) {
				x -= dx * spd;
				y += dy * spd;
				if (x <= 0 || x > Game.player.getX() + 320) {
					Game.bullets.remove(this);
				}
			} else {
				Game.bullets.remove(this);
				// World.generateParticles(5,(int) x,(int) y);
			}
		
		}
		
		

		}

	public void render(Graphics g) {

		
		if (right) {
			bulletSprite = bulletRight;
		} else if (left) {
			bulletSprite = bulletLeft;
		}
		
		g.drawImage(bulletSprite, this.getX() - Camera.x, this.getY() - 7 - Camera.y, 6, 4, null);
		
	}

}
