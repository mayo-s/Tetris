package game;

public class Playfield {

	private int[][] matrix;
	private int[] currTetrominoCoords;
	private final int WIDTH = 10;
	private final int HEIGHT = 22;
	
	public Playfield() {
		matrix = new int[HEIGHT][WIDTH];
		fill();
		currTetrominoCoords = new int[2];
	}
	
	private void fill() {
		for(int[] array : matrix) {
			java.util.Arrays.fill(array, 0);
		}
	}
	
	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;		
	}

	public int[] getCurrTetrominoCoords() {
		return currTetrominoCoords;
	}

	public void setCurrTetrominoCoords(int[] currTetrominoCoords) {
		this.currTetrominoCoords = currTetrominoCoords;
	}	
}
