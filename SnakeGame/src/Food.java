/*
 * This class creates a Food object.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Food extends GameObject{

	public Food(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle (x+1, y+1, width-2, height-2);
	}
}
