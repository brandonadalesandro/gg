import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;


public class HeadsUpDisplay {
	//todo add constructor for a variable player (ie health, ammo w/e)
	private Map<String, GameObject> elements;
	private int maxHealth, curHealth;
	private int maxEnergy, curEnergy;
	private int windowWidth, windowHeight;
	Map<String, GameObject> currEquipment;
	
	public HeadsUpDisplay(int w, int h){
		currEquipment =  new HashMap<String, GameObject>();
		int spacing = w/4;
		elements = new HashMap<String, GameObject>();
		elements.put("Wall", new Wall(110, h - 100, true));
		elements.put("Turret", new Turret(220, h - 100, true));
		elements.put("Sword & Shield", new Sword(330, h - 100));
		windowWidth = w; windowHeight = h;
		//todo find a way to calculate these positions!
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
//		g.drawRect(10, 10, curEnergy, 50);
		
		//todo: update positions of elements using graphics g
		int counter = 1;
		for(String s : elements.keySet()){
			g.drawString(s, 110 * counter, windowHeight - 50);
			elements.get(s).draw(g);
			counter++;
		}
		
		
	}
	
	public boolean click(int x, int y){
		Map<String, GameObject> result = new HashMap<String, GameObject>();
		for(String s : elements.keySet()){
			Rectangle r = new Rectangle(elements.get(s).getX(), elements.get(s).getY(), elements.get(s).getWidth(), elements.get(s).getHeight());
			if(r.contains(x, y)){
				currEquipment.clear();
				currEquipment.put(s, elements.get(s));
				return true;
			}
		}
		return false;
	}
	
	public Map<String, GameObject> getEquipment(){
		return currEquipment;
	}
}
