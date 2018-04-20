package elements;

public class T implements Tetromino{

	int[][] tetromino;
	String color = "#900C3F";
	
	public T(){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 1},
            {0, 0, 1, 0},
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
