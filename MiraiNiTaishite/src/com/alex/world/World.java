package com.alex.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alex.entities.Enemy;
import com.alex.entities.Particle;
import com.alex.entities.Player;
import com.alex.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		// Renderização de Mapa
				try {
					BufferedImage map = ImageIO.read(getClass().getResource(path));
					int[] pixels = new int[map.getWidth() * map.getHeight()];
					WIDTH = map.getWidth();
					HEIGHT = map.getHeight();
					tiles = new Tile[map.getWidth() * map.getHeight()];
					map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
					for (int xx = 0; xx < map.getWidth(); xx++) {
						for (int yy = 0; yy < map.getHeight(); yy++) {
							int pixelAtual = pixels[xx + (yy * map.getWidth())];
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
							if(pixelAtual == 0xFF000000) {
								tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
							}else if(pixelAtual == 0xFFFFFFFF) {
								tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
								if (yy-1 >= 0 && pixels[xx+((yy-1)* map.getWidth())] == 0xFFFFFFFF) {
									tiles[xx +(yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_DOWN);
								}
							}else if(pixelAtual == 0xFF00FFFF) {
								Game.player.setX(xx*16);
								Game.player.setY(yy*16);
							}else if(pixelAtual == 0xFF4CFF00) {
								Enemy enemy = new Enemy(xx*16,yy*16,24,24,1,Enemy.enemyRight);
								enemy.enemy1 = true;
								enemy.enemy2 = false;
								enemy.enemy3 = false;
								Game.enemies.add(enemy);
							}else if(pixelAtual == 0xFFFF00DC) {
								Enemy enemy2 = new Enemy(xx*16,yy*16,24,24,1,Enemy.enemyRight2);
								enemy2.enemy1 = false;
								enemy2.enemy2 = true;
								enemy2.enemy3 = false;
								Game.enemies.add(enemy2);
							}else if(pixelAtual == 0xFFFF0000) {
								Enemy enemy3 = new Enemy(xx*16,yy*16,24,24,2,Enemy.enemyScrab);
								enemy3.enemy1 = false;
								enemy3.enemy2 = false;
								enemy3.enemy3 = true;
								Game.enemies.add(enemy3);
							}else if(pixelAtual == 0xFF404040) {
								tiles[xx +(yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Game.spritesheet.getSprite(16, 32, 16, 16));
							}else if(pixelAtual == 0XFF808080) {
								tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_EDIFICE);
							}
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

	}

	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {

		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + width - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height - 1) / TILE_SIZE;

		int x4 = (xnext + width - 1) / TILE_SIZE;
		int y4 = (ynext + height - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}

	public static boolean isFree(int xnext, int ynext) {

		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}

	public static void restartGame() {
		
		Player.currentCoins = 0;
		Tile.TILE_WALL = Game.tiles.getSprite(0, 0, 16, 16);
		Tile.TILE_WALL_DOWN = Game.tiles.getSprite(0, 16, 16, 16);
		Tile.TILE_FLOOR = Game.tiles.getSprite(0, 32, 16, 16);
		Game.enemies.clear();
		Game.entities.clear();
		Game.bullets.clear();
		Game.entities.add
		(Game.player = new Player(Game.WIDTH / 2 - 30, Game.HEIGHT / 2-16, 36, 32, 1, Player.playerRight[0]));
		Game.world = new World("/level1.png");
		
		return;
	}
	
	public static void generateParticles(int amount, int x, int y) {
		for(int i = 0 ; i < amount; i++) {
			Game.entities.add(new Particle(x,y,1,1,1,null));
		}
	}
	
	public static boolean checkCollision(BufferedImage image1, int x1, int y1, BufferedImage image2, int x2, int y2) {
        int width1 = image1.getWidth();
        int height1 = image1.getHeight();
        int width2 = image2.getWidth();
        int height2 = image2.getHeight();

        int startX = Math.max(x1, x2);
        int startY = Math.max(y1, y2);
        int endX = Math.min(x1 + width1, x2 + width2);
        int endY = Math.min(y1 + height1, y2 + height2);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int pixel1 = image1.getRGB(x - x1, y - y1);
                int pixel2 = image2.getRGB(x - x2, y - y2);

                if (((pixel1 >> 24) & 0xff) != 0 && ((pixel2 >> 24) & 0xff) != 0) {
                    return true;
                }
            }
           }
		return false;
	}

	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;

		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}

	}

}
