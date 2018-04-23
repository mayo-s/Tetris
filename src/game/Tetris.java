package game;

import gui.Gui;
import javafx.application.Application;
import javafx.scene.Scene;
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
		Scene scene = new Scene(gui);
		stage.setScene(scene);
		stage.show();
		gui.updatePreviewGrid(game.getNextTetrominos().element());
		gui.updateGameGrid(game.getField(), game.getNextTetrominos().element());
		while(!game.gameOver()) {
			game.moveDown();
		}
		test.printMatrix(game.getField().getMatrix());
	}

}
