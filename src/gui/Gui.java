package gui;

import java.util.ArrayList;
import java.util.List;

import game.Playfield;
import game.Tetromino;
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
	private GridPane gameGrid;
	// private GridPane previewGrid;
	private List<GridPane> previewList;
	private Label playerLabel;
	private Label gameOverLabel;
	private HBox lvlBox;
	private HBox scoreBox;
	private HBox lineCountBox;
	private HBox tetroCountBox;

	public Gui(int preview) {

		main = new HBox();
		main.setPadding(new Insets(10, 10, 10, 10));

		// left side game grid
		StackPane sp = new StackPane();
		gameGrid = setupGridPane(20, 10);
		gameGrid.setGridLinesVisible(true);
		gameOverLabel = new Label("Game Over");
		gameOverLabel.setFont(new Font("Cambria", 42));
		gameOverLabel.setTextFill(Color.web("red"));
		gameOverLabel.setRotate(45d);
		gameOverLabel.setVisible(false);
		sp.getChildren().addAll(gameGrid, gameOverLabel);

		// right side info box
		VBox infoBox = new VBox(10);
		infoBox.setPadding(new Insets(10, 10, 10, 10));

		if (preview > 1) {
			previewList = new ArrayList<GridPane>();
			for (int i = 1; i < preview; i++) {
				GridPane previewGrid;
				previewGrid = setupGridPane(4, 4);
				previewGrid.setGridLinesVisible(true);
				previewList.add(previewGrid);
				infoBox.getChildren().add(previewGrid);
			}
		}
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

		Label controlsLabel = new Label("\n^ - Rotate\n< - move left\n> - move right\nv - move down\n\nP - Play/Pause\nA - AI on/off");
		infoBox.getChildren().addAll(playerLabel, lvlBox, scoreBox, tetroCountBox, lineCountBox, controlsLabel);

		main.getChildren().addAll(sp, infoBox);
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
		int frows = field.getHEIGHT() - 2;
		int coordR = tetromino.getRow();
		int coordC = tetromino.getColumn();
		int[][] tmatrix = tetromino.getMatrix();
		int tdimension = tmatrix.length;
		String tcolor = tetromino.getColor();

		// field
		for (int r = 0; r < frows; r++) {
			for (int c = 0; c < fcolumns; c++) {
				gameGrid.getChildren().get(r * fcolumns + c).setStyle("-fx-background-color: " + field.getMatrix().get(r + 2)[c]);
			}
		}

		// Tetromino in field
		for (int r = 0; r < tdimension; r++) {
			if (coordR + r > 1) {
				for (int c = 0; c < tdimension; c++) {
					if (tmatrix[r][c] == 1)
						gameGrid.getChildren().get((coordR + r - 2) * fcolumns + (coordC + c)).setStyle("-fx-background-color: " + tcolor);
				}
			}
		}
	}

	public void updatePreviewGrid(List<Tetromino> tetrominos) {

		for (int i = 0; i < previewList.size(); i++) {
			GridPane previewGrid = previewList.get(i);
			Tetromino tetromino = tetrominos.get(i + 1);
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
