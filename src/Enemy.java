import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Enemy extends GameObject{
	NVector vectorToPlayer = null;
	NVector direction = null;
	private int SPEED = 1;
	/*wall collision
	 * 0-right ->y
	 * 1-bottom ->x
	 * 2-left ->y
	 * 3-top ->x
	 */
	private int WALL_COLLISION = -1;
	
	public Enemy(int x, int y){
		super(x, y, 10, 10);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.red);
		g.drawOval((int)getX(), (int)getY(), getWidth(), getHeight());
	}
	
	//finds te player
	private void findPlayer(int x, int y){
		vectorToPlayer = new NVector(x - super.getX(), y - super.getY());
		vectorToPlayer.normalize();
	}
	
	//handles basic logic, override to implement a different ai
	public void update(List<PlaceableGameObject> list, Player p){
		
		findPlayer(p.getX(), p.getY());
		
		boolean isCollision = false;
//		System.out.println(isCollision);
		//if movement straight towards the player is blocked, move along the walls
		for(PlaceableGameObject w : list){
			if(Helper.isBoundingBoxCollision((int)(getX() + vectorToPlayer.getDX() * SPEED), (int)(getY() + vectorToPlayer.getDY() * SPEED), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){
				isCollision = true;
				
				if(Math.abs(vectorToPlayer.getDX()) > Math.abs(vectorToPlayer.getDY())){
					if(vectorToPlayer.getDX() > 0)
						WALL_COLLISION = 3;
					else
						WALL_COLLISION = 1;
				}
				else if(Math.abs(vectorToPlayer.getDX()) < Math.abs(vectorToPlayer.getDY())){
					if(vectorToPlayer.getDY() > 0)
						WALL_COLLISION = 0;
					else
						WALL_COLLISION = 2;
				}
			
			}
		}
		//System.out.println(isCollision);
		//set the direction to the straight on vector, to be reset if there is a collision on this path
		direction = vectorToPlayer;
		
		if(isCollision){
			//reset the variable, don't mind that what this is named is completely opposite = PIMPIN'
			isCollision = false;
			
			//scale dem walls son, and see when the path is clear
			for(PlaceableGameObject w : list){
				if(WALL_COLLISION == 0 && !Helper.isBoundingBoxCollision(getX() + SPEED, getY(), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){
					WALL_COLLISION = 3;
					isCollision = true;
				}
				else if(WALL_COLLISION == 1 && !Helper.isBoundingBoxCollision(getX(), getY() + SPEED, getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){
					WALL_COLLISION--;
					isCollision = true;
				}
				else if(WALL_COLLISION == 2 && !Helper.isBoundingBoxCollision(getX() - SPEED, getY(), getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){
					WALL_COLLISION--;
					isCollision = true;
				}
				else if(WALL_COLLISION == 3 && !Helper.isBoundingBoxCollision(getX(), getY() - SPEED, getWidth(), getHeight(), w.getX(), w.getY(), w.getWidth(), w.getHeight())){
					WALL_COLLISION--;
					isCollision = true;
				}
			}
			
			//if there is NOT a wall on the designated side, set the vector accoridingly
			if(isCollision){
				if(WALL_COLLISION == 0)
					direction = new NVector(0, 1);
				else if(WALL_COLLISION == 1)
					direction = new NVector(1, 0);
				else if(WALL_COLLISION == 2)
					direction = new NVector(0, -1);
				else if(WALL_COLLISION == 3)
					direction = new NVector(-1, 0);
			}
		}
		setX((int)(getX() + Math.round(direction.getDX()*SPEED)));
		setY((int)(getY() + Math.round(direction.getDY()*SPEED)));
	}
}
