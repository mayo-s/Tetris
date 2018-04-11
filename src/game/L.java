package game;

public class L implements Tetromino {

	private int[][] tetromino;
	
	public L(){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
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
