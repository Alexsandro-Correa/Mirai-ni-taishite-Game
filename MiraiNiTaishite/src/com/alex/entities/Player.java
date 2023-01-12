package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.main.Game;
import com.alex.world.Camera;
import com.alex.world.World;

public class Player extends Entity {

	public boolean right, left;

	public static double life = 3;

	public static int currentCoins = 0;
	public static int maxCoins = 0;

	public int dir = 1;
	private int gravity = 2;
	// private double vspd = 0;

	public boolean jump = false;

	public boolean isJumping = false;
	public int jumpHeight = 48;
	public int jumpFrames = 0;

	private int framesAnimation = 0;
	private int maxFrames = 15;

	private int maxSprite = 2;
	private int curSprite = 0;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

	}

	public void tick() {
		depth = 2;

		if (World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
			y += gravity;
			for (int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if (e instanceof Enemy) {
					if (Entity.isColliding(this, e)) {
						// Aplicar o pulo
						isJumping = true;
						jumpHeight = 24;
						// Remover vida do inimigo
						((Enemy) e).life--;
						if (((Enemy) e).life == 0) {
							Game.entities.remove(i);
							break;
						}
					}
				}
			}
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
			if (World.isFree(getX(), getY() - 2)) {
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

		// Detectar Dano
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Enemy) {
				if (Entity.isColliding(this, e)) {
					life--;
				}
			}
		}

		// Detectar Colisão com a moeda e aumentar contagem
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Coin) {
				if (Entity.isColliding(this, e)) {
					if (Entity.rand.nextInt(100) > 5) {
						Game.entities.remove(i);
						currentCoins++;
						break;
					}
				}
			}
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
			sprite = Entity.PLAYER_RIGHT[curSprite];
		} else if (dir == -1) {
			sprite = Entity.PLAYER_LEFT[curSprite];
		}
		super.render(g);
	}

}
