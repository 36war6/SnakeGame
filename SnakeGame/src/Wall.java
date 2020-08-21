/*
 * This class creates a wall in the game for the border of the grid.
 */
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameObject{

	public Wall(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void render(Graphics g) {
	}

	public Rectangle getBounds() {
		return new Rectangle (x, y, width, height);
	}
}
