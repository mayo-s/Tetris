package game;

import java.util.ArrayList;

public class Move {

	private Playfield field = new Playfield();
	private Game game;
	private final int LEFTEDGE = 0;
	private final int RIGHTEDGE = field.getWIDTH() - 1;
	private final int BOTTOM = field.getHEIGHT() - 1;

	public Move(Game game) {
		this.game = game;
	}

	/**
	 * @param fmatrix
	 *            Playfield matrix
	 * @param tmatrix
	 *            Tetromino matrix
	 * @param frow
	 *            starting row of current Tetromino in field
	 * @param fcolumn
	 *            starting column of current Tetromino in field
	 * @param execute
	 *            execute move in Game if possible?
	 * @return true if move is possible, else false
	 */
	public boolean down(ArrayList<String[]> fmatrix, int[][] tmatrix, int frow, int fcolumn, boolean execute) {
		for (int r = 0; r < tmatrix.length; r++) {
			for (int c = 0; c < tmatrix.length; c++) {
				if (tmatrix[r][c] == 1 && (frow + r >= BOTTOM || fcolumn + c > RIGHTEDGE || fmatrix.get(frow + r + 1)[fcolumn + c] != null)) {
					if (execute) {
						game.tetrominoToField();
						game.next();
					}
					return false;
				}
			}
		}
		if (execute)
			game.down();
		return true;
	}

	/**
	 * @param fmatrix
	 *            Playfield matrix
	 * @param tmatrix
	 *            Tetromino matrix
	 * @param frow
	 *            starting row of current Tetromino in field
	 * @param fcolumn
	 *            starting column of current Tetromino in field
	 * @param execute
	 *            execute move in Game if possible?
	 * @return true if move is possible, else false
	 */
	public boolean left(ArrayList<String[]> fmatrix, int[][] tmatrix, int frow, int fcolumn, boolean execute) {
		int tdimension = tmatrix.length;
		for (int c = 0; c < tdimension; c++) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (fcolumn + c <= LEFTEDGE || fmatrix.get(frow + r)[fcolumn + c - 1] != null)) {
					return false;
				}
			}
		}
		if (execute)
			game.left();
		return true;
	}

	/**
	 * @param fmatrix
	 *            Playfield matrix
	 * @param tmatrix
	 *            Tetromino matrix
	 * @param frow
	 *            starting row of current Tetromino in field
	 * @param fcolumn
	 *            starting column of current Tetromino in field
	 * @param execute
	 *            execute move in Game if possible?
	 * @return true if move is possible, else false
	 */
	public boolean right(ArrayList<String[]> fmatrix, int[][] tmatrix, int frow, int fcolumn, boolean execute) {
		int tdimension = tmatrix.length;
		for (int c = tdimension - 1; c >= 0; c--) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (fcolumn + c >= RIGHTEDGE || fmatrix.get(frow + r)[fcolumn + c + 1] != null)) {
					return false;
				}
			}
		}
		if (execute)
			game.right();
		return true;
	}

	/**
	 * @param fmatrix
	 *            Playfield matrix
	 * @param tmatrix
	 *            Tetromino matrix
	 * @param frow
	 *            starting row of current Tetromino in field
	 * @param fcolumn
	 *            starting column of current Tetromino in field
	 * @param execute
	 *            execute move in Game if possible?
	 * @return true if move is possible, else false
	 */
	public boolean rotate(ArrayList<String[]> fmatrix, int[][] tmatrix, int frow, int fcolumn, boolean execute) {
		int tdimension = tmatrix.length;
		for (int c = 0; c < tdimension; c++) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (fcolumn + c >= RIGHTEDGE || fcolumn + c <= LEFTEDGE || fmatrix.get(frow + r)[fcolumn + c] != null)) {
					return false;
				}
			}
		}
		if (execute)
			game.rotate();
		return true;
	}
}
