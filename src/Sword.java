import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;


public class Sword extends GameObject{

	public Sword(int x, int y) {
		super(x, y, 2, 10);
	}
	
	public void draw(Graphics g, double rotationRads, int axisx, int axisy){
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform rotate = AffineTransform.getRotateInstance(rotationRads, axisx, axisy);
		Shape s = rotate.createTransformedShape(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		g2d.setColor(Color.red);
		g2d.draw(s);
	}
	
	public Sword getNew(int x, int y){
		return new Sword(x, y);
	}

}
