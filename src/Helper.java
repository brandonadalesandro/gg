import java.awt.Rectangle;


public class Helper {
	public static boolean isBoundingBoxCollision(int x1, int y1, int w1, int h1, 
							int x2, int y2, int w2, int h2){
		Rectangle r1 = new Rectangle(x1, y1, w1, h1);
		Rectangle r2 = new Rectangle(x2, y2, w2, h2);
		return r1.intersects(r2);
	}
	public static boolean isBoundingBoxCollision(double x1, double y1, int w1, int h1, 
		double x2, double y2, int w2, int h2){
		Rectangle r1 = new Rectangle((int)x1, (int)y1, w1, h1);
		Rectangle r2 = new Rectangle((int)x2, (int)y2, w2, h2);
		return r1.intersects(r2);
}
}
