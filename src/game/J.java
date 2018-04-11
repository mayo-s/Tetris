package game;

public class J implements Tetromino{

	int[][] tetromino;
	
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
}
