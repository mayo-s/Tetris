package gui;

import elements.Playfield;
import elements.Tetromino;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Gui extends HBox {

	private HBox main;
	private Label playerLabel;
	private Label lvllabel;
	private GridPane gameGrid;
	private GridPane previewGrid;
	private HBox scoreBox;

	public Gui() {

		main = new HBox();
		main.setPadding(new Insets(10, 10, 10, 10));

		// left side game grid
		gameGrid = setupGridPane(22, 10);
		gameGrid.setGridLinesVisible(true);

		// right side info box
		VBox info = new VBox(10);
		info.setPadding(new Insets(10, 10, 10, 10));
		previewGrid = setupGridPane(4, 4);
		previewGrid.setGridLinesVisible(true);
		playerLabel = new Label("Player: " + "DummyName");
		
		scoreBox = new HBox();
		Label scoreLabelText = new Label("Score: ");
		Label scoreLabel = new Label("0");
		scoreBox.getChildren().addAll(scoreLabelText, scoreLabel);
		
		
		
		lvllabel = new Label("Level: " + Integer.toString(1));
		Label controlsLabel = new Label("P - Play/Pause\n^ - Rotate\n< - move left\n> - move right\nv - drop");
		info.getChildren().addAll(previewGrid, playerLabel, lvllabel, scoreBox, controlsLabel);

		main.getChildren().addAll(gameGrid, info);
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
					previewGrid.getChildren().get(r * columns + c).setStyle("-fx-background-color: " + tetromino.getColor());
				else
					previewGrid.getChildren().get(r * columns + c).setStyle("-fx-background-color: " + "#ffffff");
			}
		}
	}
	
	public void updateScore(int score) {
		Label label = new Label(Integer.toString(score));	
		scoreBox.getChildren().remove(1);	
		scoreBox.getChildren().add(label);
		
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
				else if (matrix[r][c] == 0)
					gameGrid.getChildren().get(r * columns + c)
							.setStyle("-fx-background-color: " + "#ffffff");
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

	public HBox getMain() {
		return main;
	}

	public void setMain(HBox main) {
		this.main = main;
	}
}
