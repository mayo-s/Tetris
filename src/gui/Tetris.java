package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import beans.Game;

public class Tetris {

	public static Game game;

	public static void main(String[] args) {

		// Swing thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame gui = new JFrame("Tetris by Mario Schuetz");
				gui.setSize(400, 640);
				gui.setVisible(true);
				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		game = new Game();
	}

}
