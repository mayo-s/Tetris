package elements;

import java.util.ArrayList;

public class Playfield {

	private ArrayList<ArrayList<Boolean>> matrix;
	private final int HEIGHT = 22;
	private final int WIDTH = 10;
	
	public Playfield() {
		matrix = new ArrayList<>();
		fill();
	}
	
	private void fill() {
		for(int r = 0; r < HEIGHT; r++) {
			matrix.add(addRow());
		}
	}
	
	public ArrayList<Boolean> addRow() {	
		ArrayList<Boolean> row = new ArrayList<>();
		for(int c = 0; c < WIDTH; c++) {
			row.add(false);
		}	
		return row;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public ArrayList<ArrayList<Boolean>> getMatrix() {
		return matrix;
	}

	public void setMatrix(ArrayList<ArrayList<Boolean>> matrix) {
		this.matrix = matrix;
	}	
}
