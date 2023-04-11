package com.alex.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
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
	
	public Entity(int x,int y, int width, int height,double speed,BufferedImage sprite) {
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
	
	public void  setX(int newX) {
		this.x = newX;
	}
	public void  setY(int newY) {
		this.y = newY;
	}
	
	
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
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
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1-y2) * (y1-y2));
	}
	
	public void followPath(List<Node> path) {
		
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x *16) {
					x--;
				}
				
				if(y < target.y * 16)  {
					y++;
				}else if(y > target.y *16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y *16) {
					path.remove(path.size()-1);
				}
				
			}
		}
		
	}
	
	public static boolean isColliding(Entity e1,Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() , e1.getY() ,e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX() , e2.getY() ,e1.getWidth(), e1.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	public static boolean isCollidingPerfect(int x1,int y1,int[] pixels1,BufferedImage sprite1,int x2,int y2,int[] pixels2,BufferedImage sprite2) {
		
		for(int xx1 = 0; xx1 < sprite1.getWidth(); xx1++) {
			for(int yy1 = 0; yy1 < sprite1.getHeight(); yy1++) {
				for(int xx2 = 0; xx2 < sprite2.getWidth(); xx2++) {
					for(int yy2 = 0; yy2 < sprite2.getHeight(); yy2++) {
						int pixelAtual1 = pixels1[xx1+yy1*sprite1.getWidth()];
						int pixelAtual2 = pixels2[xx2+yy2*sprite2.getWidth()];
						if(pixelAtual1 == 0X00000000 || pixelAtual2 == 0X00000000) {
							continue;
						}
						if(xx1+x1 == xx2+x2 & yy1+y1 == yy2+y2) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	
	public void render(Graphics g) {
		//g.drawImage(sprite, this.getX() -8- Camera.x, this.getY() -8- Camera.y, 24, 24, null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() +maskx - Camera.x, this.getY() + masky - Camera.y,mwidth,mheight);
	}

}
