package elements;

public class J implements Tetromino{

	int[][] tetromino;
	String color = "#DAF7A6";
	
	public J(){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 1, 1, 0}};
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
