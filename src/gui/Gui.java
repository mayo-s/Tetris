package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class Gui extends JFrame {

	private InformationPanel infoPanel;
	
	public Gui(String title) {
		super(title);

		// Layout Manager
		setLayout(new BorderLayout());
		
		// Components
		infoPanel = new InformationPanel();
		
		// Content Pane
		Container container = getContentPane();
		container.add(infoPanel, BorderLayout.EAST);
	}

}
