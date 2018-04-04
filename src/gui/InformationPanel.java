package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel {

	public InformationPanel() {
		Dimension size = getPreferredSize();
		size.width = 200;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Tetris"));
//		
//		JLabel nameLable = new JLabel("Player: ");
//		JLabel scoreLable = new JLabel("Score: ");
//		JLabel controlsLable = new JLabel("< move left");
//		
//		setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
	}
}
