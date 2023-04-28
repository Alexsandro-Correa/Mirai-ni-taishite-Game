package com.alex.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.alex.entities.Bullets;
import com.alex.entities.Enemy;
import com.alex.entities.Entity;

import com.alex.entities.Player;
import com.alex.graficos.Spritesheet;
import com.alex.graficos.UI;
import com.alex.world.Climate;
import com.alex.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 352;
	public static final int HEIGHT = 208;
	public static final int SCALE = 4;

	private BufferedImage image;

	public static World world;
	public static List<Bullets> bullets;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static Spritesheet spritesheet;
	public static Spritesheet spritePlayer;
	public static Spritesheet background;
	public static Spritesheet guns;
	public static Player player;
	public static Menu menu;
	public static Pause pause;
	public static MarketMenu market;
	public static Climate climate;

	public UI ui;

	public static String gameState = "MENU";

	public Game() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		// Tela Cheia setPreferredSize(new
		// Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		spritePlayer = new Spritesheet("/skins.png");
		background = new Spritesheet("/cidade.jpg");
		guns = new Spritesheet("/guns.png");
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		player = new Player(WIDTH / 2 - 30, HEIGHT / 2, 36, 32, 1, Player.playerRight);
		world = new World("/level1.png");
		ui = new UI();
		climate = new Climate(HEIGHT, SCALE, 0, 0, HEIGHT, Entity.SNOW);

		bullets = new ArrayList<Bullets>();

		entities.add(player);

		menu = new Menu();
		pause = new Pause();
		market = new MarketMenu();
	}

	public void initFrame() {
		frame = new JFrame("Mirai ni Taishite");
		frame.add(this);
		// frame.setUndecorated(true); // Esconde Icone de maximizar e fechar
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		if (gameState == "INICIO") {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				enemy.tick();
			}
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
		} else if (gameState == "MENU") {
			menu.tick();
		} else if (gameState == "PAUSE") {
			pause.tick();
		} else if (gameState == "MARKET") {
			market.tick();
		} else if (gameState == "SAIR") {
			System.exit(0);
		}

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();
		g.setColor(new Color(122, 102, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Renderização do Jogo
		if (gameState == "INICIO") {
			world.render(g);
			Collections.sort(entities, Entity.nodeSorter);
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
			for (int i = 0; i < enemies.size(); i++) {
				Entity e = enemies.get(i);
				e.render(g);
			}
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).render(g);
			}
			ui.render(g);
		}

		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		if (gameState == "MENU") {
			menu.render(g);
		}

		if (gameState == "PAUSE") {
			pause.render(g);
		}

		if (gameState == "MARKET") {
			market.render(g);
		}

		g.dispose();
		bs.show();
	}

	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (gameState == "MENU") {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				menu.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				menu.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = true;
			}
		}

		if (gameState == "PAUSE") {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				pause.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				pause.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				pause.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				pause.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				pause.enter = true;
			}
		}
		
		if(gameState == "MARKET") {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				market.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				market.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				market.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				market.down = true;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				market.enter = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_V) {
				gameState = "INICIO";
			}
		}
		
		if(gameState == "INICIO") {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameState = "PAUSE";
			}
		}


		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			// Abrir Menu de Compras
			gameState = "MARKET";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (gameState == "MENU") {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				menu.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				menu.down = false;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = false;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = false;
			}
		}

		if (gameState == "PAUSE") {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				pause.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				pause.down = false;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				pause.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				pause.down = false;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				pause.enter = false;
			}
		}
		
		if(gameState == "MARKET") {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				market.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				market.down = false;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				market.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				market.down = false;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				market.enter = false;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
