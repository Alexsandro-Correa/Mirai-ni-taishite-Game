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

	public static int[] pixelsBullet;

	public Bullets(int x, int y, int width, int height, double speed, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, speed, sprite);
		this.dx = dx;
		this.dy = dy;

		bulletRight = Game.spritesheet.getSprite(96, 0, 16, 16);
		bulletLeft = Game.spritesheet.getSprite(80, 0, 16, 16);
		pixelsBullet = new int[bulletRight.getWidth() * bulletRight.getHeight()];
		bulletRight.getRGB(0, 0, bulletRight.getWidth(), bulletRight.getHeight(), pixelsBullet, 0,
				bulletRight.getWidth());

	}

	public void tick() {
		if (World.isFreeDynamic((int) (x + (dx * spd)), (int) (y + (dy * spd)), 3, 3)) {
			x -= dx * spd;
			y += dy * spd;
		} else {
			Game.bullets.remove(this);
			// World.generateParticles(5,(int) x,(int) y);
		}

	}

	public void render(Graphics g) {

		g.drawImage(bulletRight, this.getX() - Camera.x, this.getY() - 18 - Camera.y, 16, 16, null);

		// g.setColor(Color.yellow);
		// g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y -10, width,
		// height);
	}

}
