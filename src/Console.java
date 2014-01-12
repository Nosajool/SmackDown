import java.util.ArrayList;
public class Console extends Game {
	private static final long serialVersionUID = 1L;
	private Input input = new Input();
	private Player player = new Player("guy");
	private Player player2 = new Player("bguy");
	private boolean p1dead, p2dead;
	private Gib body, arm1, arm2, leg1, leg2, head, bbody, barm1, barm2, bleg1, bleg2, bhead;
	private int gibCounter=0;
	
	public static void main(String args[]) {
		new Console();
		}
	public Console() {
		setVisible(true);
		initComponents();
	}
	public void setup() {
		setDelay(10);
		add(player);
		add(player2);
	}

	public void act() {
		repaint();
		keys();
		handlePlayers();
	}
	
	private void handlePlayers() {
		player.collision(player2.getCX() - player.getCX(), player2.getCY() - player.getCY());
		player2.collision(player.getCX() - player2.getCX(), player.getCY() - player2.getCY());
		
		if (Math.abs(player.getCX() - player2.getCX()) < 80 &&
			Math.abs(player.getCY() - player2.getCY()) < 80) { //they touch
			if (Math.abs(player.returnSpeed()) > Math.abs(player2.returnSpeed()) + 6 && !player.isDodging()) {
				player2.bump(player.returnSpeed() + player2.returnSpeed());
				player.setBumpBuffer(30);
			}
			if (Math.abs(player2.returnSpeed()) > Math.abs(player.returnSpeed()) + 6 && !player2.isDodging()) {
				player.bump(player.returnSpeed() + player2.returnSpeed());
				player2.setBumpBuffer(30);
			}
		}
		

		if (Math.abs(player.getCX() - player2.getCX()) < 100 &&
				Math.abs(player.getCY() - player2.getCY()) < 100) {
			if (player.inAir() && player2.inAir()) {
				if (player.knocking() && !player2.knocking()) { 
					player2.spike(30);
				}
				if (player2.knocking() && !player.knocking()) {
					player.spike(30);
					}
			}
		}
		if(p1dead&&p2dead&&gibCounter<50){//if you both die
			p1gibs();
			p2gibs();
			gibCounter++;
		}
		
		if (player.isDead() && !p1dead) {
			p1gibs();
		}
		
		if (player2.isDead() && !p2dead) {
			p2gibs();
		}
	}
	private void p1gibs(){
		body = new Gib(player.getCX(), player.getCY(), 4-Math.random()*8,-16+Math.random()*8,"guy_gib0");
		leg1 = new Gib(player.getCX(), player.getCY(), 4-Math.random()*8,-16+Math.random()*8,"guy_gib1");
		leg2 = new Gib(player.getCX(), player.getCY(), 4-Math.random()*8,-16+Math.random()*8,"guy_gib1");
		arm1 = new Gib(player.getCX(), player.getCY(), 4-Math.random()*8,-16+Math.random()*8,"guy_gib2");
		arm2 = new Gib(player.getCX(), player.getCY(), 4-Math.random()*8,-16+Math.random()*8,"guy_gib2");
		head = new Gib(player.getCX(), player.getCY(), 4-Math.random()*8,-16+Math.random()*8,"guy_gib3");
		add(body); add(leg1); add(leg2); add(arm1); add(arm2); add(head);
		remove(player);
		p1dead = true;
	}
	private void p2gibs(){
		bbody = new Gib(player2.getCX(), player2.getCY(), 4-Math.random()*8,-16+Math.random()*8,"bguy_gib0");
		bleg1 = new Gib(player2.getCX(), player2.getCY(), 4-Math.random()*8,-16+Math.random()*8,"bguy_gib1");
		bleg2 = new Gib(player2.getCX(), player2.getCY(), 4-Math.random()*8,-16+Math.random()*8,"bguy_gib1");
		barm1 = new Gib(player2.getCX(), player2.getCY(), 4-Math.random()*8,-16+Math.random()*8,"bguy_gib2");
		barm2 = new Gib(player2.getCX(), player2.getCY(), 4-Math.random()*8,-16+Math.random()*8,"bguy_gib2");
		bhead = new Gib(player2.getCX(), player2.getCY(), 4-Math.random()*8,-16+Math.random()*8,"bguy_gib3");
		add(bbody); add(bleg1); add(bleg2); add(barm1); add(barm2); add(bhead);
		remove(player2);
		p2dead = true;
	}
	private void keys() {
		player.leftKeyPressed(keyPressed('S')); 
		player.rightKeyPressed(keyPressed('F')); 
		player.upKeyPressed(keyPressed('E')); 
		player.downKeyPressed(keyPressed('D')); 
		player.shootKeyPressed(keyPressed('A'));
		player2.cheapKeyPressed(keyPressed('M'));
		player.cheapKeyPressed(keyPressed('X'));
		player2.leftKeyPressed(keyPressed('J')); 
		player2.rightKeyPressed(keyPressed('L')); 
		player2.upKeyPressed(keyPressed('I')); 
		player2.downKeyPressed(keyPressed('K')); 
		player2.shootKeyPressed(keyPressed('H'));
		
		

		/*if (keyPressed('B') && (p1dead || p2dead)) {
			remove(body); remove(leg1); remove(leg2); remove(arm1); remove(arm2); remove(head);
			remove(bbody); remove(bleg1); remove(bleg2); remove(barm1); remove(barm2); remove(bhead);
			add(player);
			add(player2);
		}*/
	}
}