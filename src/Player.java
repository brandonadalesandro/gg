import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.*;


public class Player extends GameObject{

	//todo: implement a currently equipped system
	private final int SPEED = 2;
	public int RANGE = 250;
	private double rotation;
	private Sword sword;
	boolean isDown = false; boolean isUp = false;
	boolean isRight = false; boolean isLeft = false;
	int mousex, mousey;
	GameObject equipment;
	
	public Player(int x, int y){
		super(x, y, 20, 20);
		
		rotation = 0;
	}
	
	//draw the player (currently a circle) and the equipment (currently just a sword)
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.drawOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		//sword.draw(g2d, rotation, this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
		g2d.drawLine(getX() + getWidth()/2, getY() + getHeight()/2, mousex, mousey);
		g2d.rotate(0);
	}
	
	//update movement, avoid walls
	public void update(List<PlaceableGameObject> list){	
		if(isUp){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX(), getY() - SPEED, getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				setY(getY() - SPEED);
		}
		if(isDown){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX(), getY() + SPEED, getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				setY(getY() + SPEED);
		}
		if(isLeft){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX() - SPEED, getY(), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				setX(getX() - SPEED);
		}
		if(isRight){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX() + SPEED, getY(), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				setX(getX() + SPEED);
		}
		
		//sword.setX(this.getX() + this.getWidth()/2);
		//sword.setY(this.getY() - sword.getHeight());
	}
	
	//this method handles the downpress
	//update for keypress
	public void update(KeyEvent e, List<PlaceableGameObject> list){
		//w--up
		//s--down
		//a--left
		//d--right
		
		if(e.getKeyCode() == KeyEvent.VK_W){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX(), getY() - SPEED, getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				isUp = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX(), getY() + SPEED, getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				isDown = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX() - SPEED, getY(), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				isLeft = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_D){
			boolean b = true;
			for(PlaceableGameObject w : list)
				if(Helper.isBoundingBoxCollision(getX() + SPEED, getY(), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){b = false;}
			if(b)
				isRight = true;
		}
		System.out.println("UP: " + isUp);
		System.out.println("DOWN: " + isDown);
		System.out.println("LEFT: " + isLeft);
		System.out.println("RIGHT: " + isRight);
		
	}
	
	//method for keyreleased, set the boolean corresponding to the key false!
	public void update(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W)
			isUp = false;
		else if(e.getKeyCode() == KeyEvent.VK_S)
			isDown = false;
		else if(e.getKeyCode() == KeyEvent.VK_A)
			isLeft = false;
		else if(e.getKeyCode() == KeyEvent.VK_D)
			isRight = false;
		
	}
	
	//update the rotation of the player
	public void updateRotation(int x, int y){
		double o = 1;
		double a = 1;
		
		o = getY() - y;
		a = x - getX();
		
		rotation = -Math.atan2(o, a)  + Math.PI/2;
		
		mousex = x;
		mousey = y;
		
	}
	
	//check if the coordinates are within the player's range
	public boolean inRange(int x, int y){
		return RANGE <= Math.sqrt(Math.pow(this.getX() - x, 2) + Math.pow(this.getY() - y, 2));
	}
	
	public void equip(Map<String, GameObject> e){
		for(String s : e.keySet())
			equipment = e.get(s);
	}
	
	public void doLeftAction(WallBoard b, List<GameObject> obs){
		//it's placeable!
		if(equipment instanceof PlaceableGameObject){
			
			b.putPotentialObject(((PlaceableGameObject)equipment).leftAction(b), obs);
		//it's not placeable, but it's not null!:D	
		}else if(equipment instanceof GameObject){
			
			
			
			
		}
	}
	
	public void doRightAction(WallBoard b){
		
	}
	
}
