package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {

	private GamePanel gamePanel;
	private JPanel infoPanel;

	public Gui(String title) {
		super(title);

		// Layout Manager
		setLayout(new BorderLayout());

		// Components
		gamePanel = new GamePanel();
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(new PreviewPanel());
		infoPanel.add(new PlayerPanel("Mario"));
		infoPanel.add(new LevelPanel());
		infoPanel.add(new ScorePanel());
		infoPanel.add(new ControlPanel());

		// Content Pane
		Container container = getContentPane();
		container.add(gamePanel, BorderLayout.WEST);
		container.add(infoPanel, BorderLayout.EAST);
	}

}
