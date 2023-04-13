package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.main.Game;
import com.alex.world.Camera;
import com.alex.world.World;

public class Player extends Entity {

	public boolean right, left;
	public int right_dir = 0, left_dir = 1;
	public int mx, my;

	public static double life = 3;

	public static int currentCoins = 0;

	public int dir = 1;
	private int gravity = 2;
	// private double vspd = 0;

	public boolean jump = false;

	public boolean isJumping = false;
	public int jumpHeight = 64;
	public int jumpFrames = 0;

	private int framesAnimation = 0;
	private int maxFrames = 15;

	private int maxSprite = 2;
	private int curSprite = 0;

	public static BufferedImage playerSprite;
	public static BufferedImage playerRight;
	public static BufferedImage playerLeft;

	public boolean shoot = false;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		playerRight = Game.spritePlayer.getSprite(0, 0, 36, 32);
		playerLeft = Game.spritePlayer.getSprite(37, 0, 36, 32);

	}

	public void tick() {

		depth = 2;

		// Sistema de Dano

		for (int i = 0; i < Game.enemies.size(); i++) {

			if (Enemy.enemySprite == Enemy.enemyRight && playerSprite == playerRight) {
				if (collision(playerSprite, getX(), getY(), Enemy.enemySprite, Game.enemies.get(i).getX(),
						Game.enemies.get(i).getY(), 10, 29, 0, 0)) {
					System.out.println("Colidiu R");
					life--;
					x = x - 30;
					y = Game.HEIGHT / 4;
					if (x < Game.WIDTH) {
						x = Game.WIDTH / 2 - 70;
						y = Game.HEIGHT / 4;
					}

				}
			}
			if (Enemy.enemySprite == Enemy.enemyLeft && playerSprite == playerRight) {
				if (collision(playerSprite, getX(), getY(), Enemy.enemySprite, Game.enemies.get(i).getX(),
						Game.enemies.get(i).getY(), 15, 28, 0, 0)) {
					System.out.println("Colidiu L");
					life--;
					x = x - 30;
					y = Game.HEIGHT / 4;
					if (x < Game.WIDTH) {
						x = Game.WIDTH / 2 - 70;
						y = Game.HEIGHT / 4;
					}
				}
			}

			if (Enemy.enemySprite == Enemy.enemyRight && playerSprite == playerLeft) {
				if (collision(playerSprite, getX(), getY(), Enemy.enemySprite, Game.enemies.get(i).getX(),
						Game.enemies.get(i).getY(), 18, 22, 0, 0)) {
					System.out.println("Colidiu R");
					life--;
					x = x - 30;
					y = Game.HEIGHT / 4;
					if (x < Game.WIDTH) {
						x = Game.WIDTH / 2 - 70;
						y = Game.HEIGHT / 4;
					}

				}
			}
			if (Enemy.enemySprite == Enemy.enemyLeft && playerSprite == playerLeft) {
				if (collision(playerSprite, getX(), getY(), Enemy.enemySprite, Game.enemies.get(i).getX(),
						Game.enemies.get(i).getY(), 21, 19, 0, 0)) {
					System.out.println("Colidiu L");
					life--;
					x = x - 30;
					y = Game.HEIGHT / 4;
					if (x < Game.WIDTH) {
						x = Game.WIDTH / 2 - 70;
						y = Game.HEIGHT / 4;
					}
				}
			}
		}

		if (World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
			y += gravity;
		}
		if (right && World.isFree((int) (x + speed), (int) y)) {
			x += speed;
			dir = 1;
		} else if (left && World.isFree((int) (x - speed), (int) y)) {
			x -= speed;
			dir = -1;
		}

		if (jump) {
			if (!World.isFree(getX(), getY() + 1)) {
				isJumping = true;
			} else {
				jump = false;
			}
		}

		if (isJumping) {
			if (World.isFree(getX(), getY() - 6)) {
				y -= 2;
				jumpFrames += 2;
				if (jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			} else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}

		if (shoot) {
			shoot = false;
			int dx = -1;
			int px = 0;
			int py = 8;

			if (dir == 1) {
				px = 18;
				dx = -1;
			} else {
				px = -8;
				dx = 1;

			}

			Bullets bullet = new Bullets(this.getX() + px, this.getY() + py, 8, 8, 1, null, dx, 0);
			Game.bullets.add(bullet);
		}
		
		if(life <= 0) {
			Game.gameState = "MENU";
			life = 3;
		}

		Camera.x = Camera.clamp((int) x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int) y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);

	}

	public void render(Graphics g) {
		framesAnimation++;
		if (framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if (curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		if (dir == 1) {
			playerSprite = playerRight;
			g.drawImage(playerSprite, this.getX() - 8 - Camera.x, this.getY() - 8 - Camera.y, 24, 24, null);
		} else if (dir == -1) {
			playerSprite = playerLeft;
			g.drawImage(playerSprite, this.getX() - Camera.x, this.getY() - 8 - Camera.y, 24, 24, null);
		}
		super.render(g);
	}

}
