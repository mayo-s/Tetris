package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class Gui extends JFrame {
	
	private GamePanel gamePanel;
	private InformationPanel infoPanel;
	
	public Gui(String title) {
		super(title);
		
		// Layout Manager
		setLayout(new BorderLayout());
		
		// Components
		gamePanel = new GamePanel();
		infoPanel = new InformationPanel();
		
		// Content Pane
		Container container = getContentPane();
		container.add(gamePanel, BorderLayout.WEST);
		container.add(infoPanel, BorderLayout.EAST);
	}

}
