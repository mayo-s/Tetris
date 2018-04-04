package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {

	public PlayerPanel(String username) {
		Dimension size = getPreferredSize();
		size.width = 198;
		size.height = 10;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Player"));

		JLabel nameLabel = new JLabel(username);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;

		add(nameLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
	}
}
