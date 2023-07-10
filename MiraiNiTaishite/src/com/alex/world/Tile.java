package com.alex.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alex.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.tiles.getSprite(0, 32, 16, 16);
	public static BufferedImage TILE_FLOOR2 = Game.tiles.getSprite(16, 32, 16, 16);
	public static BufferedImage TILE_WALL = Game.tiles.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL_DOWN = Game.tiles.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_WALL2 = Game.tiles.getSprite(16, 0, 16, 16);
	public static BufferedImage TILE_WALL2_DOWN = Game.tiles.getSprite(16, 16, 16, 16);
	public static BufferedImage TILE_EDIFICE = Game.spritesheet.getSprite(64, 128, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
