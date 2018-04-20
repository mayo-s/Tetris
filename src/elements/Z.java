package elements;

public class Z implements Tetromino {

	int[][] tetromino;
	String color = "#92F028";
	
	public Z(){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 1, 1},
            {0, 0, 0, 0}};
	}	
	
	@Override
	public int[][] getMatrix() {
		return tetromino;
	}

	@Override
	public void setMatrix(int[][] matrix) {
		tetromino = matrix;		
	}

	@Override
	public String getColor() {
		return color;
	}
}
