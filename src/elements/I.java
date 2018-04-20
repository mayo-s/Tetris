package elements;

public class I implements Tetromino{
	
	int[][] tetromino;
	String color = "#85C1E9";
	
	public I(){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
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
