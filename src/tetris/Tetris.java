package tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tetris extends Application {

	private static Game game;
	private static Gui gui;
	private static Move move;
	private static AI ai;
	private static Timeline interval;
	private static boolean paused = false;
	private static boolean aiOn = false;
	private static boolean sloMo = false;

	public static void main(String[] args) {

		launch(args);
		System.out.println("AI " + aiOn);
		if (sloMo)
			System.out.println("SLO MO " + sloMo);
		if (aiOn)
			ai();
	}

	private static void gameSetup() {

		List<Integer> choices = new ArrayList<>();
		choices.add(0);
		choices.add(1);
		choices.add(2);
		choices.add(3);

		ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, choices);
		dialog.setTitle("Game Setup");
		dialog.setHeaderText("Set number of Tetrominos in preview");
		dialog.setContentText("Choose a number");

		Optional<Integer> previewNumber = dialog.showAndWait();
		previewNumber.ifPresent(number -> game = new Game(number));
		gui = new Gui(game.getNextTetrominos().size());
		move = new Move(game);
		ai = new AI(move);

	}

	@Override
	public void start(Stage stage) throws Exception {

		gameSetup();

		stage.setTitle("Tetris by Mario Schuetz");
		Scene scene = new Scene(gui.getMain());
		stage.setScene(scene);
		stage.show();
		intervalEvent();
		userEvent(scene);
	}

	private void intervalEvent() {
		int lvl = game.getLvl();
		double time = Math.pow((0.8 - ((lvl - 1) * 0.007)), (lvl - 1)) * 1000;

		if (game.getNextTetrominos().size() > 1)
			gui.updatePreviewGrid(game.getNextTetrominos());
		interval = new Timeline(new KeyFrame(Duration.millis(time), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (game.isGameOver()) {
					gui.getGameOverLabel().setVisible(true);
					interval.stop();
				} else {

					Tetromino tetro = game.getNextTetrominos().get(0);
					Playfield field = game.getField();
					move.down(field.getMatrix(), tetro.getMatrix(), tetro.getRow(), tetro.getColumn(), true);
					gui.updateGameGrid(field, tetro);
					if (aiOn && game.newTetro())
						ai();
					if (game.getNextTetrominos().size() > 1)
						gui.updatePreviewGrid(game.getNextTetrominos());
					gui.updateScore(game.getScore());
					gui.updateLineCount(game.getLcount());
					gui.updateTetroCount(game.getTcount());
					if (game.levelUp()) {
						gui.updateLvl(lvl + 1);
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
							System.out.println("SLO MO " + sloMo);
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
		// double startTime = System.currentTimeMillis();
		int[] aiCommands = ai.start(game.getField(), game.getNextTetrominos());
		// System.out.println("Evaluation Time: " + (System.currentTimeMillis() -
		// startTime) + "ms");
		gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
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