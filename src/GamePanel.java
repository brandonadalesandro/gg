import java.awt.*;

import java.awt.event.*;

import java.util.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, MouseListener, KeyListener, MouseMotionListener{
	public static void main(String[] args){
		//GameFrame f = new GameFrame();
		GamePanel p = new GamePanel();
	}

	//width and height of the window
	private int WINDOW_W = 640;
	private int WINDOW_H = 480;
	
	private Thread t;
	
	private GameFrame frame;
	private MouseSelection mouseSelection;
	private WallBoard wallBoard;
	private HeadsUpDisplay hud;
	
	private Player player;
	private Enemy enemy;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();


	boolean inRange = false;
	boolean isLeftClick = false;
	boolean isRightClick = false;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	//minion spawners
	List<Rectangle> spawnPoints = new ArrayList<Rectangle>();
	
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
		frame.setContentPane(this);
		frame.addKeyListener(this);
		frame.pack();
		
		t = new Thread(this);
		t.start();
		hud = new HeadsUpDisplay(getWidth(), getHeight());
		
		spawnPoints.add(new Rectangle(0, 0, 20, 20));
		spawnPoints.add(new Rectangle(WINDOW_W - 20, 0, 20, 20));
		spawnPoints.add(new Rectangle(0, WINDOW_H, 20, 20));
		spawnPoints.add(new Rectangle(WINDOW_W - 20, WINDOW_H - 20, 20, 20));
	}
	
	@Override
	public void run() {
		int counter = 0;
		while(true){
			repaint();
			
			if(counter > 200){
				player.update(wallBoard.getObjects());
				wallBoard.update(objects);
				
				for(GameObject o : objects){
					o.update(wallBoard.getObjects(), player);
				}
			}
			
			counter++;
			try{t.sleep(10);}catch(InterruptedException e){ } 
		}
		
	}

	public void paint(Graphics g){
		g.setColor(Color.white);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		player.draw(g);
		mouseSelection.draw(g);
		wallBoard.draw(g);
		hud.draw(g);
		
		for(GameObject o : objects)
			o.draw(g);
		
		g.setColor(Color.black);
		String s = "( " + mouseX + " , " + mouseY + " )";
		g.drawString(s, mouseX - s.length()*3, mouseY + 2);
		
		g.setColor(Color.red);
		for(Rectangle r : spawnPoints){
			g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
		}
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
		
		//if it is clicked, spawn enemies
		for(Rectangle r : spawnPoints){
			if(r.contains(e.getX(), e.getY())){
				objects.add(new Enemy((int)(r.getX() + r.getWidth()), (int)(r.getY() + r.getHeight())));
				System.out.println("adding enemy");
			}
		}
		
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
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	  /////////////////////////////////////////////////
	 //                 END EVENTS                  //
	/////////////////////////////////////////////////
	
	public Dimension getPreferredSize(){
		return new Dimension(WINDOW_W, WINDOW_H);
	}
}
