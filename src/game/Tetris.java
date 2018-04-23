package game;

import gui.Gui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
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
		Scene scene = new Scene(gui.getMain());
		stage.setScene(scene);
		stage.show();
		playGame();
	}
	
	public void playGame() {
		
		Timeline interval = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent event) {
		        System.out.println("this is called every second on UI thread");
		        game.moveDown();
		        gui.updatePreviewGrid(game.getNextTetrominos().element());
				gui.updateGameGrid(game.getField(), game.getNextTetrominos().element());
		        test.printMatrix(game.getField().getMatrix());
		    }
		}));
		interval.setCycleCount(Timeline.INDEFINITE);
		interval.play();

	}

}
