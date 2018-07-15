package game;

import java.util.ArrayList;

public class Playfield {

	private ArrayList<String[]> matrix;
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
	
	public String[] addRow() {	
		String[] row = new String[WIDTH];
		return row;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public ArrayList<String[]> getMatrix() {
		return matrix;
	}

	public void setMatrix(ArrayList<String[]> matrix) {
		this.matrix = matrix;
	}
}
