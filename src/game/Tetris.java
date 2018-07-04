package game;

import ai.AI;
import elements.Tetromino;
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
	private AI ai;
	private Timeline interval;
	private boolean paused;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris by Mario Schuetz");
		game = new Game();
		gui = new Gui();
		ai = new AI();
		paused = false;
		Scene scene = new Scene(gui.getMain());
		stage.setScene(scene);
		stage.show();
		intervalEvent();
		userEvent(scene);
	}

	private void intervalEvent() {
		double time = Math.pow((0.8 - ((game.getLvl() - 1) * 0.007)), (game.getLvl() - 1)) * 1000;

		gui.updatePreviewGrid(game.getNextTetrominos().get(1));
		interval = new Timeline(new KeyFrame(Duration.millis(time), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (game.isGameOver()) {
					gui.getGameOverLabel().setVisible(true);
					interval.stop();
				} else {

					game.moveDown();
					gui.updatePreviewGrid(game.getNextTetrominos().get(1));
					gui.updateScore(game.getScore());
					gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
					gui.updateLineCount(game.getLcount());
					gui.updateTetroCount(game.getTcount());
					if (game.levelUp()) {
						gui.updateLvl(game.getLvl());
						interval.stop();
						intervalEvent();
					}
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
					if (!paused) {

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
						if (event.getCode() == KeyCode.P) {
							paused = true;
							interval.pause();
						}
					} else if (paused) {
						if (event.getCode() == KeyCode.P) {
							paused = false;
							interval.play();
						}
					}

					gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
				}
			}
		});
	}

	private void ai() {
		int[] aiCommands = ai.evaluate(game.getField(), game.getNextTetrominos());
		Tetromino tetro = game.getNextTetrominos().get(0);
		int fRow = aiCommands[1];
		int fColumn = aiCommands[2];
		for (int i = 0; i < aiCommands[0]; i++) {
			game.moveRotate();
		}
		// move to column
		if (fColumn > tetro.getColumn()) {
		} else if (fColumn < tetro.getColumn()) {

		}
		game.moveDown();
		gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
		
	}

}
