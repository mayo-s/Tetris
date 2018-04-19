package game;

import gui.Gui;
import javafx.stage.Stage;

public class Game {

	Logic logic;
	Gui gui;
	private String player;
	private String controls;
	private int score;
	private int lvl;

	public Game(Stage stage) {
		
		logic = new Logic();
		gui = new Gui(stage);
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public String getControls() {
		return controls;
	}
}
