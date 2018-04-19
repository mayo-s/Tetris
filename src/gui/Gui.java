package gui;

import game.Game;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui{
	
	Game game;
	Label playerLabel;
	Label scoreLabel;
	Label lvllabel;

	public Gui(Stage stage, Game game) {
		stage.setTitle("Tetris by Mario Schuetz");
		this.game = game;
		playerLabel = new Label(game.getPlayer());
		scoreLabel = new Label(Integer.toString(game.getScore()));
		lvllabel = new Label(Integer.toString(game.getLvl()));
		String controls = "P - Play/Pause\n^ - Rotate\n< - move left\n> - move right\nv - drop";
		Label controlsLabel = new Label(controls);
		
		HBox main = new HBox();
		main.setPadding(new Insets(10,10, 10, 10));
		GridPane gameGrid = setupGridPane(game.getField().getHEIGHT(), game.getField().getWIDTH());
		VBox info = new VBox(10);
		
		// left side game grid		
		gameGrid.setGridLinesVisible(true);
		
		// right side info box
		info.setPadding(new Insets(10, 10, 10, 10));
		GridPane previewGrid = setupGridPane(4, 4);
		previewGrid.setGridLinesVisible(true);
		
		
		
		info.getChildren().addAll(previewGrid, playerLabel, lvllabel, scoreLabel, controlsLabel);
		
		main.getChildren().addAll(gameGrid, info);
		Scene scene = new Scene(main);
//		scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		stage.show();
	}

	
	private GridPane setupGridPane(int row, int column) {
		GridPane newGridPane = new GridPane();
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < column; c++) {
				Pane pane = new Pane();
				pane.setPrefSize(20, 20);
				newGridPane.add(pane, c, r, 1, 1);			
			}
		}
		return newGridPane;
	}

	
}
