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
	private List<Tetromino> nextTetrominos;
	private final int LIMIT = 2; // current + next
	private final int STARTROW = 0;
	private final int STARTCOLUMN = 3;
	private Playfield field;
	private boolean gameOver;
	private int score;
	private int oldScore;

	public Game() {
		init();
	}

	private void init() {
		gameOver = false;
		tetrominos = new ArrayList<>();
		nextTetrominos = new ArrayList<>(LIMIT);
		field = new Playfield();
		score = 0;
		oldScore = score;

		tetrominos.add(new I(STARTROW, STARTCOLUMN));
		tetrominos.add(new O(STARTROW, STARTCOLUMN));
		tetrominos.add(new J(STARTROW, STARTCOLUMN));
		tetrominos.add(new L(STARTROW, STARTCOLUMN));
		tetrominos.add(new S(STARTROW, STARTCOLUMN));
		tetrominos.add(new Z(STARTROW, STARTCOLUMN));
		tetrominos.add(new T(STARTROW, STARTCOLUMN));

		for (int i = 0; i < LIMIT; i++) {
			nextTetrominos.add(randomTetromino());
		}
		System.out.println("START\n");
		tetrominoToField();
	}

	private Tetromino randomTetromino() {
		int tetro = (int) (Math.random() * tetrominos.size());
		int randomRotation = (int) (Math.random() * 4);
		Tetromino newTetromino = tetrominos.get(tetro);
		for (int i = 0; i < randomRotation; i++) {
			newTetromino.setMatrix(rotateTetromino(newTetromino.getMatrix()));
		}
		return newTetromino;
	}

	private void next() {

		gameOver = gameOver();
		if (!gameOver) {
			ArrayList<Boolean> cr = completeRows();
			if (cr.contains(true)) {
				removeCompleteRows(cr);
				updateScore(cr);
			}
			nextTetrominos.get(0).setRow(STARTROW);
			nextTetrominos.get(0).setColumn(STARTCOLUMN);
			nextTetrominos.remove(0);
			nextTetrominos.add(randomTetromino());
			tetrominoToField();
		} else
			System.out.println("GAME OVER");

	}

	private void tetrominoToField() {

		int[][] currTetromino = nextTetrominos.get(0).getMatrix();
		int row = nextTetrominos.get(0).getRow();
		int rows = currTetromino.length;
		int column = nextTetrominos.get(0).getColumn();
		int columns = currTetromino[0].length;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (!field.getMatrix().get(row + r).get(column + c) && currTetromino[r][c] == 1)
					field.getMatrix().get(row + r).set((column + c), true);
			}
		}
	}

	boolean gameOver() {
		if (nextTetrominos.get(0).getRow() == STARTROW && nextTetrominos.get(0).getColumn() == STARTCOLUMN)
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

	private void updateScore(ArrayList<Boolean> completeRows) {
		int count = 0;
		for (Boolean complete : completeRows) {
			if (complete)
				count++;
		}

		int newScore = score;
		if (count == 1)
			newScore = score + 1;
		else if (count == 2)
			newScore = score + 3;
		else if (count == 3)
			newScore = score + 5;
		else if (count == 4)
			newScore = score + 8;

		score = newScore;
	}

	private ArrayList<Boolean> completeRows() {
		ArrayList<ArrayList<Boolean>> matrix = field.getMatrix();
		int row = nextTetrominos.get(0).getRow();
		ArrayList<Boolean> complete = new ArrayList<>(Arrays.asList(false, false, false, false));

		for (int r = row; r <= (row + 3); r++) {
			boolean gap = false;
			if (r < matrix.size()) {
				for (Boolean cell : matrix.get(r)) {
					if (cell == false) {
						gap = true;
						break;
					}
				}
				if (!gap) {
					complete.set((r - row), true);
				}
			}
		}
		return complete;
	}

	private void removeCompleteRows(ArrayList<Boolean> completeRows) {
		int row = nextTetrominos.get(0).getRow();

		for (int r = 0; r < completeRows.size(); r++) {
			if (completeRows.get(r)) {
				field.getMatrix().remove(row + r);
				field.getMatrix().add(0, field.addRow());
			}
		}
	}

	void moveDown() {
		if (!collisionBottom()) {
			ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int row = nextTetrominos.get(0).getRow();
			int column = nextTetrominos.get(0).getColumn();
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);
			for (int r = (3 - bottom); r >= 0; r--) {
				for (int c = (0 + left); c <= (3 - right); c++) {
					if (newMatrix.get(row + r + 1).get(column + c) == true && currTetromino[r][c] == 1) {
						failed = true;
						next();
						break;
					} else if (newMatrix.get(row + r + 1).get(column + c) == true && currTetromino[r][c] == 0) {
						// newMatrix[row + r + 1][column + c] = 1;
					} else if (newMatrix.get(row + r + 1).get(column + c) == false && currTetromino[r][c] == 1) {
						newMatrix.get(row + r + 1).set(column + c, true);
						newMatrix.get(row + r).set(column + c, false);
					}
				}
				if (failed)
					break;
			}

			if (!failed) {
				field.setMatrix(newMatrix);
				nextTetrominos.get(0).setRow(row + 1);
			}
		} else
			next();
	}

	void moveLeft() {
		if (!collisionLeft()) {
			ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int row = nextTetrominos.get(0).getRow();
			int column = nextTetrominos.get(0).getColumn();
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int c = (0 + left); c <= (3 - right); c++) {
				for (int r = 0; r <= (3 - bottom); r++) {
					if (newMatrix.get(row + r).get(column + c - 1) && currTetromino[r][c] == 1) {
						failed = true;
						break;
					} else if (newMatrix.get(row + r).get(column + c - 1) && currTetromino[r][c] == 0) {
						// ignore
					} else if (!newMatrix.get(row + r).get(column + c - 1) && currTetromino[r][c] == 1) {
						newMatrix.get(row + r).set(column + c - 1, true);
						newMatrix.get(row + r).set(column + c, false);
					}
				}
				if (failed)
					break;
			}
			if (!failed) {
				nextTetrominos.get(0).setColumn(nextTetrominos.get(0).getColumn() - 1);
				field.setMatrix(newMatrix);
			}
		}
	}

	void moveRight() {
		if (!collisionRight()) {
			ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int row = nextTetrominos.get(0).getRow();
			int column = nextTetrominos.get(0).getColumn();
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int c = (3 - right); c >= (0 + left); c--) {
				for (int r = 0; r <= (3 - bottom); r++) {
					if (newMatrix.get(row + r).get(column + c + 1) && currTetromino[r][c] == 1) {
						failed = true;
						break;
					} else if (newMatrix.get(row + r).get(column + c + 1) && currTetromino[r][c] == 0) {
						// ignore
					} else if (!newMatrix.get(row + r).get(column + c + 1) && currTetromino[r][c] == 1) {
						newMatrix.get(row + r).set(column + c + 1, true);
						newMatrix.get(row + r).set(column + c, false);
					}
				}
				if (failed)
					break;
			}
			if (!failed) {
				nextTetrominos.get(0).setColumn((nextTetrominos.get(0).getColumn()) + 1);
				field.setMatrix(newMatrix);
			}
		}
	}

	void moveRotate() {
		ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
		int[][] currTetromino = nextTetrominos.get(0).getMatrix();
		int[][] rotatedTetromino = rotateTetromino(currTetromino);
		int row = nextTetrominos.get(0).getRow();
		int column = nextTetrominos.get(0).getColumn();
		boolean failed = false;
		int bottom = emptyLineBottom(currTetromino);
		int left = emptyLineLeft(currTetromino);
		int right = emptyLineRight(currTetromino);

		for (int r = 0; r <= (3 - bottom); r++) {
			for (int c = (0 + left); c <= (3 - right); c++) {
				// clear currTetromino
				if (newMatrix.get(row + r).get(column + c) && currTetromino[r][c] == 1) {
					newMatrix.get(row + r).set(column + c, false);
				}
				if (newMatrix.get(row + r).get(column + c) && rotatedTetromino[r][c] == 1) {
					failed = true;
					break;
				} else if ((newMatrix.get(row + r).get(column + c) && rotatedTetromino[r][c] == 0)
						|| (!newMatrix.get(row + r).get(column + c) && rotatedTetromino[r][c] == 1)) {
					newMatrix.get(row + r).set(column + c, true);
				} else if (!newMatrix.get(row + r).get(column + c) && rotatedTetromino[r][c] == 0) {
					newMatrix.get(row + r).set(column + c, false);
				} else {
					failed = true;
					break;
				}
			}
			if (failed)
				break;
		}

		if (!failed) {
			field.setMatrix(newMatrix);
			nextTetrominos.get(0).setMatrix(rotatedTetromino);
		}
	}

	private boolean collisionBottom() {
		int row = nextTetrominos.get(0).getRow() + 3 - emptyLineBottom(nextTetrominos.get(0).getMatrix());
		if (row >= field.getHEIGHT() - 1) {
			return true;
		}

		return false;
	}

	private boolean collisionLeft() {
		int column = nextTetrominos.get(0).getColumn() + emptyLineLeft(nextTetrominos.get(0).getMatrix());
		if (column <= 0) {
			return true;
		}

		return false;
	}

	private boolean collisionRight() {
		int column = nextTetrominos.get(0).getColumn() + 3 - emptyLineRight(nextTetrominos.get(0).getMatrix());
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
		int lines = 0;
		boolean empty = true;
		for (int r = 3; r >= 0; r--) {
			for (int c = 0; c < 4; c++) {
				if (matrix[r][c] == 1) {
					empty = false;
				}
			}
			if (empty) {
				lines++;
			} else
				break;
		}
		return lines;
	}

	// vertical
	//
	// 0010
	// 0010
	// 0010
	// 0010
	//
	private int emptyLineRight(int[][] matrix) {
		int lines = 0;
		boolean empty = true;
		for (int c = 3; c >= 0; c--) {
			for (int r = 0; r <= 3; r++) {
				if (matrix[r][c] == 1) {
					empty = false;
				}
			}
			if (empty) {
				lines++;
			} else
				break;
		}
		return lines;
	}

	private int emptyLineLeft(int[][] matrix) {
		int lines = 0;
		boolean empty = true;
		for (int c = 0; c <= 3; c++) {
			for (int r = 0; r <= 3; r++) {
				if (matrix[r][c] == 1) {
					empty = false;
				}
			}
			if (empty) {
				lines++;
			} else
				break;
		}
		return lines;
	}

	public List<Tetromino> getNextTetrominos() {
		return nextTetrominos;
	}

	public void setNextTetrominos(List<Tetromino> nextTetrominos) {
		this.nextTetrominos = nextTetrominos;
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
}
