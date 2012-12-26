import java.awt.*;

import java.awt.event.*;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, MouseListener, KeyListener, MouseMotionListener{
	public static void main(String[] args){
		//GameFrame f = new GameFrame();
		GamePanel p = new GamePanel();
	}

	//dem walls
	GameFrame frame;
	Wall potentialWall;
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Wall> lineWalls = new ArrayList<Wall>();
	Player player;
	Enemy enemy;
	MouseSelection mouseSelection;
	WallBoard wallBoard;
	HeadsUpDisplay hud;
	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Thread t;
	int mouseX = 0;
	int mouseY = 0;
	boolean inRange = false;
	boolean isLeftClick = false;
	boolean isRightClick = false;
	
	public GamePanel(){ 
		potentialWall = new Wall(mouseX, mouseY);
		wallBoard = new WallBoard();
		player = new Player(100, 100);
		enemy = new Enemy(10, 10);
		objects.add(player);
		objects.add(enemy);
		mouseSelection = new MouseSelection();
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		frame = new GameFrame();
		frame.addKeyListener(this);
		frame.add(this);
		
		t = new Thread(this);
		t.start();
		
		hud = new HeadsUpDisplay(this.getWidth(), this.getHeight());
	}
	
	@Override
	public void run() {
		int counter = 0;
		while(true){
			repaint();
			
			if(counter > 200){
				player.update(wallBoard.getObjects());
				enemy.update(wallBoard.getObjects(), player);
				wallBoard.update(objects);
			}
			
			counter++;
			try{t.sleep(10);}catch(InterruptedException e){ } 
		}
		
	}

	public void paint(Graphics g){
		g.setColor(Color.white);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		player.draw(g);
		enemy.draw(g);
		mouseSelection.draw(g);
		wallBoard.draw(g);
		//draw the existing walls
		for(Wall w : walls)
			w.draw(g); 
		
		//draw the potential wall
		potentialWall.draw(g);
		
		//draw the lineWalls
		for(Wall w : lineWalls)
			w.draw(g);
	}
	
	public void addWall(int x, int y){
		Wall wall = new Wall(potentialWall.getX(), potentialWall.getY());
		//if the player is colliding with the potential wall, no wall
		//if(Helper.isBoundingBoxCollision(x, y, wall.width, wall.height, player.getX(), player.getY(), player.width, player.height)){return;}
		
		//if the distance between the player and the potential wall is too high, no wall
		if(!player.inRange(wall.getX(), wall.getY())){return;}
		//for(Wall w : walls){
			//if the potential wall is colliding with any existing walls, no wall
			//if(Helper.isBoundingBoxCollision(x, y, wall.width, wall.height, w.getX(), w.getY(), w.width, w.height)){return;}
		//}
		//TODO add collision for enemies, other terrain items etc
		wall.isReal = true;
		walls.add(wall);
	}
	
	public void destroyWall(int x, int y){
		for(Wall w : walls){
			//if the potential wall is colliding with any existing walls, no wall
			if((Helper.isBoundingBoxCollision(x, y, 1, 1, w.getX(), w.getY(), w.getWidth(), w.getHeight())) && (Math.abs(Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2) )) <=  player.RANGE)){walls.remove(w);}
		}
	}

	  /////////////////////////////////////////////////
	 //                   EVENTS                    //
	/////////////////////////////////////////////////
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			frame.dispose();
		player.update(e, walls);
		//System.out.println("keypressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.update(e);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {//i just farted and it smells
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if(e.getButton() == MouseEvent.BUTTON1){
			isLeftClick = true;
			//click the hud?
			Map<String, GameObject> result = hud.click(e.getX(), e.getY());
			if(result != null){
				//the user selected something on the hud, pass it to the player.
				player.equip(result);
			}else if(player.inRange(e.getX(), e.getY()))
				wallBoard.putPotentialObject(new Turret(e.getX(), e.getY()), objects);
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			isRightClick = true;
			wallBoard.clearPotentialObjects();
			destroyWall(e.getX(), e.getY());
		}

		mouseSelection.mouseClick(e);
		
		mouseX = e.getX();
		mouseY = e.getY();
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isLeftClick = false;
		isRightClick = false;
		lineWalls = new ArrayList<Wall>();
		
		//new
		if(e.getButton() == MouseEvent.BUTTON1)
			wallBoard.push();
		else if(e.getButton() == MouseEvent.BUTTON3){
			mouseSelection.mouseRelease(e);
			wallBoard.destroyObjectsInArea(mouseSelection.getRect());
		}
			
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//update the mouseSelection
		mouseSelection.mouseDrag(e);
		if(isLeftClick)
			wallBoard.putPotentialObject(new Turret(e.getX(), e.getY()), objects);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		player.updateRotation(e.getX(), e.getY());
	}
}
