package elements;

public interface Tetromino {
	int[][] getMatrix();
	void setMatrix(int[][] matrix);
	String getColor();
	int getRow();
	void setRow(int row);
	int getColumn();
	void setColumn(int column);
}
