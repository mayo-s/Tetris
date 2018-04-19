package game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Tetris extends Application{
	
	Game game;
	
	public static void main(String[] args) {

		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		game = new Game(stage);		
		
	}
}
