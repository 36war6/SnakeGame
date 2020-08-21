/*
 * This class creates a controls a bunch of stuff (rendering all objects and checking for key input).
 */
import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private boolean up = false, down = false, right = false, left = false, pause = false, cannotSelfKill = false, throughWalls = false, restart = false;
	
	public void render(Graphics g) {
		for (int i = 0; i<object.size(); i++) {
			object.get(i).render(g);
		}
	}

	public void addObject (GameObject tempObject) {
		if (tempObject instanceof Eyes) {
			for (int i=object.size()-1; i>=0; i--) {
				if (object.get(i) instanceof Eyes) {
					removeObject(object.get(i));
					break;
				}
			}
		}
		object.add(tempObject);
	}

	public void removeObject (GameObject tempObject) {
		object.remove(tempObject);
	}
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean isPaused() {
		return pause;
	}
	
	public void setPause() {
		pause = !pause;
	}
	
	public boolean isCannotSelfKill() {
		return cannotSelfKill;
	}
	
	public void setCannotSelfKill() {
		cannotSelfKill = !cannotSelfKill;
	}
	
	public boolean isThroughWalls() {
		return throughWalls;
	}
	
	public void setThroughWalls() {
		throughWalls = !throughWalls;
	}
	
	public void setRestart() {
		restart = !restart;
	}
	
	public boolean isRestart() {
		return restart;
	}
}
