package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	public ControlPanel() {
		Dimension size = getPreferredSize();
		size.width = 198;
		size.height = 200;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Controls"));

		JLabel playLabel = new JLabel("P - Play/Pause game");
		JLabel leftLabel = new JLabel("< - Move left");
		JLabel rightLabel = new JLabel("> - Move right");
		JLabel upLabel = new JLabel("^ - Rotate 90°");
		JLabel downLabel = new JLabel(" - Down");

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(playLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(leftLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(rightLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(upLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(downLabel, gbc);
	}
}
