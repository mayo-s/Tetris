package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {

	public ScorePanel() {
		Dimension size = getPreferredSize();
		size.width = 198;
		size.height = 10;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Score"));

		JLabel scoreLabel = new JLabel("0");

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;

		add(scoreLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
	}
}
