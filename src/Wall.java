import java.awt.Color;
import java.awt.Graphics;

public class Wall extends PlaceableGameObject{
	private int x, y;
	public Wall(int x, int y){
		this(x, y, false);
	}
	
	public Wall(int x, int y, boolean isHud){
		super(x, y, 20, 20, isHud);
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
		
		g.setColor(wallOutline);
		g.drawRect(x, y, getWidth(), getHeight());
		g.setColor(wallInside);
		g.fillRect(x + 1, y + 1, getWidth() - 1, getHeight() - 1);
	}
}
