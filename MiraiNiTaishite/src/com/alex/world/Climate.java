package com.alex.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.entities.Entity;
import com.alex.main.Game;

public class Climate extends Entity {

	public int timer = 0;
	public int maxTime = 60 * 1;
	public boolean raining = false;

	public static int begin = 15,end;
	
	//Criar um lista de y para diminuir o efeito visual da chuva e recomeçar cada altura de gota individualmente
	//Segue o exemplo
	//public static int[] ys = {20,20,20,20,20};
	
	public Climate(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		depth = 2;
		
		end = begin + 405;
		if (raining) {
			y+=1.1;
			x-=1.5;
		}
		

	}

	public void render(Graphics g) {
		
		if(raining) {
			for (int i = 0; i < Game.WIDTH*Game.SCALE; i++) {
				
				if (i % 90 == 0 && i < end) {
					
					
						g.fillOval((i)+(int)(x), (int) y -80, 3, 3);
						g.fillOval((i)+(int)(x), (int) y -40, 3, 3);
						g.fillOval((i)+(int)(x), (int) y , 3, 3);
						g.fillOval((i)+(int)(x), (int) y +40, 3, 3);
						//g.fillOval((i)+(int)(x), (int) y +80, 3, 3);
						g.fillOval((i)+(int)(x), (int) y +120, 3, 3);
					

					g.setColor(Color.cyan);
					
				
					
					if(!World.isFree((int)x, (int)y)) {
						y = 20;
						x = 2;
					}

				}
				
				if(i % 43 == 0 && i < end) {
					g.setColor(Color.cyan);
					
					g.fillOval((i)+(int)(x), (int) y -50, 3, 3);
					g.fillOval((i)+(int)(x), (int) y -10, 3, 3);
					//g.fillOval((i)+(int)(x), (int) y +30, 3, 3);
					g.fillOval((i)+(int)(x), (int) y +70, 3, 3);
					g.fillOval((i)+(int)(x), (int) y +110, 3, 3);
					g.fillOval((i)+(int)(x), (int) y +150, 3, 3);
					
					if(!World.isFree((int)x, (int)y+130)) {
						y = 20;
						x = 1;
					}
				}
			}
		}
	}
		
		

}
