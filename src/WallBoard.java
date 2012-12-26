import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.*;
public class WallBoard {
	
	List<PlaceableGameObject> objects;
	List<PlaceableGameObject> potentialObjects;
	boolean isLeftClick, isRightClick;
	
	//initializes the wall/potential wall lists so that walls can come and go as they please.
	public WallBoard(){
		objects = new ArrayList<PlaceableGameObject>();
		potentialObjects = new ArrayList<PlaceableGameObject>();
	}
	
	public void update(List<GameObject> obs){
		for(PlaceableGameObject o : objects)
			o.update(obs);
	}
	
	//draws the walls & the potential walls
	public void draw(Graphics g){
		//draw the existing walls
			for(GameObject w : objects)
				w.draw(g); 
			
			
			//draw the lineWalls
			for(GameObject w : potentialObjects)
				w.draw(g);
	}
	
	//TODO: generalize this for all placeablegameobjects
	//puts the wall (x - width/2, y - height/2) so that the user is placing the walls in the middle of the wall 
	public void putPotentialObject(PlaceableGameObject object, List<GameObject> ob){
		System.out.println("debug: in putPotentialObject()");
			isLeftClick = true;
			PlaceableGameObject newOb = object;
			newOb.setX(newOb.getX() - newOb.getWidth()/2);
			newOb.setY(newOb.getY() - newOb.getHeight()/2);
			newOb.isReal = false;
			//check collision with the game objects on the map (player/enemy)
			for(GameObject o : ob){
				if(Helper.isBoundingBoxCollision(o.getX(), o.getY(), o.getWidth(), o.getHeight(),
						newOb.getX(), newOb.getY(), newOb.getWidth(), newOb.getHeight())){
					newOb.isBlocked = true;
				}
			}
			
			//check collision with other walls already on the map
			for(GameObject w : objects){
				if(Helper.isBoundingBoxCollision(w.getX(), w.getY(), w.getWidth(), w.getHeight(),
						newOb.getX(), newOb.getY(), newOb.getWidth(), newOb.getHeight())){
					newOb.isBlocked = true;
				}
			}
			boolean b = true;
			//check collision with other potential walls that are alrady on the map
			for(GameObject w : potentialObjects){
				if(Helper.isBoundingBoxCollision(w.getX(), w.getY(), w.getWidth(), w.getHeight(),
						newOb.getX(), newOb.getY(), newOb.getWidth(), newOb.getHeight())){
					b = false;
					System.out.println("colliding");
				}
			}
			
			//if new wall isn't colliding with any current potential walls, add it
			if(b){
				potentialObjects.add(newOb);
				System.out.println("adding object");
			}
	}
	
	//adds the valid potential walls to the board
	public void push(){
		System.out.println("debug: in push()");
		for(PlaceableGameObject w : potentialObjects){
			if(!w.isBlocked){
				w.isReal = true;
				objects.add(w);
			}
		}
		potentialObjects.clear();
	}
	
	public void destroyObjectsInArea(Rectangle r){
		//Rectangle r = new Rectangle(x, y, width, height);
		for(int i = 0; i < objects.size();){
			PlaceableGameObject w = objects.get(i);
			Rectangle obR = new Rectangle(w.getX(), w.getY(), w.getWidth(), w.getHeight());
			System.out.println("in destroywallsinarea");
			
			if(r.contains(obR) || r.intersects(obR)){
				objects.remove(i);
				System.out.println("destroying wall");
			}
			else
				i++;
		}
	}
	
	public void clearPotentialObjects(){
		potentialObjects.clear();
	}
	
	public List<PlaceableGameObject> getObjects(){
		return Collections.unmodifiableList(objects);
	}
}
