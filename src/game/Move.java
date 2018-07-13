package game;

import java.util.ArrayList;

import elements.Playfield;

public class Move {

	private Playfield field = new Playfield();
	private final int LEFTEDGE = 0;
	private final int RIGHTEDGE = field.getWIDTH() - 1;
	private final int BOTTOM = field.getHEIGHT() - 1;

	public Move() {
	}

	/**
	 * @param field
	 *            current Playfield
	 * @param tetromino
	 *            current Tetromino
	 * @param frow
	 * 			  row position in field
	 * @param fcolumn
	 * 			  column position in field
	 * @return is move downwards possible?
	 */
	public boolean down(ArrayList<String[]> fmatrix, int[][] tmatrix, int frow, int fcolumn) {
		for (int r = 0; r < tmatrix.length; r++) {
			for (int c = 0; c < tmatrix.length; c++) {
				if (tmatrix[r][c] == 1 && (frow + r >= BOTTOM || fcolumn + c > RIGHTEDGE || fmatrix.get(frow + r + 1)[fcolumn + c] != null)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param field
	 *            current Playfield
	 * @param tetromino
	 *            current Tetromino
	 * @return is move leftwards possible?
	 */
	boolean left(ArrayList<String[]> fmatrix, int[][] tmatrix, int row, int column) {
		int tdimension = tmatrix.length;
		for (int c = 0; c < tdimension; c++) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (column + c <= LEFTEDGE || fmatrix.get(row + r)[column + c - 1] != null)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param field
	 *            current Playfield
	 * @param tetromino
	 *            current Tetromino
	 * @return is move rightwards possible?
	 */
	boolean right(ArrayList<String[]> fmatrix, int[][] tmatrix, int row, int column) {
		int tdimension = tmatrix.length;
		for (int c = tdimension - 1; c >= 0; c--) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (column + c >= RIGHTEDGE || fmatrix.get(row + r)[column + c + 1] != null)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param field
	 *            current Playfield
	 * @param tetromino
	 *            rotated Tetromino
	 * @return is rotation possible?
	 */
	boolean rotate(ArrayList<String[]> fmatrix, int[][] tmatrix, int row, int column) {
		int tdimension = tmatrix.length;
		for (int c = 0; c < tdimension; c++) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (column + c >= RIGHTEDGE || column + c <= LEFTEDGE
						|| fmatrix.get(row + r)[column + c] != null)) {
					return false;
				}
			}
		}
		return true;
	}
}
