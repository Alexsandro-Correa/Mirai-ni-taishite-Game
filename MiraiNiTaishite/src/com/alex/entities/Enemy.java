package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.world.World;

public class Enemy extends Entity {

	private int gravity = 2;
	public boolean right = true, left = false;

	public int life = 3;

	public Enemy(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
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
	}

	public void render(Graphics g) {
		if (right) {
			sprite = Entity.ENEMY1_RIGHT;
		} else if (left) {
			sprite = Entity.ENEMY1_LEFT;
		}
		super.render(g);
	}

}
