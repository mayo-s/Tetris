package game;

import gui.Gui;
import javafx.application.Application;
import javafx.stage.Stage;
import test.Test;

public class Tetris extends Application {

	Test test = new Test();
	
	public Game game;
	public Gui gui;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris by Mario Schuetz");
		game = new Game();

		gui = new Gui();
		stage.setScene(gui.getScene());
		stage.show();
		gui.updatePreviewGrid(game.getNextTetrominos().element());
		gui.updateGameGrid(game.getField(), game.getNextTetrominos().element());	
	}

}
