/*
 * This class creates eyes for the snake
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Eyes extends GameObject {
	String direction;

	public Eyes(int x, int y, int width, int height, String direction) {
		super(x, y, width, height);
		this.direction = direction;
	}
	
	public void render (Graphics g) {
		g.setColor(Color.BLACK);
		if (direction=="up") {
			g.drawLine(x+10, y, x+10, y+10);
			g.drawLine(x+20, y, x+20, y+10);
		}
		else if (direction=="down") {
			g.drawLine(x+10, y+20, x+10, y+30);
			g.drawLine(x+20, y+20, x+20, y+30);
		}
		else if (direction=="left") {
			g.drawLine(x, y+10, x+10, y+10);
			g.drawLine(x, y+20, x+10, y+20);
		}
		else if (direction=="right") {
			g.drawLine(x+20, y+10, x+30, y+10);
			g.drawLine(x+20, y+20, x+30, y+20);
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle (-3, -3, 1, 1);
	}
}