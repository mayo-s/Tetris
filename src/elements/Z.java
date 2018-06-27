package elements;

public class Z implements Tetromino {

	int[][] tetromino;
	String color = "red";
	int row;
	int column;
	
	public Z(int row, int column){
		tetromino = new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 1, 1},
            {0, 0, 0, 0}};

            this.row = row;
            this.column = column;
	}	

	public int[][] getMatrix() {
		return tetromino;
	}

	public void setMatrix(int[][] matrix) {
		tetromino = matrix;		
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;		
	}	

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}