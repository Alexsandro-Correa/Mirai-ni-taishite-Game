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

	public static BufferedImage enemySprite;
	public static BufferedImage enemyRight;
	public static BufferedImage enemyLeft;
	public static BufferedImage enemyRight2;
	public static BufferedImage enemyLeft2;

	public boolean enemy1, enemy2;

	public static int[] pixelsEnemy;
	public static int[] pixelsEnemy2;

	public Enemy(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		enemyRight = Game.spritesheet.getSprite(96, 0, 16, 16);
		enemyLeft = Game.spritesheet.getSprite(80, 0, 16, 16);
		enemyRight2 = Game.spritesheet.getSprite(96, 17, 16, 16);
		enemyLeft2 = Game.spritesheet.getSprite(80, 17, 16, 16);
		pixelsEnemy = new int[enemyRight.getWidth() * enemyRight.getHeight()];
		pixelsEnemy2 = new int[enemyRight2.getWidth() * enemyRight2.getHeight()];
		enemyRight.getRGB(0, 0, enemyRight.getWidth(), enemyRight.getHeight(), pixelsEnemy, 0, enemyRight.getWidth());
		enemyRight2.getRGB(0, 0, enemyRight2.getWidth(), enemyRight2.getHeight(), pixelsEnemy, 0,
				enemyRight2.getWidth());
	}

	public void tick() {

		depth = 2;

		if (World.isFree((int) x, (int) (y + gravity))) {
			y += gravity;
		}

		if (right) {
			dir = 1;
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
			dir = -1;
			if (World.isFree((int) (x - speed), (int) y)) {
				x -= speed;
				if (World.isFree((int) (x - 16), (int) y + 1)) {
					right = true;
					left = false;
				}
			} else {
				right = true;
				left = false;
			}
		}

		// Sistema de Dano e Destruição de Inimigos

		for (int i = 0; i < Game.enemies.size(); i++) {
			for (int b = 0; b < Game.bullets.size(); b++) {
				// Game.bullets.get(0).setX(248);
				// Game.bullets.get(0).setY(76);
				if (collision(Enemy.enemySprite, Game.enemies.get(i).getX(), Game.enemies.get(i).getY(),
						Bullets.bulletRight, Game.bullets.get(b).getX(), Game.bullets.get(b).getY(), 21, 8, 18, -7)) {
					System.out.println("Pei");

					Game.enemies.get(i).life--;
					System.out.println("Dano");
					Game.bullets.remove(b);

					if (Game.enemies.get(i).life == 0) {
						Game.enemies.remove(i);
						Player.currentCoins += 5;
						break;
					}

				}
			}

		}

	}

	public void render(Graphics g) {

		if (dir == 1) {
			enemySprite = enemyRight;
		} else if (dir == -1) {
			enemySprite = enemyLeft;
		}

		g.drawImage(enemySprite, this.getX() - 2 - Camera.x, this.getY() - 16 - Camera.y, 32, 32, null);

		super.render(g);
	}

}
