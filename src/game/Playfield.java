package game;

public class Playfield {

	private int[][] matrix;
	private final int HEIGHT = 22;
	private final int WIDTH = 10;
	
	public Playfield() {
		matrix = new int[HEIGHT][WIDTH];
		fill();
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

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}	
}
