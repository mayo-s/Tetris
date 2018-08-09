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

	private static Game game = new Game();
	private static Gui gui = new Gui();
	private static Move move = new Move(game);
	private static AI ai = new AI(move);
	private static Timeline interval;
	private static boolean paused = false;
	private static boolean aiOn = false;
	private static boolean sloMo = true;

	public static void main(String[] args) {

		System.out.println("AI " + aiOn);
		System.out.println("SLO MO " + sloMo);
		launch(args);
		if (aiOn)
			ai();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris by Mario Schuetz");
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
						if (keyPressed == KeyCode.S) {
							sloMo = !sloMo;
							System.out.println("Slow Motion " + sloMo);
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
						}
						gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
					}
				}
			}
		});
	}

	private static void ai() {
		int[] aiCommands = ai.start(game.getField(), game.getNextTetrominos());
		autoMoveToPosition(aiCommands[0], aiCommands[1], aiCommands[2]);
	}

	private static void autoMoveToPosition(int rotation, int fRow, int fColumn) {
		Playfield field = game.getField();
		Tetromino tetro = game.getNextTetrominos().get(0);

		for (int i = 1; i <= rotation; i++) {
			move.rotate(field.getMatrix(), tetro, tetro.getRow(), tetro.getColumn(), true);
		}

		if (fColumn < tetro.getColumn()) {
			for (int i = tetro.getColumn(); i > fColumn; i--) {
				move.left(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
			}
		} else if (fColumn > tetro.getColumn()) {
			for (int i = tetro.getColumn(); i < fColumn; i++) {
				move.right(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
			}
		}
		
		for (int i = tetro.getRow(); i < fRow; i++) {
			move.down(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
		}
	}
}