import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;


public class MouseSelection {
	private boolean isRightDown;
	private boolean isLeftDown;
	private int mouseClickX, mouseClickY, mouseDragX, mouseDragY;
	private Rectangle currRect;
	
	public MouseSelection(){
		currRect = new Rectangle(0, 0, 0, 0);
	}
	public void draw(Graphics g){
		if(isRightDown){
			System.out.println("hello");
			g.setColor(Color.red);
			int w, h, x, y;
			if(mouseClickX < mouseDragX){
				w = mouseDragX - mouseClickX;
				x = mouseClickX;
			}
			else{
				w = mouseClickX - mouseDragX;
				x = mouseDragX;
			}
			if(mouseClickY < mouseDragY){
				h = mouseDragY - mouseClickY;
				y = mouseClickY;
			}
			else{
				h = mouseClickY - mouseDragY;
				y = mouseDragY;
			}
			currRect = new Rectangle(x, y, w, h);
			g.drawRect(x, y, w, h);
		}else
			currRect = new Rectangle(0,0,0,0);
	}
	
	public void mouseClick(MouseEvent e){
		
		if(e.getButton() == MouseEvent.BUTTON3){
			isRightDown = true;
			System.out.println("mouse click");
			mouseClickX = e.getX();
			mouseClickY = e.getY();
		}
	}
	
	public void mouseDrag(MouseEvent e){
		
		if(isRightDown){
			System.out.println("mouse drag");
			mouseDragX = e.getX();
			mouseDragY = e.getY();
		}
	}
	
	public void mouseRelease(MouseEvent e){
		System.out.println("mouse release");
		isLeftDown = false;
		isRightDown = false;
	}
	
	public boolean contains(Rectangle r){
		System.out.println(currRect.contains(r));
		return currRect.contains(r);
	}
	
	public Rectangle getRect(){
		return currRect;
	}
}
