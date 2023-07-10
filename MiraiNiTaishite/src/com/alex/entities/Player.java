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

	public static int dir = 1;
	private int gravity = 2;
	// private double vspd = 0;

	public boolean jump = false;

	public boolean isJumping = false;
	public int jumpHeight = 64;
	public int jumpFrames = 0;

	public boolean isMoved = false;

	private int framesAnimation = 0;
	private int maxFrames = 10;

	private int maxSprite = 3;
	private int curSprite = 0;
	private int delay = 0;

	public static BufferedImage playerSprite;
	public static BufferedImage[] playerRight = { Game.spritePlayer.getSprite(0, 66, 36, 32),
			Game.spritePlayer.getSprite(0, 99, 36, 32), Game.spritePlayer.getSprite(0, 132, 36, 32) };
	public static BufferedImage[] playerLeft = { Game.spritePlayer.getSprite(0, 166, 36, 32),
			Game.spritePlayer.getSprite(0, 199, 36, 32), Game.spritePlayer.getSprite(0, 232, 36, 32)};

	public boolean shoot = false;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

	}

	public void tick() {

		depth = 2;

		// Sistema de Dano
		
		for (int i = 0; i < Game.enemies.size(); i++) {
			if (World.checkCollision(playerSprite, getX()-18, getY(), Enemy.enemySprite, Game.enemies.get(i).getX(),
					Game.enemies.get(i).getY())) {
				life--;
				x = x - 30;
				y = Game.HEIGHT / 4;
				if (x < Game.WIDTH) {
					x = Game.WIDTH / 2 - 70;
					y = Game.HEIGHT / 4;
				}

			}
		}
		
		if(y+20 >= Game.HEIGHT) {
			life--;
			x = x - 30;
			y = Game.HEIGHT / 4;
			if (x < Game.WIDTH) {
				x = Game.WIDTH / 2 - 70;
				y = Game.HEIGHT / 4;
			}
		}

		if (World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
			y += gravity;
		}
		if (right && World.isFree((int) (x + speed), (int) y)) {
			x += speed;
			dir = 1;
		} else if (left && World.isFree((int) (x - speed)-8, (int) y) && Game.player.getX() > 8) {
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
			if (World.isFree(getX(), getY() - 10)) {
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

		if (shoot && delay == 0) {
			shoot = false;

			int dx = -1;
			int px = 0;
			int py = 8;

			if (dir == 1) {
				px = 18;
				dx = -1;
				Bullets.right = true;
				Bullets.left = false;
			} else {
				px = -8;
				dx = 1;
				Bullets.right = false;
				Bullets.left = true;
			}

			Bullets bullet = new Bullets(this.getX() + px, this.getY() + py, 8, 8, 1, null, dx, 0);
			Game.bullets.add(bullet);
		}

		delay++;
		if (delay >= 10) {
			delay = 0;
		}

		if (life <= 0) {
			Game.gameState = "MENU";
			life = 3;
			shoot = false;
			World.restartGame();
		}

		if (left || right) {
			isMoved = true;
		} else {
			isMoved = false;
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
			if (!World.isFree(getX(), getY() + 1) && isMoved && isJumping == false && right) {
				playerSprite = playerRight[curSprite];
				g.drawImage(playerSprite, this.getX() - 8 - Camera.x, this.getY() - 8 - Camera.y, 24, 24, null);
			} else {
				playerSprite = Game.spritePlayer.getSprite(0, 0, 36, 32);
				g.drawImage(playerSprite, this.getX() - 8 - Camera.x, this.getY() - 8 - Camera.y, 24, 24, null);
			}
		} else if (dir == -1) {
			if (!World.isFree(getX(), getY() + 1) && isMoved && isJumping == false && left) {
				playerSprite = playerLeft[curSprite];
				g.drawImage(playerSprite, this.getX() - 8 - Camera.x, this.getY() - 9 - Camera.y, 24, 24, null);
			} else {
				playerSprite = Game.spritePlayer.getSprite(0, 33, 36, 32);
				g.drawImage(playerSprite, this.getX() - 8 - Camera.x, this.getY() - 8 - Camera.y, 24, 24, null);
			}
		}
		//super.render(g);
	}

}
