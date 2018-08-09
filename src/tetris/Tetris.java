package tetris;

import ai.AI;
import game.Game;
import game.Move;
import game.Playfield;
import game.Tetromino;
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

	private static Game game;
	private static Gui gui;
	private static Move move;
	private static AI ai;
	private Timeline interval;
	private static boolean paused;
	private static boolean aiOn;

	public static void main(String[] args) {
		game = new Game();
		gui = new Gui();
		move = new Move(game);
		paused = false;
		aiOn = false;
		ai = new AI(move);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris by Mario Schuetz");
		Scene scene = new Scene(gui.getMain());
		stage.setScene(scene);
		stage.show();
		intervalEvent();
		userEvent(scene);

		System.out.println("AI " + aiOn);
		if (aiOn)
			ai();

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

					if (aiOn && !game.isGameOver() && game.newTetro())
						ai();

					Tetromino tetro = game.getNextTetrominos().get(0);
					Playfield field = game.getField();
					move.down(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
					gui.updatePreviewGrid(game.getNextTetrominos().get(1));
					gui.updateScore(game.getScore());
					gui.updateGameGrid(field, tetro);
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
					KeyCode keyPressed = event.getCode();
					if (keyPressed == KeyCode.P) {
						paused = !paused;
						System.out.println("PAUSED " + paused);
						if (paused)
							interval.pause();
						else
							interval.play();
					}

					if (!paused) {
						if (keyPressed == KeyCode.A) {
							aiOn = !aiOn;
							System.out.println("AI " + aiOn);
							ai();
						}
						if (!aiOn) {
							Tetromino tetro = game.getNextTetrominos().get(0);
							Playfield field = game.getField();

							if (keyPressed == KeyCode.LEFT) {
								move.left(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
							} else if (keyPressed == KeyCode.RIGHT) {
								move.right(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
							} else if (keyPressed == KeyCode.DOWN) {
								move.down(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
							} else if (keyPressed == KeyCode.UP) {
								move.rotate(field.getMatrix(), tetro, tetro.getRow(), tetro.getColumn(), true);
							}
							gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
						}
					}
				}
			}
		});
	}

	private void ai() {

		int[] aiCommands = ai.start(game.getField(), game.getNextTetrominos());
		// System.out.println("Returned values: rotation x" + aiCommands[0] + " row " +
		// aiCommands[1] + " column " + aiCommands[2]);
		Playfield field = game.getField();
		Tetromino tetro = game.getNextTetrominos().get(0);

		int rotation = aiCommands[0];
		// int fRow = aiCommands[1];
		int fColumn = aiCommands[2];
		for (int i = 1; i <= rotation; i++) {
			move.rotate(field.getMatrix(), tetro, tetro.getRow(), tetro.getColumn(), true);
			// gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
		}

		if (fColumn < tetro.getColumn()) {
			for (int i = tetro.getColumn(); i > fColumn; i--) {
				move.left(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
				// gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
			}
		} else if (fColumn > tetro.getColumn()) {
			for (int i = tetro.getColumn(); i < fColumn; i++) {
				move.right(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
				// gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
			}
		}
		// for (int i = tetro.getRow(); i < fRow; i++) {
		// game.move("down");
		//// gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
		// }
		while (move.down(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true)) {

		}
	}
}
