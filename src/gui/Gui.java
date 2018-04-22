package gui;

import elements.BasicValue;
import elements.Playfield;
import elements.Tetromino;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Gui {

	BasicValue bv;
	Scene scene;
	Label playerLabel;
	Label scoreLabel;
	Label lvllabel;
	GridPane gameGrid;
	GridPane previewGrid;

	public Gui() {
		bv = new BasicValue();
		playerLabel = new Label("Player: " + bv.getPlayer());
		scoreLabel = new Label("Score: " + Integer.toString(bv.getScore()));
		lvllabel = new Label("Level: " + Integer.toString(bv.getLvl()));
		Label controlsLabel = new Label(bv.getControls());

		HBox main = new HBox();
		main.setPadding(new Insets(10, 10, 10, 10));
		gameGrid = setupGridPane(bv.getFieldHeight(), bv.getFieldWidth());
		VBox info = new VBox(10);

		// left side game grid
		gameGrid.setGridLinesVisible(true);

		// right side info box
		info.setPadding(new Insets(10, 10, 10, 10));
		previewGrid = setupGridPane(bv.getTetrominoHeight(), bv.getTetrominoWidth());
		previewGrid.setGridLinesVisible(true);

		info.getChildren().addAll(previewGrid, playerLabel, lvllabel, scoreLabel, controlsLabel);

		main.getChildren().addAll(gameGrid, info);
		scene = new Scene(main);
		// scene.getStylesheets().add("style.css");
	}

	private GridPane setupGridPane(int rows, int columns) {
		GridPane newGridPane = new GridPane();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Pane pane = new Pane();
				pane.setPrefSize(20, 20);
				newGridPane.add(pane, c, r, 1, 1);
			}
		}
		return newGridPane;
	}

	public void updatePreviewGrid(Tetromino tetromino) {
		int[][] matrix = tetromino.getMatrix();
		int rows = matrix.length;
		int columns = matrix[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (matrix[r][c] == 1)
					previewGrid.getChildren().get(r * columns + c)
							.setStyle("-fx-background-color: " + tetromino.getColor());
			}
		}
	}

	public void updateGameGrid(Playfield field, Tetromino tetromino) {
		int[][] matrix = field.getMatrix();
		int rows = matrix.length;
		int columns = matrix[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (matrix[r][c] == 1)
					gameGrid.getChildren().get(r * columns + c)
							.setStyle("-fx-background-color: " + tetromino.getColor());
			}
		}
	}

	public GridPane getGameGrid() {
		return gameGrid;
	}

	public void setGameGrid(GridPane gameGrid) {
		this.gameGrid = gameGrid;
	}

	public GridPane getPreviewGrid() {
		return previewGrid;
	}

	public void setPreviewGrid(GridPane previewGrid) {
		this.previewGrid = previewGrid;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

}
