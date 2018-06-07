package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elements.I;
import elements.J;
import elements.L;
import elements.O;
import elements.Playfield;
import elements.S;
import elements.T;
import elements.Tetromino;
import elements.Z;

public class Game {

	private List<Tetromino> tetrominos;
	private final int LIMIT = 2; // current + next
	private final int STARTROW = 0;
	private final int STARTCOLUMN = 3;
	private final int[] POINTS = { 0, 100, 300, 500, 800 };
	private Playfield field;
	private boolean gameOver;
	private int lvl;
	private int tcount; // tetromino count
	private int lcount; // eliminated line count
	private int score;
	private int oldScore;

	public Game() {
		init();
	}

	private void init() {
		gameOver = false;
		tetrominos = new ArrayList<>(LIMIT);
		field = new Playfield();
		score = 0;
		oldScore = score;
		lvl = 1;
		tcount = 1;
		lcount = 0;

		for (int i = 0; i < LIMIT; i++) {
			tetrominos.add(randomTetromino());
		}

		System.out.println("START\n");
		tetrominoToField();
	}

	private Tetromino randomTetromino() {
		int tetro = (int) (Math.random() * 7);
		int randomRotation = (int) (Math.random() * 4);
		Tetromino newTetromino = null;
		switch (tetro) {
		case 0:
			newTetromino = new T(STARTROW, STARTCOLUMN);
			break;
		case 1:
			newTetromino = new I(STARTROW, STARTCOLUMN);
			break;
		case 2:
			newTetromino = new O(STARTROW, STARTCOLUMN);
			break;
		case 3:
			newTetromino = new J(STARTROW, STARTCOLUMN);
			break;
		case 4:
			newTetromino = new L(STARTROW, STARTCOLUMN);
			break;
		case 5:
			newTetromino = new S(STARTROW, STARTCOLUMN);
			break;
		case 6:
			newTetromino = new Z(STARTROW, STARTCOLUMN);
			break;
		}

		for (int i = 0; i < randomRotation; i++) {
			newTetromino.setMatrix(rotateTetromino(newTetromino.getMatrix()));
		}
		return newTetromino;
	}

	private void next() {

		gameOver = gameOver();
		if (!gameOver) {

			tcount++;
			ArrayList<Boolean> cr = completeRows();
			if (cr.contains(true)) {
				removeCompleteRows(cr);
				updateScore(cr);
			}

			tetrominos.get(0).setRow(STARTROW);
			tetrominos.get(0).setColumn(STARTCOLUMN);
			tetrominos.remove(0);
			tetrominos.add(randomTetromino());
			tetrominoToField();
		} else {
			System.out.println("GAME OVER");
			System.out.println(tcount + " Tetrominos used");
		}

	}

	private void tetrominoToField() {

		int[][] currTetromino = tetrominos.get(0).getMatrix();
		String color = tetrominos.get(0).getColor();
		int row = tetrominos.get(0).getRow();
		int rows = currTetromino.length;
		int column = tetrominos.get(0).getColumn();
		int columns = currTetromino[0].length;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (field.getMatrix().get(row + r)[column + c] == null && currTetromino[r][c] == 1)
					field.getMatrix().get(row + r)[column + c] = color;
			}
		}
	}

	boolean gameOver() {
		if (tetrominos.get(0).getRow() == STARTROW && tetrominos.get(0).getColumn() == STARTCOLUMN)
			return true;
		return false;
	}

	private int[][] rotateTetromino(int[][] tetromino) {
		int size = tetromino.length;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tetromino[size - j - 1][i];
			}
		}
		return rotated;
	}

	boolean newScore() {
		if (score != oldScore) {
			oldScore = score;
			return true;
		}
		return false;
	}

	boolean levelUp() {
		if (lcount / lvl >= 10) {
			lvl++;
			return true;
		}
		return false;
	}

	private void updateScore(ArrayList<Boolean> completeRows) {
		int count = 0;
		for (Boolean complete : completeRows) {
			if (complete)
				count++;
		}
		score = score + POINTS[count] * lvl;
		lcount += count;
	}

	private ArrayList<Boolean> completeRows() {
		ArrayList<String[]> matrix = field.getMatrix();
		int row = tetrominos.get(0).getRow();
		ArrayList<Boolean> complete = new ArrayList<>(Arrays.asList(false, false, false, false));

		for (int r = row; r <= (row + 3); r++) {
			boolean gap = false;
			if (r < matrix.size()) {
				for (String cell : matrix.get(r)) {
					if (cell == null) {
						gap = true;
						break;
					}
				}
				if (!gap) {
					complete.set((r - row), true);
				}
			}
		}
		for(boolean gap : complete) System.out.println(gap);
		System.out.println();
		return complete;
	}

	private void removeCompleteRows(ArrayList<Boolean> completeRows) {
		int row = tetrominos.get(0).getRow();

		for (int r = 0; r < completeRows.size(); r++) {
			if (completeRows.get(r)) {
				field.getMatrix().remove(row + r);
				field.getMatrix().add(0, field.addRow());
			}
		}
	}

	void moveDown() {
		if (!collisionBottom()) {
			ArrayList<String[]> newMatrix = field.getMatrix();
			int[][] currTetromino = tetrominos.get(0).getMatrix();
			int row = tetrominos.get(0).getRow();
			int column = tetrominos.get(0).getColumn();
			String color = tetrominos.get(0).getColor();
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int r = (3 - bottom); r >= 0; r--) {
				for (int c = (0 + left); c <= (3 - right); c++) {

					if ((column + c) < 0 || (column + c) >= field.getWIDTH()) {
						// ignore
					} else if (newMatrix.get(row + r + 1)[column + c] != null && currTetromino[r][c] == 1) {
						failed = true;
						next();
						break;
					} else if (newMatrix.get(row + r + 1)[column + c] != null && currTetromino[r][c] == 0) {
						// newMatrix[row + r + 1][column + c] = 1;
					} else if (newMatrix.get(row + r + 1)[column + c] == null && currTetromino[r][c] == 1) {
						newMatrix.get(row + r + 1)[column + c] = color;
						newMatrix.get(row + r)[column + c] = null;
					}
				}
				if (failed)
					break;
			}

			if (!failed) {
				score++;
				field.setMatrix(newMatrix);
				tetrominos.get(0).setRow(row + 1);
			}
		} else
			next();
	}

	void moveLeft() {
		if (!collisionLeft()) {
			ArrayList<String[]> newMatrix = field.getMatrix();
			int[][] currTetromino = tetrominos.get(0).getMatrix();
			int row = tetrominos.get(0).getRow();
			int column = tetrominos.get(0).getColumn();
			String color = tetrominos.get(0).getColor();
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int c = (0 + left); c <= (3 - right); c++) {
				for (int r = 0; r <= (3 - bottom); r++) {
					if (newMatrix.get(row + r)[column + c - 1] != null && currTetromino[r][c] == 1) {
						failed = true;
						break;
					}
					// else if (newMatrix.get(row + r).get(column + c - 1) && currTetromino[r][c] ==
					// 0) {
					// // ignore
					// }
					else if (newMatrix.get(row + r)[column + c - 1] == null && currTetromino[r][c] == 1) {
						newMatrix.get(row + r)[column + c - 1] = color;
						newMatrix.get(row + r)[column + c] = null;
					}
				}
				if (failed)
					break;
			}
			if (!failed) {
				tetrominos.get(0).setColumn(tetrominos.get(0).getColumn() - 1);
				field.setMatrix(newMatrix);
			}
		}
	}

	void moveRight() {
		if (!collisionRight()) {
			ArrayList<String[]> newMatrix = field.getMatrix();
			int[][] currTetromino = tetrominos.get(0).getMatrix();
			int row = tetrominos.get(0).getRow();
			int column = tetrominos.get(0).getColumn();
			String color = tetrominos.get(0).getColor();
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int c = (3 - right); c >= (0 + left); c--) {
				for (int r = 0; r <= (3 - bottom); r++) {
					if (newMatrix.get(row + r)[column + c + 1] != null && currTetromino[r][c] == 1) {
						failed = true;
						break;
					}
					// else if (newMatrix.get(row + r).get(column + c + 1) && currTetromino[r][c] ==
					// 0) {
					// // ignore
					// }
					else if (newMatrix.get(row + r)[column + c + 1] == null && currTetromino[r][c] == 1) {
						newMatrix.get(row + r)[column + c + 1] = color;
						newMatrix.get(row + r)[column + c] = null;
					}
				}
				if (failed)
					break;
			}
			if (!failed) {
				tetrominos.get(0).setColumn((tetrominos.get(0).getColumn()) + 1);
				field.setMatrix(newMatrix);
			}
		}
	}

	void moveRotate() {
		ArrayList<String[]> newMatrix = field.getMatrix();
		int[][] currTetromino = tetrominos.get(0).getMatrix();
		int[][] rotatedTetromino = rotateTetromino(currTetromino);
		int row = tetrominos.get(0).getRow();
		int column = tetrominos.get(0).getColumn();
		String color = tetrominos.get(0).getColor();
		boolean failed = false;
		int bottom = emptyLineBottom(currTetromino);
		int left = emptyLineLeft(currTetromino);
		int right = emptyLineRight(currTetromino);

		if (!(column < 0 || row > field.getHEIGHT())) {
			for (int r = 0; r <= (3 - bottom); r++) {
				for (int c = (0 + left); c <= (3 - right); c++) {

					// clear currTetromino
					if (newMatrix.get(row + r)[column + c] != null && currTetromino[r][c] == 1) {
						newMatrix.get(row + r)[column + c] = null;
					}
					// rotate
					if (newMatrix.get(row + r)[column + c] != null && rotatedTetromino[r][c] == 1) {
						failed = true;
						break;
					} else if ((newMatrix.get(row + r)[column + c] != null && rotatedTetromino[r][c] == 0)
							|| (newMatrix.get(row + r)[column + c] == null && rotatedTetromino[r][c] == 1)) {
						newMatrix.get(row + r)[column + c] = color;
					} else if (newMatrix.get(row + r)[column + c] == null && rotatedTetromino[r][c] == 0) {
						newMatrix.get(row + r)[column + c] = null;
					} else {
						failed = true;
						break;
					}
				}
				if (failed)
					break;
			}
		} else
			failed = true;

		if (!failed) {
			field.setMatrix(newMatrix);
			tetrominos.get(0).setMatrix(rotatedTetromino);
		}
	}

	private boolean collisionBottom() {
		int row = tetrominos.get(0).getRow() + 3 - emptyLineBottom(tetrominos.get(0).getMatrix());
		if (row >= field.getHEIGHT() - 1) {
			return true;
		}

		return false;
	}

	private boolean collisionLeft() {
		int column = tetrominos.get(0).getColumn() + emptyLineLeft(tetrominos.get(0).getMatrix());
		if (column <= 0) {
			return true;
		}

		return false;
	}

	private boolean collisionRight() {
		int column = tetrominos.get(0).getColumn() + 3 - emptyLineRight(tetrominos.get(0).getMatrix());
		if (column >= field.getWIDTH() - 1) {
			return true;
		}

		return false;
	}

	// horizontal
	//
	// 0000
	// 1111
	// 0000
	// 0000
	//
	private int emptyLineBottom(int[][] matrix) {
		for (int r = 3; r >= 0; r--) {
			for (int c = 0; c < 4; c++) {
				if (matrix[r][c] == 1) {
					return 3 - r;
				}
			}
		}
		throw new IllegalStateException("Tetromino is empty");
	}

	// vertical
	//
	// 0010
	// 0010
	// 0010
	// 0010
	//
	private int emptyLineRight(int[][] matrix) {
		for (int c = 3; c >= 0; c--) {
			for (int r = 0; r <= 3; r++) {
				if (matrix[r][c] == 1) {
					return 3 - c;
				}
			}
		}
		throw new IllegalStateException("Tetromino is empty");
	}

	private int emptyLineLeft(int[][] matrix) {
		for (int c = 0; c <= 3; c++) {
			for (int r = 0; r <= 3; r++) {
				if (matrix[r][c] == 1) {
					return 0 + c;
				}
			}
		}
		throw new IllegalStateException("Tetromino is empty");
	}

	public List<Tetromino> getNextTetrominos() {
		return tetrominos;
	}

	public void setNextTetrominos(List<Tetromino> nextTetrominos) {
		this.tetrominos = nextTetrominos;
	}

	public Playfield getField() {
		return field;
	}

	public void setField(Playfield field) {
		this.field = field;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public int getScore() {
		return score;
	}

	public int getLvl() {
		return lvl;
	}

	public int getTcount() {
		return tcount;
	}

	public int getLcount() {
		return lcount;
	}
}
