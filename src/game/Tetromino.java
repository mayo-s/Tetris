package game;

public class Tetromino {

	int[][] matrix;
	String color;
	int row;
	int column;

	/**
	 * @param shape
	 *            Available values I, J, L, O, S, T, Z
	 *            - any other value will result in an empty 4x4 int[][]
	 */
	public Tetromino(String shape) {

		if (shape == null) shape = "";
		row = 0;
		column = 3;

		switch (shape) {
		case "I":
			matrix = new int[][] {
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 } };
			color = "cyan";
			break;
		case "J":
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 1, 1, 0 } };
			color = "blue";
			break;
		case "L":
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 0, 0 }, 
				{ 0, 1, 0, 0 }, 
				{ 0, 1, 1, 0 } };
			color = "orange";
			break;
		case "O":
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "yellow";
			break;
		case "S":
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 1, 1 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "green";
			break;
		case "T":
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 1 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "purple";
			break;
		case "Z":
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 1, 1 }, 
				{ 0, 0, 0, 0 } };
			color = "red";
			break;
		default:
			matrix = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "white";
			break;
		}
	}
	
	public int[][] rotate() {
		int size = matrix.length;
		int[][] rotated = new int[size][size];

		for (int r = 0; r < size; ++r) {
			for (int c = 0; c < size; ++c) {
				rotated[r][c] = matrix[size - c - 1][r];
			}
		}
		return rotated;
	}
	
	public int[][] copy() {
		int size = matrix.length;
		int[][] newTetro = new int[size][size];

		for (int r = 0; r < size; ++r) {
			for (int c = 0; c < size; ++c) {
				newTetro[r][c] = matrix[r][c];
			}
		}
		return newTetro;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
