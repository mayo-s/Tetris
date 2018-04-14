package game;

import java.util.ArrayList;
import java.util.List;

public class Movement {

	Playfield field;

	public Movement() {
		field = new Playfield();
	}

	private Tetromino rotate(Tetromino tetromino) {
		int size = tetromino.getMatrix().length;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tetromino.getMatrix()[size - j - 1][i];
			}
		}

		tetromino.setMatrix(rotated);

		return tetromino;
	}

	public List<Object> down(int[][] newMatrix, int row, int column) {

		if (!collisionDown(row + 3)) {

			for (int r = 3; r >= 0; r--) {
				for (int c = 0; c < 4; c++) {
					newMatrix[row + r + 1][column + c] = newMatrix[row + r][column + c];
					newMatrix[row + r][column + c] = 0;
				}
			}
			row++;
		}

		List<Object> result = new ArrayList<Object>();
		result.add(newMatrix);
		result.add(row);
		result.add(column);

		return result;
	}

	private List<Object> left(int[][] newMatrix, int row, int column) {

		if (!collisionLeft(column)) {

			for (int c = 0; c < 4; c++) {
				for (int r = 0; r < 4; r++) {
					newMatrix[row + r][column + c - 1] = newMatrix[row + r][column + c];
					newMatrix[row + r][column + c] = 0;
				}
			}
			column--;
		}

		List<Object> result = new ArrayList<Object>();
		result.add(newMatrix);
		result.add(row);
		result.add(column);

		return result;
	}

	private List<Object> right(int[][] newMatrix, int row, int column) {

		if (!collisionRight(column + 3)) {

			for (int c = 3; c >= 0; c--) {
				for (int r = 0; r < 4; r++) {
					newMatrix[row + r][column + c + 1] = newMatrix[row + r][column + c];
					newMatrix[row + r][column + c] = 0;
				}
			}
			column++;
		}

		List<Object> result = new ArrayList<Object>();
		result.add(newMatrix);
		result.add(row);
		result.add(column);

		return result;
	}

	private boolean collisionDown(int row) {
		boolean collision = false;

		if (row >= field.getHEIGHT() - 1)
			return true;

		return collision;
	}

	private boolean collisionLeft(int column) {
		boolean collision = false;
		if (column <= 0)
			return true;

		return collision;
	}

	private boolean collisionRight(int column) {
		boolean collision = false;
		if (column >= field.getWIDTH() - 1)
			return true;

		return collision;
	}
}
