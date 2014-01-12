import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public abstract class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	private ArrayList <GameObject> ObjectList = new ArrayList <GameObject>();
	private Timer _t;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int) screenSize.getWidth();
	private int windowHeight = (int) screenSize.getHeight();
	private boolean[] charKey = new boolean[128];
	private boolean mouseLeft = false;
	public abstract void setup();
	public abstract void act();
	
	public boolean keyPressed(char c) { return this.charKey[c]; }
	public boolean mousePressed() { return this.mouseLeft; }
	public int getWindowWidth() { return windowWidth; }
	public int getWindowHeight() { return windowHeight; }
	public int getMouseX() { return (int) MouseInfo.getPointerInfo().getLocation().getX(); }
	public int getMouseY() { return (int) MouseInfo.getPointerInfo().getLocation().getY(); }

	public void initComponents() {
		getContentPane().setBackground(Color.GRAY);
		setup();
		for (int i = 0; i < this.ObjectList.size(); i++) {
			GameObject o = (GameObject) this.ObjectList.get(i);
			o.repaint();
		}
		this._t.start();
	}

	public void add(GameObject o) {
		this.ObjectList.add(o);
		getContentPane().add(o);
	}

	public void remove(GameObject o) {
		this.ObjectList.remove(o);
		getContentPane().remove(o);
	}

	public void setDelay(int delay) {
		this._t.setDelay(delay);
	}

	public Game() {
		setSize(windowWidth, windowHeight);
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		this._t = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.this.act();
				for (int i = 0; i < Game.this.ObjectList.size(); i++) {
					Game.this.ObjectList.get(i).act();
				}
			}
		});

		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {		
				char pressed = Character.toUpperCase(e.getKeyChar());
				if (pressed == 27) { System.exit(0); }
				if (pressed < 128) {charKey[pressed] = true;}
				}

			public void keyPressed(KeyEvent e) {}

			public void keyReleased(KeyEvent e) {
				char released = Character.toUpperCase(e.getKeyChar());
				if (released < 128) { charKey[released] = false; }
			}
		});

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) { Game.this.mouseLeft = true; }
			public void mouseReleased(MouseEvent e) { Game.this.mouseLeft = false; }
		});
	}

	public void startGame() {
		this._t.start();
	}

	public void stopGame() {
		this._t.stop();
	}

	public int getFieldWidth() {
		return getContentPane().getBounds().width;
	}

	public int getFieldHeight() {
		return getContentPane().getBounds().height;
	}
}