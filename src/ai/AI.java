package ai;

import java.util.ArrayList;
import java.util.List;

import elements.Playfield;
import elements.Tetromino;

public class AI {

	private Playfield field;
	private ArrayList<String[]> fMatrix;
	private final int ROWS = field.getHEIGHT();
	private final int COLUMNS = field.getWIDTH();
	private List<Tetromino> tetrominos;

	/**
	 * @param field
	 *            current Playfield
	 * @param tetrominos
	 *            list of current and following Tetrominos
	 */
	public AI(Playfield field, List<Tetromino> tetrominos) {
		this.field = field;
		this.fMatrix = field.getMatrix();
		this.tetrominos = tetrominos;
		evaluate();
	}

	public Tetromino evaluate() {
		ArrayList<Tetromino> options = makeList();
		
		return null;
	}

	private ArrayList<Tetromino> makeList() {
		Tetromino t = tetrominos.get(0);
		int[][] tmatrix = t.getMatrix();
		ArrayList<Tetromino> allOptions = new ArrayList<>();
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				t.setRow(r);
				t.setColumn(c);
				// initial
				allOptions.add(t);
				// rotate 1
				t.setMatrix(rotate(tmatrix));
				allOptions.add(t);
				// rotate 2
				t.setMatrix(rotate(tmatrix));
				allOptions.add(t);
				// rotate 3
				t.setMatrix(rotate(tmatrix));
				allOptions.add(t);

			}
		}
		return reduceList(allOptions);
	}

	private ArrayList<Tetromino> reduceList(ArrayList<Tetromino> allOptions) {

		for (int i = allOptions.size() - 1; i >= 0; i--) {
			Tetromino t = allOptions.get(i);
			int[][] tmatrix = t.getMatrix();
			int row = t.getRow();
			int column = t.getColumn();
			for (int r = 0; r < 4; ++r) {
				if (row + r < ROWS)
					for (int c = 0; c < 4; ++c) {
						if (tmatrix[r][c] == 1 && (column + c >= COLUMNS || fMatrix.get(row + r)[column + c] != null))
							allOptions.remove(i);
					}
			}
		}
		return null;
	}

	private int[][] rotate(int[][] tetromino) {
		int size = tetromino.length;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tetromino[size - j - 1][i];
			}
		}
		return rotated;
	}

}