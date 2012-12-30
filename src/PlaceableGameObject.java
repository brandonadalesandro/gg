import java.awt.Color;
import java.awt.Graphics;
import java.util.List;


public class PlaceableGameObject extends GameObject{
	public boolean isReal = false;
	public boolean isBlocked = false;
	public Color insideColor, outsideColor;
	
	public PlaceableGameObject(PlaceableGameObject o){
		this(o.getX(), o.getY(), o.getWidth(), o.getHeight(), false);
	}
	
	public PlaceableGameObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	public PlaceableGameObject(int x, int y, int w, int h, boolean isHud) {
		super(x, y, w, h, isHud);
		// TODO Auto-generated constructor stub
	}
	
	public void update(List<GameObject> obs){} 
	
	//sets the color for when the object is blocked/non-real
	//always do a super call to avoid nullpointers
	public void draw(Graphics g){
		insideColor = Color.black;
		outsideColor = Color.white;
		
		
		if(!isReal){insideColor = Color.white;outsideColor = Color.black;}
		if(isBlocked){insideColor = Color.red; outsideColor = Color.LIGHT_GRAY;}
	}
	
	public PlaceableGameObject leftAction(WallBoard b){
		return this;
	}
	
	public PlaceableGameObject getNew(int x, int y){
		return new PlaceableGameObject(x, y, 10, 10);
	}
	

}
