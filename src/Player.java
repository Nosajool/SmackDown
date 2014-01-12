public class Player extends GameObject {
	private static final long serialVersionUID = 1L;
	private double dx, dy, r;
	private boolean leftKey, rightKey, upKey, downKey, shootKey,cheapKey;
	private String baseSprite;
	private boolean dead;
	private int grounded;
	private boolean helpless;
	private boolean gotSpiked;
	private int xcol;
	private int bumpBuffer;
	private int knockBuffer;
	private byte moveSpeed = 6;
	private double spriteFrame = 0;
	private byte direction = 1;
	private int shootCount;
	private int dodgeCount;
	
	public Player(String s) {
		baseSprite = s;
		setSprite(baseSprite + ".png");
		setSize(192,128);
		setX(Math.random()*1400);
		setY(100);
	}
	
	public void act() {
		setX(x() + dx);
		setY(y() + dy);
		
		r = r % 360;
		spriteFrame += 0.1;
		spriteFrame %= 4;
		
		if (shootCount > 0) { shootCount--; }
		else { shootCount = 0; }

		if (bumpBuffer > 0) { bumpBuffer--; }
		else { bumpBuffer = 0; }
		
		if (dodgeCount > 0) { dodgeCount--; }
		else { dodgeCount = 0; }
		
		if(knockBuffer > 0){ knockBuffer--;	}
		else { knockBuffer = 0; }
		
		if (dx != 0) { dx /= 1.1; }
		
		if (grounded == 2) { //on the ground
			if (shootKey && shootCount == 0) { //can dash on the ground
				dx = direction * 48;
				shootCount = 60;
			}
			else {
				if (dx == 0) { setSprite(baseSprite + ".png"); }
				else { setSprite(baseSprite + (int)spriteFrame + ".png"); }
			}
			if (Math.abs(dx) > moveSpeed&&grounded==2) { setSprite(baseSprite + "_shoot.png"); }
			//else { setSprite("guy" + (int)spriteFrame + "_shoot.png"); }
			dy = 0;
			if (upKey) {
				dy = -14;
				grounded = 1;
			}
		}
		else {//in the air
			if(shootKey && knockBuffer == 0 && !helpless){ knockBuffer=30; }
			if (knockBuffer > 0) { setSprite(baseSprite + "_jump_shoot.png"); }
			else { setSprite(baseSprite + "_jump.png"); }
			if (downKey && dy > 0) { dy += 3; }
			dy += 0.5;
			if (upKey && grounded == 1 && dy > 0) {
				dy = -14;
				grounded = 0;
			}
			if (y() + getHeight() > 800) {
				grounded = 2;
				dy = 0;
				setY(800 - getHeight());
				if (helpless) {
					helpless = false;
				}
				if(gotSpiked){//GG GAME OVER explosion
					dead = true;
				}
			}
		}
		if (leftKey ^ rightKey && Math.abs(dx) <= moveSpeed) {
			if (leftKey && (xcol < -80 || xcol > 0)) {
				if (dx > -moveSpeed) {
					dx = -moveSpeed; 
				}
				if (grounded == 2) { direction = -1; }
				}
			if (rightKey && (xcol > 80 || xcol < 0)) {
				if (dx < moveSpeed) {
					dx = moveSpeed;
				}
				if (grounded == 2) { direction = 1; }
				}
		}
		else if (grounded == 2 && Math.abs(dx) <= moveSpeed) {
			dx = 0;
		}
		
		
		//troloolool
		if(cheapKey){
			dead=true;
		}
		//

		if (downKey && dodgeCount == 0) { //dodge
			dx = -direction * 32;
			dy = -8;
			grounded = 1;
			dodgeCount = 60;
		}
		
		if (dodgeCount > 30) { setSprite(baseSprite + "_dodge.png"); }
		
		if (helpless) {
			setSprite(baseSprite + "_hurt.png");
			grounded = 0;
		}
		
		if (getX() < -80) {
			setX(-80);
			if (Math.abs(dx) > moveSpeed) { dx *= -1; }
			}
		if (getX() > 1480) {
			setX(1480);
			if (Math.abs(dx) > moveSpeed) { dx *= -1; }
			}
		
		if ((!(xcol < -80 || xcol > 0) && dx < 0) || (!(xcol > 80 || xcol < 0) && dx > 0)) {
			//dx = 0;
		}
		flipSprite(direction);
	}
	
	public void collision(int d, int y) {
		if (Math.abs(y) < 80) {
			xcol = d;
		}
		else {
			xcol = 200;
		}
	}
	public double returnSpeed() {
		return dx;
	}
	public void bump(double diffx) {
		if (bumpBuffer == 0) {
			dx = diffx / 2;
			dy = -18;
			helpless = true;
		}
	}
	public void spike(double diffy) {
		if (bumpBuffer == 0) { 
			dy = diffy;
			gotSpiked = true;
			helpless = true;
		}
	}
	public void setBumpBuffer(int b) {
		bumpBuffer = 30;
	}
	
	public boolean isDodging() { return dodgeCount > 0; }
	public boolean isDead() { return dead; }
	public boolean canShoot() { return shootCount == 0; }
	public boolean knocking() { return knockBuffer > 26; }
	public boolean inAir() { return grounded != 2; }
	public int direction() { return direction; }
	public void cheapKeyPressed(boolean b){ cheapKey = b;}
	public void leftKeyPressed(boolean b) { leftKey = b; }
	public void rightKeyPressed(boolean b) { rightKey = b; }
	public void upKeyPressed(boolean b) { upKey = b; }
	public void downKeyPressed(boolean b) { downKey = b; }
	public void shootKeyPressed(boolean b) { shootKey = b; }
}