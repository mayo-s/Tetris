package ai;

import java.util.ArrayList;
import java.util.List;

import elements.Playfield;
import elements.Tetromino;

public class AI {

	private Playfield field = new Playfield();
	private final int BOTTOM = field.getHEIGHT();
	private final int BORDER = field.getWIDTH();
	private ArrayList<String[]> fMatrix;
	private List<Tetromino> tetrominos;

	public AI() {

	}

	/**
	 * @param field
	 *            current Playfield
	 * @param tetrominos
	 *            list of current and following Tetrominos
	 */
	public Tetromino evaluate(Playfield field, List<Tetromino> tetrominos) {
		this.fMatrix = field.getMatrix();
		this.tetrominos = tetrominos;
		ArrayList<Tetromino> options = initialList();

		return null;
	}

	private ArrayList<Tetromino> initialList() {
		Tetromino t = tetrominos.get(0);
		int[][] tmatrix = t.getMatrix();
		String tcolor = t.getColor();
		ArrayList<Tetromino> allOptions = new ArrayList<>();

		for (int fRow = topRow(); fRow < BOTTOM; fRow++) {
			for (int fColumn = 0; fColumn < BORDER; fColumn++) {
				for (int rotation = 0; rotation < 4; rotation++) {
					if (addToOptions(tmatrix, fRow, fColumn)) {
						Tetromino newT = new Tetromino("");
						newT.setMatrix(tmatrix);
						newT.setColor(tcolor);
						newT.setRow(fRow);
						newT.setColumn(fColumn);
						allOptions.add(newT);
					}
					tmatrix = rotate(tmatrix);
				}
			}
		}
		return allOptions;
	}

	private boolean addToOptions(int[][] tmatrix, int fRow, int fColumn) {

		for (int tRow = 0; tRow < tmatrix.length; ++tRow) {
			if (fRow + tRow < BOTTOM) {
				for (int tColumn = 0; tColumn < tmatrix.length; ++tColumn) {
					if (tmatrix[tRow][tColumn] == 1 && (fColumn + tColumn >= BORDER || fMatrix.get(fRow + tRow)[fColumn + tColumn] != null)) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private int[][] rotate(int[][] tmatrix) {
		int size = tmatrix.length;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tmatrix[size - j - 1][i];
			}
		}
		return rotated;
	}

	private int topRow() {
		for (int fRow = 2; fRow < BOTTOM - 1; fRow++) {
			for (int fColumn = 0; fColumn < BORDER; fColumn++) {
				if (fMatrix.get(fRow)[fColumn] != null)
					return fRow - 1;
			}
		}
		return BOTTOM - 1;
	}

}