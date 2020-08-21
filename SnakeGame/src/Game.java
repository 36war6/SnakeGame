/*
 * This class controls everything and runs the game.
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private int width = 1130, height = 780;
	private LinkedList<Snake> player = new LinkedList<Snake>();
	private GameObject[] other = new GameObject[5];
	private String direction="";
	private int score =0;
	private int eatenFruit = 0;
	private int fruitScore = 100;
	private long fruitScoreTime;
	boolean crash = false;
	
	// The constructor that is called by the main method to start everything
	public Game() {
		new Window(width, height, "Snake Game", this);
	
		handler = new Handler();
		start ();
		
		player.add(new Snake(360, 360, 30, 30));
		other[0]=new Wall (-1, 0, 1, height);
		other[1]=new Wall (0, -1, height, 1);
		other[2]=new Wall (height+1, 0, 1, height);
		other[3]=new Wall (0, height-29, height, 1);
		spawnFood();
		
		handler.addObject(player.get(0));
		for (int i = 0; i<4; i++)
			handler.addObject(other[i]);
		
		this.addKeyListener(new KeyInput (handler));

	}
	
	// Called once in the constructor to start the thread
	private void start() {
		fruitScoreTime = System.currentTimeMillis()/1000;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	// Called after the player dies to potentially play again
	private void stop() {
		handler.setRestart();
		do {
			try  {
	             Thread.sleep(100);
	        }catch(InterruptedException e){}
		} while (handler.isRestart());
		
		for (int i=0; i<player.size();) {
			handler.removeObject(player.get(0));
			player.remove(0);
		}
		player.add(new Snake(360, 360, 30, 30));
		handler.addObject(player.get(0));

		crash = false;
		
		direction = "";
		player.add(new Snake(360, 360, 30, 30));
		
		fruitScoreTime=System.currentTimeMillis()/1000;
		score = 0;
		eatenFruit = 0;
		fruitScore = 101;
		renderObjects();
	}
	
	// Continuously goes
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double na = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / na;
			lastTime = now;
			while (delta >= 1) {
				delta--;
			}
			render();
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop ();
	}
	
	// Updates a thousand times a second
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		/////////////////////////////////////////////
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		grid(g);

		directionChoose();
		if (direction!="")
			renderObjects();
		sideBar(g);
		collision();
		handler.render (g);
		/////////////////////////////////////////////
		g.dispose();
		bs.show();
		if (handler.isPaused()) {
			long startPause = System.currentTimeMillis()/1000;
			do {
				try  {
		             Thread.sleep(100);
		        }catch(InterruptedException e){}
			} while (handler.isPaused());
			fruitScoreTime += System.currentTimeMillis()/1000 - startPause;
		}
		if (crash)
			stop();
	}
	
	// Draws the grid
	public void grid(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i<=25; i++) {
			g.drawLine(30*i, 0, 30*i, height);
			g.drawLine(0, 30*i, height, 30*i);
		}
	}
	
	// Text for the side bar
	public void sideBar(Graphics g) {
		g.setColor(Color.WHITE);
		Font subTitleFont = new Font ("ComicSans", Font.PLAIN, 30);
		Font tinyFont = new Font("ComicSans", Font.PLAIN, 20);
		g.setFont(new Font("ComicSans", Font.BOLD, 40));
		g.drawString("The Snake Game", height+15, 80);
		g.setFont(subTitleFont);
		g.drawString("Statistics", height+30, 160);
		g.setFont(tinyFont);
		g.drawString("Total Score: "+score, height+40, 210);
		g.drawString("Fruit Eaten: "+eatenFruit, height+40, 240);
		fruitScore = 100+(int)(fruitScoreTime-System.currentTimeMillis()/1000);
		if (fruitScore<0)
			fruitScore = 0;
		g.drawString("Fruit Score: "+fruitScore, height+40, 270);
		
		g.setFont(subTitleFont);
		g.drawString("Controls", height+30, 350);
		g.setFont(tinyFont);
		g.drawString("Move Up: W / Up Arrowkey", height+40, 400);
		g.drawString("Move Down: S / Down Arrowkey", height+40, 430);
		g.drawString("Move Left: A / Left Arrowkey", height+40, 460);
		g.drawString("Move Right: D / Right Arrowkey", height+40, 490);
		g.drawString("Pause Game: P / Space Bar", height+40, 520);
		g.drawString("New Game (AD): Enter Bar", height+40, 550);
		
		g.setFont(subTitleFont);
		g.drawString("Cheats", height+30, 600);
		g.setFont(tinyFont);
		g.drawString("Cannot Self-Kill (E): "+handler.isCannotSelfKill(), height+40, 650);
		g.drawString("Walk through Walls (R): "+handler.isThroughWalls(), height+40, 680);
	}
	
	// Checks for collision
	public void collision() {
		Snake first = player.get(player.size()-1);
		if (!handler.isCannotSelfKill()){
			for (int i = 0; i<player.size()-1; i++) {
				if (player.get(i).getBounds().intersects(first.getBounds())) {
					crash = true;
					return;
				}
			}
		}
		if (handler.isThroughWalls())
			return;
		for (int i = 0; i<other.length-1; i++) {
			if (first.getBounds().intersects(other[i].getBounds())) {
				crash = true;
				return;
			}
		}
	}
	
	// Renders the snake
	public void renderObjects() {
		Snake previous = player.get(player.size()-1);
		if (direction=="up") {
			player.add(new Snake (previous.getX(), previous.getY()-30, 30, 30));
		}
		else if (direction=="down") {
			player.add(new Snake (previous.getX(), previous.getY()+30, 30, 30));
		}
		else if (direction=="right") {
			player.add(new Snake (previous.getX()+30, previous.getY(), 30, 30));
		}
		else if (direction=="left") {
			player.add(new Snake (previous.getX()-30, previous.getY(), 30, 30));
		}
		Eyes eyes = new Eyes (player.get(player.size()-1).getX(), player.get(player.size()-1).getY(), 30,30, direction);
		handler.addObject(player.get(player.size()-1));
		handler.addObject(eyes);
		if (player.get(player.size()-1).getBounds().intersects(other[4].getBounds())) {
			eatenFruit++;
			score += fruitScore;
			fruitScore = 100;
			handler.removeObject(other[4]);
			spawnFood();
			fruitScoreTime = System.currentTimeMillis()/1000;
		}
		else {
			handler.removeObject(player.get(0));
			player.remove(0);
		}
		try  {
             Thread.sleep(100);			// Change this to change speed (inversely proportional)
        }catch(InterruptedException e){}
	}
	
	// Decides direction from keyboard input
	public void directionChoose () {
		if (handler.isUp() && !direction.equals("down"))
			direction="up";
		else if (handler.isDown() && !direction.equals("up"))
			direction="down";
		else if (handler.isRight() && !direction.equals("left"))
			direction="right";
		else if (handler.isLeft() && !direction.equals("right"))
			direction="left";
	}
	
	// Generates food
	public void spawnFood () {
		int x = ((int)(Math.random()*25))*30+5;
		int y = ((int)(Math.random()*25))*30+5;
		other[4] = new Food(x, y, 20, 20);
		boolean spawned = false;
		while (!spawned) {
			for (int i = 0; i<player.size(); i++) {
				if (player.get(i).getBounds().intersects(other[4].getBounds())) {
					if (x>700 && y>700) {
						y=5;
						x=5;
					}
					else if (x>700) {
						x=5;
						y+=30;
					}
					else
						x+=30;
					other[4] = new Food(x, y, 20, 20);
					break;
				}
				else if (i==player.size()-1) {
					handler.addObject(other[4]);
					spawned = true;
				}
			}
		}
	}

	// Main method that starts everything
	public static void main(String[] args) {
		new Game ();
	}
}
