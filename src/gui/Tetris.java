package gui;

import game.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Tetris extends Application{

	public static Game game;
	public static Gui gui;
	
	public static void main(String[] args) {
		
		game = new Game();
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		gui = new Gui(stage, game);		
		
	}

}
