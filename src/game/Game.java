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
	private int[] currTetrominoCoords; // row + column
	private final int LIMIT = 2; // current + next
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
		currTetrominoCoords = new int[2];
		score = 0;
		oldScore = score;

		tetrominos.add(new I());
		tetrominos.add(new O());
		tetrominos.add(new J());
		tetrominos.add(new L());
		tetrominos.add(new S());
		tetrominos.add(new Z());
		tetrominos.add(new T());

		for (int i = 0; i < LIMIT; i++) {
			nextTetrominos.add(randomTetromino());
		}
		System.out.println("START\n");
		tetrominoToField(false);
		resetCoords();
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

	private void tetrominoToField(boolean next) {
		gameOver = gameOver();
		if (next && !gameOver) {
			ArrayList<Boolean> cr = completeRows();
			if (cr.contains(true)) {
				removeCompleteRows(cr);
				updateScore(cr);
			}
			nextTetrominos.remove(0);
			nextTetrominos.add(randomTetromino());
			resetCoords();
		}
		if (!gameOver) {
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			ArrayList<ArrayList<Boolean>> currfield = field.getMatrix();
			for (int row = 0; row < currTetromino.length; row++) {
				for (int column = 0; column < currTetromino.length; column++) {
					if (currfield.get(0).get(column + 3) == false && currTetromino[row][column] == 1)
						currfield.get(row).set((column + 3), true);
				}
			}

		} else {
			System.out.println("GAME OVER");
		}
	}

	private void resetCoords() {
		currTetrominoCoords[0] = 0;
		currTetrominoCoords[1] = 3;
	}

	boolean gameOver() {
		if (currTetrominoCoords[0] == 0 && currTetrominoCoords[1] == 3)
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
			System.out.println("Send new Score " + score + " ( " + oldScore + " )");
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
		 ArrayList<ArrayList<Boolean>>matrix = field.getMatrix();
		int row = currTetrominoCoords[0];
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

	}

	void moveDown() {
		if (!collisionBottom()) {
			ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int row = currTetrominoCoords[0];
			int column = currTetrominoCoords[1];
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);
			for (int r = (3 - bottom); r >= 0; r--) {
				for (int c = (0 + left); c <= (3 - right); c++) {
					if (newMatrix.get(row + r + 1).get(column + c) == true && currTetromino[r][c] == 1) {
						failed = true;
						tetrominoToField(true);
						break;
					} else if (newMatrix.get(row + r + 1).get(column + c) == true && currTetromino[r][c] == 0) {
						// newMatrix[row + r + 1][column + c] = 1;
					} else if (newMatrix.get(row + r + 1).get(column + c) == false && currTetromino[r][c] == 1) {
						newMatrix.get(row + r + 1).set(column + c, true);
						newMatrix.get(row + r ).set(column + c, false);
					}
				}
				if (failed)
					break;
			}

			if (!failed) {
				field.setMatrix(newMatrix);
				currTetrominoCoords[0]++;
			}
		} else
			tetrominoToField(true);
	}

	void moveLeft() {
		if (!collisionLeft()) {
			ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int row = currTetrominoCoords[0];
			int column = currTetrominoCoords[1];
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int c = (0 + left); c <= (3 - right); c++) {
				for (int r = 0; r <= (3 - bottom); r++) {
					// clear old Tetromino
//					if (newMatrix.get(row + r).get(column + c) && currTetromino[r][c] == 1) {
//						newMatrix.get(row + r).set(column + c, false);
//					}
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
				currTetrominoCoords[1]--;
				field.setMatrix(newMatrix);
			}
		}
	}

	void moveRight() {
		if (!collisionRight()) {
			ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int row = currTetrominoCoords[0];
			int column = currTetrominoCoords[1];
			boolean failed = false;
			int bottom = emptyLineBottom(currTetromino);
			int left = emptyLineLeft(currTetromino);
			int right = emptyLineRight(currTetromino);

			for (int c = (3 - right); c >= (0 + left); c--) {
				for (int r = 0; r <= (3 - bottom); r++) {
					// clear old Tetromino
					// if (newMatrix[row + r][column + c] == 1 && currTetromino[r][c] == 1) {
					// newMatrix[row + r][column + c] = 0;
					// }
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
				currTetrominoCoords[1]++;
				field.setMatrix(newMatrix);
			}
		}
	}

	void moveRotate() {
		ArrayList<ArrayList<Boolean>> newMatrix = field.getMatrix();
		int[][] currTetromino = nextTetrominos.get(0).getMatrix();
		int[][] rotatedTetromino = rotateTetromino(currTetromino);
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];
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
		int row = currTetrominoCoords[0] + 3 - emptyLineBottom(nextTetrominos.get(0).getMatrix());
		if (row >= field.getHEIGHT() - 1) {
			return true;
		}

		return false;
	}

	private boolean collisionLeft() {
		int column = currTetrominoCoords[1] + emptyLineLeft(nextTetrominos.get(0).getMatrix());
		if (column <= 0) {
			return true;
		}

		return false;
	}

	private boolean collisionRight() {
		int column = currTetrominoCoords[1] + 3 - emptyLineRight(nextTetrominos.get(0).getMatrix());
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
