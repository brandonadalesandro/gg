import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HeadsUpDisplay {
	//todo add constructor for a variable player (ie health, ammo w/e)
	private Map<String, GameObject> elements;
	private List<Rectangle> hitboxes;
	private int maxHealth, curHealth;
	private int maxEnergy, curEnergy;
	private int windowWidth, windowHeight;
	Map<String, GameObject> currEquipment;
	Rectangle hudRect;
	private int HUD_W, HUD_H;
	public HeadsUpDisplay(int w, int h){
		HUD_W = w;
		HUD_H = h/10;
		hudRect = new Rectangle(HUD_W, HUD_H);
		currEquipment =  new HashMap<String, GameObject>();
		int spacing = w/4;
		elements = new HashMap<String, GameObject>();
		elements.put("Wall", new Wall(110, h - 100, true));
		elements.put("Turret", new Turret(220, h - 100, true));
		elements.put("Sword & Shield", new Sword(330, h - 100));
		windowWidth = w; windowHeight = h;
		
		hitboxes = new ArrayList<Rectangle>();
		int counter = 1;
		for(String s : elements.keySet()){
			int dy = hudRect.height/2 + elements.get(s).getHeight();
			int dx = (int) (counter*(hudRect.getWidth()/(1 + elements.size())));
			elements.get(s).setX(dx);
			elements.get(s).setY(windowHeight - dy);
			
			int hitboxWidth = (int) (hudRect.getWidth()/(1 + elements.size()));
			hitboxes.add(new Rectangle(dx - hitboxWidth/2, (int)hudRect.getY() + 1, hitboxWidth, HUD_H - 4));
			
			counter++;
		}


		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.drawRect(1, windowHeight - (int)hudRect.getHeight(), (int)hudRect.getWidth(), (int)hudRect.getHeight() - 1);
		
		//todo: update positions of elements using graphics g
		int counter = 1;
		for(String s : elements.keySet()){
			int dx = elements.get(s).getX() - (s.length()/2)*5;
			
			//draw string fro inventory item
			g.drawString(s, dx, elements.get(s).getY() - 2);
			//draw inventory item
			elements.get(s).draw(g);
			counter++;
		}
		
		for(Rectangle r : hitboxes){
			g.drawRect((int)r.getX(), windowHeight - (int)hudRect.getHeight(), (int)r.getWidth(), (int)r.getHeight());
		}
		
		
	}
	
	public boolean click(int x, int y){
		Map<String, GameObject> result = new HashMap<String, GameObject>();
		for(String s : elements.keySet()){
			Rectangle r = new Rectangle(elements.get(s).getX(), elements.get(s).getY(), elements.get(s).getWidth(), elements.get(s).getHeight());
			if(r.contains(x, y)){
				currEquipment.clear();
				currEquipment.put(s, elements.get(s).getNew(0, 0));
				return true;
			}
		}
		return false;
	}
	
	public Map<String, GameObject> getEquipment(){
		return currEquipment;
	}
}
