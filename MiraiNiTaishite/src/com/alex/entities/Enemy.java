package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.main.Game;
import com.alex.world.Camera;
import com.alex.world.World;

public class Enemy extends Entity {

	private int gravity = 2;
	public boolean right = true, left = false;

	public int life = 3;
	
	public int dir = 1;
	
	public static BufferedImage enemyRight;
	public static BufferedImage enemyLeft;
	
	public static int[] pixelsEnemy; 

	public Enemy(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		enemyRight = Game.spritesheet.getSprite(96, 0, 16, 16);
		enemyLeft = Game.spritesheet.getSprite(80, 0, 16, 16);
		pixelsEnemy = new int[enemyRight.getWidth() * enemyRight.getHeight()];
		enemyRight.getRGB(0, 0, enemyRight.getWidth(), enemyRight.getHeight(), pixelsEnemy, 0, enemyRight.getWidth());
		if(pixelsEnemy[0] == 0x00000000) {
			System.out.println("Transparente Inimigo");
		}
	}

	public void tick() {
		
		depth = 2;
	
		if (World.isFree((int) x, (int) (y + gravity))) {
			y += gravity;
		}

		if (right) {
			if (World.isFree((int) (x + speed), (int) y)) {
				x += speed;
				if (World.isFree((int) (x + 16), (int) y + 1)) {
					right = false;
					left = true;
				}
			} else {
				right = false;
				left = true;
			}
		}
		if (left) {
			if (World.isFree((int) (x - speed), (int) y)) {
				x -= speed;
				if (World.isFree((int) (x - 16), (int) y + 1)) {
					right = true;
					left = false;
				}
			}else {
			right = true;
			left = false;
		}
		} 
		
		if (right && World.isFree((int) (x + speed), (int) y)) {
			x += speed;
			dir = 1;
		} else if (left && World.isFree((int) (x - speed), (int) y)) {
			x -= speed;
			dir = -1;
		}
		
	}

	public void render(Graphics g) {
		if (dir == 1) {
			g.drawImage(enemyRight,this.getX() - 16 - Camera.x, this.getY() - 16 - Camera.y,32,32, null);
		} else if (dir == -1) {
			g.drawImage(enemyLeft,this.getX() - 16 - Camera.x, this.getY() - 16 - Camera.y,32,32, null);
		}
		super.render(g);
	}

}
