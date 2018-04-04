package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public GamePanel() {
		Dimension size = getPreferredSize();
		size.width = 400;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder(""));
	}
	
}
