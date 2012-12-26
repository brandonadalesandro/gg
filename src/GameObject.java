import java.awt.Color;
import java.awt.Graphics;


public class GameObject {
	private int x, y, w, h;
	private boolean isHud = false;
	public GameObject(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public GameObject(int x, int y, int w, int h, boolean isHud){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isHud = isHud;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
	}	
	
	public void draw(Graphics g, double rotation, int axisx, int axisy){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
	}
	
	public void update(){
	
	}
	
	//getter methods//
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return w;
	}
	
	public int getHeight(){
		return h;
	}
	
	//setter methods//
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setWidth(int w){
		this.w = w;
	}
	
	public void setHeight(int h){
		this.h = h;
	}
	
	public boolean isHud(){
		return isHud;
	}
	
	public void leftAction(){
		
	}
	
	public void rightAction(){
		
	}
}
