package gui;

import elements.BasicValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui{

	BasicValue bv;
	Label playerLabel;
	Label scoreLabel;
	Label lvllabel;

	public Gui(Stage stage) {
		stage.setTitle("Tetris by Mario Schuetz");
		bv = new BasicValue();
		playerLabel = new Label(bv.getPlayer());
		scoreLabel = new Label(Integer.toString(bv.getScore()));
		lvllabel = new Label(Integer.toString(bv.getLvl()));
		Label controlsLabel = new Label(bv.getControls());

		HBox main = new HBox();
		main.setPadding(new Insets(10, 10, 10, 10));
		GridPane gameGrid = setupGridPane(bv.getFieldHeight(), bv.getFieldWidth());
		VBox info = new VBox(10);

		// left side game grid
		gameGrid.setGridLinesVisible(true);

		// right side info box
		info.setPadding(new Insets(10, 10, 10, 10));
		GridPane previewGrid = setupGridPane(bv.getTetrominoHeight(), bv.getTetrominoWidth());
		previewGrid.setGridLinesVisible(true);

		info.getChildren().addAll(previewGrid, playerLabel, lvllabel, scoreLabel, controlsLabel);

		main.getChildren().addAll(gameGrid, info);
		Scene scene = new Scene(main);
		// scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		stage.show();
	}

	private GridPane setupGridPane(int row, int column) {
		GridPane newGridPane = new GridPane();
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < column; c++) {
				Pane pane = new Pane();
				pane.setPrefSize(20, 20);
				newGridPane.add(pane, c, r, 1, 1);
			}
		}
		return newGridPane;
	}

}
