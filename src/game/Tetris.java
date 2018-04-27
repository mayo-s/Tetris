package game;

import gui.Gui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tetris extends Application {

	private Game game;
	private Gui gui;
	private Timeline interval;

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
		intervalEvent();
		userEvent(scene);
	}

	private void intervalEvent() {
		interval = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (game.isGameOver()) {
					gui.getGameOverLabel().setVisible(true);
					interval.stop();
				} else {
					if (game.newScore())
						gui.updateScore(game.getScore());
					game.moveDown();
					gui.updatePreviewGrid(game.getNextTetrominos().get(1));
					gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
				}
			}
		}));
		interval.setCycleCount(Timeline.INDEFINITE);
		interval.play();
	}

	private void userEvent(Scene scene) {

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (!game.isGameOver()) {
					if (event.getCode() == KeyCode.LEFT) {
						game.moveLeft();
					}
					if (event.getCode() == KeyCode.RIGHT) {
						game.moveRight();
					}
					if (event.getCode() == KeyCode.DOWN) {
						game.moveDown();
					}
					if (event.getCode() == KeyCode.UP) {
						game.moveRotate();
					}
					gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
				}
			}
		});
	}
}
