import java.awt.Color;
import java.awt.Graphics;

public class Wall extends PlaceableGameObject{
	private int x, y;
	public Wall(int x, int y){
		this(x, y, false);
	}
	
	public Wall(int x, int y, boolean isHud){
		super(x, y, 10, 10, isHud);
		if(isHud){
			isReal = true;
			isBlocked = false;
		}
	}
	
	public Wall(Wall w){
		super(w.getX(), w.getY(), w.getWidth(), w.getHeight());
	}
	
	public void draw(Graphics g){
		Color wallInside = Color.black;
		Color wallOutline = Color.white;
		
		if(isReal){wallInside = Color.white;wallOutline = Color.black;}
		else{wallInside = Color.green; wallOutline = Color.black;}
		if(isBlocked){wallInside = Color.red; wallOutline = Color.LIGHT_GRAY;}
		
		if(isHud()){
			insideColor = Color.white;
			outsideColor = Color.black;
		}
		
		g.setColor(insideColor);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(outsideColor);
		g.fillRect(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);
	}
}
