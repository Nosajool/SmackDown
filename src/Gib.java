public class Gib extends GameObject {
	private static final long serialVersionUID = 1L;
	private double dx, dy;
	private int jumps;
	private boolean stop;
	public Gib(int x, int y, double xspeed, double yspeed, String s) {
		setSprite(s + ".png");
		setSize(64,64);
		setX(x);
		setY(y);
		dx = xspeed;
		dy = yspeed;
		jumps = 5;
		if (Math.random() > 0.5) { flipSprite(-1); }
	}
	public void act() {
		if (!stop) {
			setX(x() + dx);
			setY(y() + dy);
			dy += 0.5;
			rotate(getRotation() + dx*3);
			if (y() + getHeight() > 800) {
				dy = -dy * 0.75;
				if (Math.abs(dy) < 1) { stop = true; }
				jumps--;
				dx *= 0.9;
			}
		}
	}
}
