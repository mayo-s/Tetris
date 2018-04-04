package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PreviewPanel extends JPanel {

	public PreviewPanel() {
		Dimension size = getPreferredSize();
		size.width = 198;
		size.height = 198;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Next up"));

//		setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.weightx = 0.5;
//		gbc.weighty = 0.5;
//		gbc.gridx = 0;
//		gbc.gridy = 0;

	}
}
