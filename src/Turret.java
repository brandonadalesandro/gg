import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Turret extends PlaceableGameObject{
	private double rotation;
	private int SPEED = 1;
	private int RANGE = 10;
	private GameObject target;
	private List<Bullet> bullets;
	private Shape barrel;
	int counter = 0;
	
	public Turret(int x, int y){
		this(x, y, false);
	}
	
	public Turret(int x, int y, boolean isHud){
		super(x, y, 20, 20, isHud);
		bullets = new ArrayList<Bullet>();
	}
	
	
	public void draw(Graphics g){
		super.draw(g);
		
		if(isReal){insideColor = Color.black; outsideColor = Color.cyan;}
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform rotate = AffineTransform.getRotateInstance(rotation, getX() + getWidth()/2, getY() + getHeight()/2);
		barrel = rotate.createTransformedShape(new Rectangle(getX() + getWidth()/2, getY() - getHeight()/2, 2, getHeight()/2 + 3));
		g2d.setColor(insideColor);
		g2d.draw(barrel);
		g2d.setColor(outsideColor);
		g2d.drawRect(getX(), getY(), getWidth(), getHeight());
		
		if(target != null)
			g2d.drawLine(getX(), getY(), target.getX(), target.getY());
		
		for(int i = 0; i < bullets.size(); i++)
			bullets.get(i).draw(g);
	}
	
	//pre : passes enemies to the turret
	//post: turret follows/shoots at the nearest enemy if it's in range
	public void update(List<GameObject> objects){
		counter++;
		boolean isAssigned = false;
		if(target != null){
			for(GameObject o : objects){
				if(inRange(o.getX(), o.getY()) && o == target && !o.getClass().isInstance(new Player(0, 0))){
					isAssigned = true;
					target = o;
					updateRotation(o.getX(), o.getY());
					break;
				}
			}
		}else{
			for(GameObject o : objects){
				if(inRange(o.getX(), o.getY()) && !o.getClass().isInstance(new Player(0, 0))){
					updateRotation(o.getX(), o.getY());
					target = o;
					break;
				}
			}
		}
		
		
		//todo: add fire rate, add reload bar
		if(!isHud() && isAssigned && counter > 100 && target != null && inRange(target.getX(), target.getY())){
			updateRotation(target.getX(), target.getY());
			fire(target);
			counter = 0;
		}
		
		for(Bullet b : bullets)
			b.update();
		
	} 
	
	public void updateRotation(int x, int y){
		double o = 1;
		double a = 1;
		
		o = getY() - y;
		a = x - getX();
		
		rotation = -Math.atan2(o, a)  + Math.PI/2;	
	
	}
	
	public void fire(GameObject o){
		int dx = getX() - o.getX();
		int dy = o.getY() - getY();
		bullets.add(new Bullet((int)barrel.getBounds2D().getX(), (int)barrel.getBounds2D().getY(), SPEED, new NVector(dx, dy)));
	}


	public boolean inRange(int x, int y){
		return RANGE <= Math.sqrt(Math.pow(this.getX() - x, 2) + Math.pow(this.getY() - y, 2));
	}
	
	private class Bullet extends GameObject{
		private int SPEED;
		private NVector direction;
		public Bullet(int x, int y, int speed, NVector v) {
			super(x, y, 2, 2);
			SPEED = speed;
			direction = v;
			direction.normalize();
		}
		
		public void update(){
			setX((int)(getX() + direction.getDX()*SPEED));
			setY((int)(getY() + direction.getDY()*SPEED));
		}
		
		public void draw(Graphics g){
			g.drawOval(getX(), getY(), getWidth(), getHeight());
		}
	}
}
