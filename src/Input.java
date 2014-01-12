import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Input extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean[] keyCode = new boolean[128];
	public boolean keyPressed(int c) { return keyCode[c]; }
	
	public Input() {
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				}
			public void keyReleased(KeyEvent e) {
				int released = e.getKeyCode();
				if (released < 256) { keyCode[released] = false; }
			}
			public void keyTyped(KeyEvent e) {
				int pressed = e.getKeyCode();
				if (pressed < 256) { keyCode[pressed] = true; }
			}
		});
	}
}
