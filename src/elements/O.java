package elements;

public class O  implements Tetromino{
	
	int[][] tetromino;
	String color = "#FF5733";
	
	public O(){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
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
