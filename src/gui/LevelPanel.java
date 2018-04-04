package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelPanel extends JPanel {

	public LevelPanel() {
		Dimension size = getPreferredSize();
		size.width = 198;
		size.height = 10;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Level"));

		JLabel levelLabel = new JLabel("1");

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;

		add(levelLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
	}
}
