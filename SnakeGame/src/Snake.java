/*
 * This class creates a Snake tile
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Snake extends GameObject {

	public Snake(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle (x, y, width, height);
	}
}
