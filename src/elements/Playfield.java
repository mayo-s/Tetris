package elements;

public class Playfield {
	
	private BasicValue bv;
	private int[][] matrix;
	private final int HEIGHT = bv.fieldHeight;
	private final int WIDTH = bv.fieldWidth;
	
	public Playfield() {
		bv = new BasicValue();
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
