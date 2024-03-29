package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.alex.main.Game;
import com.alex.world.Camera;
import com.alex.world.Node;
import com.alex.world.Vector2i;
import com.alex.world.World;

public class Entity {

	public static BufferedImage HEART = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage SNOW = Game.spritesheet.getSprite(0, 48, 16, 16);

	public double x;
	public double y;
	protected double speed;
	protected int width;
	protected int height;
	public int life;

	public int depth;

	protected List<Node> path;

	protected BufferedImage sprite;

	public static Random rand = new Random();

	public Entity(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

	}

	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {

		@Override

		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth) {
				return +1;
			}
			if (n1.depth > n0.depth) {
				return -1;
			}
			return 0;

		}

	};

	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {

		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void tick() {

	}

	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public void followPath(List<Node> path) {

		if (path != null) {
			if (path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				// xprev = x;
				// yprev = y;
				if (x < target.x * 16) {
					x++;
				} else if (x > target.x * 16) {
					x--;
				}

				if (y < target.y * 16) {
					y++;
				} else if (y > target.y * 16) {
					y--;
				}

				if (x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}

			}
		}

	}

	public void render(Graphics g) {
		// g.drawImage(sprite, this.getX() -8- Camera.x, this.getY() -8- Camera.y, 24,
		// 24, null);
		// g.setColor(Color.red);
		// g.fillRect(this.getX() +maskx - Camera.x, this.getY() + masky -
		// Camera.y,mwidth,mheight);
	}

}
