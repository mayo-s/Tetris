package gui;

import elements.Playfield;
import elements.Tetromino;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Gui extends HBox {

	private HBox main;
	private Label playerLabel;
	private Label gameOverLabel;
	private GridPane gameGrid;
	private GridPane previewGrid;
	private HBox lvlBox;
	private HBox scoreBox;
	private HBox lineCountBox;
	private HBox tetroCountBox;

	public Gui() {

		main = new HBox();
		main.setPadding(new Insets(10, 10, 10, 10));

		// left side game grid
		StackPane sp = new StackPane();
		gameGrid = setupGridPane(22, 10);
		gameGrid.setGridLinesVisible(true);
		gameOverLabel = new Label("Game Over");
		gameOverLabel.setFont(new Font("Cambria", 42));
		gameOverLabel.setTextFill(Color.web("red"));
		gameOverLabel.setRotate(45d);
		gameOverLabel.setVisible(false);
		sp.getChildren().addAll(gameGrid, gameOverLabel);

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

		lvlBox = new HBox();
		Label lvlLabelText = new Label("Level: ");
		Label lvlLabel = new Label("1");
		lvlBox.getChildren().addAll(lvlLabelText, lvlLabel);

		tetroCountBox = new HBox();
		Label tcLabelText = new Label("Tetromino #");
		Label tcLabel = new Label("1");
		tetroCountBox.getChildren().addAll(tcLabelText, tcLabel);

		lineCountBox = new HBox();
		Label lcLabelText = new Label("Lines cleared: ");
		Label lcLabel = new Label("0");
		lineCountBox.getChildren().addAll(lcLabelText, lcLabel);

		Label controlsLabel = new Label("P - Play/Pause\n^ - Rotate\n< - move left\n> - move right\nv - drop");
		info.getChildren().addAll(previewGrid, playerLabel, lvlBox, scoreBox, tetroCountBox, lineCountBox,
				controlsLabel);

		main.getChildren().addAll(sp, info);
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

	public void updateGameGrid(Playfield field, Tetromino tetromino) {

		int fcolumns = field.getWIDTH();
		int frows = field.getHEIGHT();
		int coordR = tetromino.getRow();
		int coordC = tetromino.getColumn();
		int[][] tmatrix = tetromino.getMatrix();
		int tdimension = tmatrix.length;
		String tcolor = tetromino.getColor();

		// field
		for (int r = 0; r < frows; r++) {
			for (int c = 0; c < fcolumns; c++) {
				gameGrid.getChildren().get(r * fcolumns + c)
						.setStyle("-fx-background-color: " + field.getMatrix().get(r)[c]);
			}
		}

		// Tetromino in field
		for (int r = 0; r < tdimension; r++) {
			for (int c = 0; c < tdimension; c++) {
				if (tmatrix[r][c] == 1)
					gameGrid.getChildren().get((coordR + r) * fcolumns + (coordC + c))
							.setStyle("-fx-background-color: " + tcolor);
			}
		}
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

	public void updateLvl(int lvl) {
		Label label = new Label(Integer.toString(lvl));
		lvlBox.getChildren().remove(1);
		lvlBox.getChildren().add(label);
	}

	public void updateLineCount(int lines) {
		Label label = new Label(Integer.toString(lines));
		lineCountBox.getChildren().remove(1);
		lineCountBox.getChildren().add(label);
	}

	public void updateTetroCount(int tetros) {
		Label label = new Label(Integer.toString(tetros));
		tetroCountBox.getChildren().remove(1);
		tetroCountBox.getChildren().add(label);
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

	public Label getGameOverLabel() {
		return gameOverLabel;
	}
}
