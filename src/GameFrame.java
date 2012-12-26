import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GameFrame extends JFrame implements KeyListener{
	public GameFrame(){
		super("w00t");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.add(new GamePanel());
		//TODO make this work, keyboard input on both the frame and the panel, or alteratively-->close it from the panel
//		this.addKeyListener(this);
//		this.setFocusable(true);
		this.setVisible(true);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			this.dispose();
		System.out.println("HERRRRP");
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
