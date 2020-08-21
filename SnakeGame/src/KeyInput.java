/*
 * This class checks for input from the keyboard.
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	Handler handler;
	
	public KeyInput (Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
			handler.setUp(true);
		else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
			handler.setDown(true);
		else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			handler.setLeft(true);
		else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			handler.setRight(true);
		else if (key == KeyEvent.VK_P || key == KeyEvent.VK_SPACE)
			handler.setPause();
		else if (key == KeyEvent.VK_E)
			handler.setCannotSelfKill();
		else if (key == KeyEvent.VK_R)
			handler.setThroughWalls();
		else if (key == KeyEvent.VK_ENTER)
			handler.setRestart();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
			handler.setUp(false);
		else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
			handler.setDown(false);
		else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			handler.setLeft(false);
		else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			handler.setRight(false);
	}

}
