package elements;

public class Tetromino {

	int[][] tetromino;
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
			tetromino = new int[][] {
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 } };
			color = "cyan";
			break;
		case "J":
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 1, 1, 0 } };
			color = "blue";
			break;
		case "L":
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 0, 0 }, 
				{ 0, 1, 0, 0 }, 
				{ 0, 1, 1, 0 } };
			color = "orange";
			break;
		case "O":
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "yellow";
			break;
		case "S":
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 1, 1 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "green";
			break;
		case "T":
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 1 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "purple";
			break;
		case "Z":
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 1, 1, 0 }, 
				{ 0, 0, 1, 1 }, 
				{ 0, 0, 0, 0 } };
			color = "red";
			break;
		default:
			tetromino = new int[][] { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 } };
			color = "white";
			break;
		}
	}

	public int[][] getMatrix() {
		return tetromino;
	}

	public void setMatrix(int[][] matrix) {
		tetromino = matrix;
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
