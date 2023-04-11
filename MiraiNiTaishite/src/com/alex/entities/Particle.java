package com.alex.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.alex.main.Game;
import com.alex.world.Camera;

public class Particle extends Entity {

	public int lifeTime =15;
	public int curLife = 0;
	
	public int spd = 2;
	public double dx = 0;
	public double dy = 0;	
	
	public Particle(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
		
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(lifeTime == curLife) {
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 5, 5);
	}
	
}