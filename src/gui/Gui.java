package gui;

import elements.BasicValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui {

	BasicValue bv;
	Label playerLabel;
	Label scoreLabel;
	Label lvllabel;
	GridPane gameGrid;
	GridPane previewGrid;

	public Gui(Stage stage) {
		stage.setTitle("Tetris by Mario Schuetz");
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
		Scene scene = new Scene(main);
		// scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		stage.show();
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

	public void updateGrid(int[][] matrix, GridPane gridPane, String color) {
		int rows = matrix.length;
		int columns = matrix[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (matrix[r][c] == 1)
					gridPane.getChildren().get(r * rows + c).setStyle("-fx-background-color: " + color);
			}
		}

	// ObservableList<Node> nodes = gridPane.getChildren();
	//
	// for (Node node : nodes) {
	// if (matrix[gridPane.getRowIndex(node)][gridPane.getColumnIndex(node)] == 1) {
	//
	// node.setStyle("-fx-background-color: #f4f4f4");
	//// Pane pane = (Pane) node;
	//// pane.backgroundProperty("");
	// }
	// }
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

}
