package game;

import ai.AI;
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
	private boolean paused;
	private boolean aiOn;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris by Mario Schuetz");
		game = new Game();
		gui = new Gui();
		paused = false;
		aiOn = false;
		Scene scene = new Scene(gui.getMain());
		stage.setScene(scene);
		stage.show();
		intervalEvent();
		userEvent(scene);
		if(aiOn) {
			System.out.println("AI ON");
			ai();		
		}
		else
			System.out.println("AI OFF");
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

					if(aiOn && !game.isGameOver() && game.newTetro()) ai();
					game.move("down");
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
					if (!paused && !aiOn) {

						if (event.getCode() == KeyCode.LEFT) {
							game.move("left");
						}
						if (event.getCode() == KeyCode.RIGHT) {
							game.move("right");
						}
						if (event.getCode() == KeyCode.DOWN) {
							game.move("down");
						}
						if (event.getCode() == KeyCode.UP) {
							game.move("rotate");
						}
						if (event.getCode() == KeyCode.P) {
							System.out.println("PAUSED");
							paused = true;
							interval.pause();
						}
						if (event.getCode() == KeyCode.A) {
							System.out.println("AI ON");
							aiOn = true;
							ai();
						}

					} else if(!paused && aiOn) {
						if (event.getCode() == KeyCode.P) {
							System.out.println("PAUSED");
							paused = true;
							interval.pause();
						}
						if (event.getCode() == KeyCode.A) {
							System.out.println("AI OFF");
							aiOn = false;
						}
					} else if (paused) {
						if (event.getCode() == KeyCode.P) {
							System.out.println("PLAY");
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
		
		AI ai = new AI();
		int[] aiCommands = ai.start(game.getField(), game.getNextTetrominos());
//		 System.out.println(
//				"Returned values: rotation x" + aiCommands[0] + " row " + aiCommands[1] + " column " + aiCommands[2]);
		Tetromino tetro = game.getNextTetrominos().get(0);
		int rotation = aiCommands[0];
//		int fRow = aiCommands[1];
		int fColumn = aiCommands[2];
		for (int i = 1; i <= rotation; i++) {
			game.move("rotate");
//			gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
		}
		
		if (fColumn < tetro.getColumn()) {
			for (int i = tetro.getColumn(); i > fColumn; i--) {
				game.move("left");
//				gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
			}
		} else if (fColumn > tetro.getColumn()) {
			for (int i = tetro.getColumn(); i < fColumn; i++) {
				game.move("right");
//				gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
			}
		}
//		for (int i = tetro.getRow(); i < fRow; i++) {
//			game.move("down");
////			gui.updateGameGrid(game.getField(), game.getNextTetrominos().get(0));
//		}
		while(game.move("down")) {
			
		}
	}
}
