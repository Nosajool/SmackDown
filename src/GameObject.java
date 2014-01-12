import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public abstract class GameObject extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage sprite;
	private int spriteFlip = 1;
	private double spriteAngle;
	private double x;
	private double y;
	
	public GameObject() {}

	public void setSize(int w, int h) { super.setSize(w, h); }
	public int width() { return getWidth(); }
	public int height() { return getHeight(); }
	public int getX() { return getLocation().x; }
	public int getY() { return getLocation().y; }
	public int getCX() { return getLocation().x + width() / 2; }
	public int getCY() { return getLocation().y + height() / 2; }
	public double x() { return this.x; }
	public double y() { return this.y; }
	
	public void setX(double x) {
		this.x = x;
		super.setLocation((int)(this.x + 0.5), getLocation().y);
	}
	
	public void setY(double y) {
		this.y = y;
		super.setLocation(getLocation().x, (int)(this.y + 0.5));
	}
	
	public void flipSprite(int f) {
		spriteFlip = f;
	}
	
	public void setSprite(String fileName) {
		Image i = null;
		
		try { i = ImageIO.read(new File("img/" + fileName));
		} catch (IOException e) { }
		
    	ImageFilter filter = new RGBImageFilter() {
    		public final int filterRGB(int x, int y, int rgb) {
    			if (rgb == Color.GREEN.getRGB()) {
    				return 0x00FFFFFF & rgb;
    			}
    			return rgb;
    		}
    	};
    	
		i = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(i.getSource(), filter));
		BufferedImage bufferedImage = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = bufferedImage.createGraphics();
    	g.drawImage(i, 0, 0, null);
    	g.dispose();
    	sprite = bufferedImage;
	}
	
	public BufferedImage getSprite() { return sprite; }
	public void scaleSprite(double scale) { setSize((int)(sprite.getWidth() * scale + 0.5), (int)(sprite.getHeight() * scale + 0.5)); }
	
	public void rotate(double degrees) { spriteAngle = degrees / 180 * Math.PI; }
	public double getRotation() { return spriteAngle * 180 / Math.PI; }
 
	public void paintComponent(Graphics g) {
		try {
			AffineTransform t = new AffineTransform();
			if (spriteAngle != 0) { t.rotate(spriteAngle, getWidth() / 2, getHeight() / 2); }
			t.scale(getWidth() * 1.0 / sprite.getWidth(), getHeight() * 1.0 / sprite.getHeight());
			Graphics2D g2d = (Graphics2D) g;
	    	if (spriteFlip == -1) {
		    	g2d.scale(-1.0, 1.0);
		    	g2d.translate(-getWidth(), 0.0);
	    	}
	        g2d.drawImage(sprite, t, null);
		}
		catch (Exception e) { g.drawImage(sprite, 0, 0, getWidth(), getHeight(), this); }
    }

	public boolean collides(GameObject o) {
		return getBounds().intersects(o.getBounds());
	}

	public abstract void act();
}