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
	
	public HeadsUpDisplay(int w, int h){
		elements = new HashMap<String, GameObject>();
		elements.put("Wall", new Wall( 10, h));
		elements.put("Turret", new Turret(20, h));
		elements.put("Sword & Shield", new Sword(30, h));
		//todo find a way to calculate these positions!
	}
	
	public void draw(Graphics g){
//		g.setColor(Color.blue);
//		g.drawRect(10, 10, curEnergy, 50);
		
		//todo: update positions of elements using graphics g
		for(String s : elements.keySet())
			elements.get(s).draw(g);
	}
	
	public Map<String, GameObject> click(int x, int y){
		Map<String, GameObject> result = new HashMap<String, GameObject>();
		for(String s : elements.keySet()){
			Rectangle r = new Rectangle(elements.get(s).getX(), elements.get(s).getY(), elements.get(s).getWidth(), elements.get(s).getHeight());
			if(r.contains(x, y))
				result.put(s, elements.get(s));
		}
		return null;
	}
}
