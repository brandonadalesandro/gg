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
		wallBoard = new WallBoard();
		player = new Player(100, 100);
		Map<String, GameObject> startingEquipment = new HashMap<String, GameObject>();
		startingEquipment.put("Sword and Sheild", new Sword(player.getX(), player.getY()));
		player.equip(startingEquipment);
		enemy = new Enemy(10, 10);
		objects.add(player);
		objects.add(enemy);
		mouseSelection = new MouseSelection();
		
		setSize(new Dimension(640, 480));
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
		hud = new HeadsUpDisplay(getWidth(), getHeight());
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
		hud.draw(g);
	}
	
	  /////////////////////////////////////////////////
	 //                   EVENTS                    //
	/////////////////////////////////////////////////
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			frame.dispose();
		player.update(e, wallBoard.getObjects());
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
			if(hud.click(e.getX(), e.getY())){
				//the user selected something on the hud, pass it to the player.
				player.equip(hud.getEquipment());
			}else
				player.doLeftAction(wallBoard, objects, e.getX(), e.getY());
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			isRightClick = true;
			wallBoard.clearPotentialObjects();
		}

		mouseSelection.mouseClick(e);
		
		mouseX = e.getX();
		mouseY = e.getY();
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isLeftClick = false;
		isRightClick = false;;
		
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
		if(isLeftClick){
			player.doLeftAction(wallBoard, objects, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		player.updateRotation(e.getX(), e.getY());
	}
}
