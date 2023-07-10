package com.alex.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.graficos.Explosion;
import com.alex.main.Game;
import com.alex.world.Camera;
import com.alex.world.Tile;
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
	public static BufferedImage enemyScrab;
	
	public int width = 32,height = 32;
	public int positionX = 2,positionY = 16;

	public int curFrames = 0, maxFrames = 16, maxAnim = 4, curAnim = 0;

	public int explosionX, explosionY;

	public boolean enemy1;
	public boolean enemy2;
	public boolean enemy3;
	public static boolean spawn = false;

	public Enemy(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		enemyRight = Game.spriteEnemies.getSprite(16, 16, 16, 16);
		enemyLeft = Game.spriteEnemies.getSprite(0, 16, 16, 16);
		enemyRight2 = Game.spriteEnemies.getSprite(16, 32, 16, 16);
		enemyLeft2 = Game.spriteEnemies.getSprite(0, 32, 16, 16);
		enemyScrab = Game.spriteEnemies.getSprite(0, 48, 16, 16);

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
				// Game.bullets.get(0).setX(253);
				// Game.bullets.get(0).setY(83);

				if (World.checkCollision(Enemy.enemySprite, Game.enemies.get(i).getX(), Game.enemies.get(i).getY() + 1,
						Bullets.bulletRight, Game.bullets.get(b).getX(), Game.bullets.get(b).getY())
						|| World.checkCollision(Enemy.enemySprite, Game.enemies.get(i).getX(),
								Game.enemies.get(i).getY() + 1, Bullets.bulletRight, Game.bullets.get(b).getX(),
								Game.bullets.get(b).getY() - 4)) {
					System.out.println("Pei");

					Game.enemies.get(i).life--;
					System.out.println("Dano");
					Game.bullets.remove(b);
					// World.generateParticles(5,(int)
					// Game.enemies.get(i).getX(),(int)Game.enemies.get(i).getY());

					if (Game.enemies.get(i).life == 0) {
						explosionX = Game.enemies.get(i).getX()- 6 - Camera.x;
						explosionY = Game.enemies.get(i).getY() - 12 - Camera.y;
						Player.currentCoins += 5;
						Game.explosion.add(new Explosion(explosionX,explosionY));
						Game.enemies.remove(i);
						break;
					}
				}

			}

		}

		// Substituir o restart por nova fase

		if (Game.enemies.size() == 0) {
			Game.phase = "LEVEL2";
			Tile.TILE_WALL = Tile.TILE_WALL2;
			Tile.TILE_WALL_DOWN = Tile.TILE_WALL2_DOWN;
			Tile.TILE_FLOOR = Tile.TILE_FLOOR2;
			Game.world = new World("/level2.png");
			Game.player.x = 16;
			Game.player.y = 32;
			
		}
		

		if(spawn) {
			spawn = false;
			Enemy enemy = new Enemy(16,16,2,2,1.1,Enemy.enemySprite);
			enemy.enemy1 = false;
			enemy.enemy2 = true;
			enemy.enemy3 = false;
			Game.enemies.add(enemy);
		}

	}

	public void render(Graphics g) {

		if (enemy1) {
			if (dir == 1) {
				enemySprite = enemyRight;
			} else if (dir == -1) {
				enemySprite = enemyLeft;
			}
		} else if (enemy2) {
			if (dir == 1) {
				enemySprite = enemyRight2;
			} else if (dir == -1) {
				enemySprite = enemyLeft2;
			}
		}else if(enemy3) {
			enemySprite = enemyScrab;
			width = 16;
			height = 16;
			positionY = 0; 
		}

		g.drawImage(enemySprite, this.getX() - positionX - Camera.x, this.getY() - positionY - Camera.y, width, height, null);

		// super.render(g);
	}

}
