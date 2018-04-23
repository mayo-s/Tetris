package game;

import java.util.ArrayList;
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
import test.Test;

public class Game {

	Test test = new Test();

	private List<Tetromino> tetrominos;
	private List<Tetromino> nextTetrominos;
	private int[] currTetrominoCoords; // row + column
	private final int LIMIT = 2; // current + next
	private Playfield field;
	private boolean gameOver;

	public Game() {
		init();
	}

	private void init() {
		gameOver = false;
		tetrominos = new ArrayList<>();
		nextTetrominos = new ArrayList<>(LIMIT);
		field = new Playfield();
		currTetrominoCoords = new int[2];

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
			System.out.println("next tetromino");
			nextTetrominos.remove(0);
			nextTetrominos.add(randomTetromino());
			
			test.printMatrix(nextTetrominos.get(1).getMatrix());
			resetCoords();
		}
		if (!gameOver) {
			int[][] currTetromino = nextTetrominos.get(0).getMatrix();
			int[][] field = this.field.getMatrix().clone();
			for (int row = 0; row < currTetromino.length; row++) {
				for (int column = 0; column < currTetromino.length; column++) {
					if (field[0][column + 3] == 0 && currTetromino[row][column] == 1)
						field[row][column + 3] = currTetromino[row][column];
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

	public void moveDown() {
		int[][] newMatrix = field.getMatrix().clone();
		int[][] currTetromino = nextTetrominos.get(0).getMatrix();
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];
		boolean failed = false;

		if (!collisionDown()) {
			int lineOffset = emptyLineBottom(currTetromino);
			System.out.println("line offset down " + lineOffset);
			for (int r = (3 - lineOffset); r >= 0; r--) {
				for (int c = 0; c < 4; c++) {
					if (newMatrix[row + r + 1][column + c] == 0) {
						newMatrix[row + r + 1][column + c] = currTetromino[r][c];
						newMatrix[row + r][column + c] = 0;
					} else
						failed = true;
				}
			}
		} else
			failed = true;

		if (failed) {
			System.out.println("collision down");
			tetrominoToField(true);
		} else {
			currTetrominoCoords[0]++;
			field.setMatrix(newMatrix);
		}
	}

	void moveLeft() {
		int[][] newMatrix = field.getMatrix();
		int[][] currTetromino = nextTetrominos.get(0).getMatrix();
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];
		boolean failed = false;

		if (!collisionLeft()) {
			int lineOffset = emptyLineLeft(currTetromino);
			for (int c = (0 + lineOffset); c < 4; c++) {
				for (int r = 0; r < 4; r++) {
					if (newMatrix[row + r][column + c - 1] == 0) {
						newMatrix[row + r][column + c - 1] = currTetromino[r][c];
						newMatrix[row + r][column + c] = 0;
					} else
						failed = true;
				}
			}
		} else
			failed = true;

		if (!failed) {
			currTetrominoCoords[1]--;
			field.setMatrix(newMatrix);
		}
	}

	void moveRight() {
		int[][] newMatrix = field.getMatrix();
		int[][] currTetromino = nextTetrominos.get(0).getMatrix();
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];
		boolean failed = false;

		if (!collisionRight()) {

			int lineOffset = emptyLineRight(currTetromino);
			for (int c = (3 - lineOffset); c >= 0; c--) {
				for (int r = 0; r < 4; r++) {
					if (newMatrix[row + r][column + c + 1] == 0) {
						newMatrix[row + r][column + c + 1] = currTetromino[r][c];
						newMatrix[row + r][column + c] = 0;
					} else
						failed = true;
					break;
				}
				if (failed)
					break;
			}
		} else
			failed = true;

		if (!failed) {
			currTetrominoCoords[1]++;
			field.setMatrix(newMatrix);
		}
	}

	boolean collisionDown() {
		boolean collision = false;
		int row = currTetrominoCoords[0] + 3 - emptyLineBottom(nextTetrominos.get(0).getMatrix());
		if (row >= field.getHEIGHT() - 1)
			return true;

		return collision;
	}

	private boolean collisionLeft() {
		boolean collision = false;
		int column = currTetrominoCoords[1];
		if (column <= 0)
			return true;

		return collision;
	}

	private boolean collisionRight() {
		boolean collision = false;
		int column = currTetrominoCoords[1] + 3;
		if (column >= field.getWIDTH() - 1)
			return true;

		return collision;
	}

	// horizontal
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
	private int emptyLineRight(int[][] matrix) {
		int lines = 0;
		boolean empty = true;
		for (int c = 3; c >= 0; c--) {
			for (int r = 0; r < 4; r++) {
				if (matrix[r][c] == 1) {
					empty = false;
				}
			}
			if (empty) {
				lines++;
			} else
				break;
		}
		System.out.println("line offset right " + lines);
		return lines;
	}

	private int emptyLineLeft(int[][] matrix) {
		int lines = 0;
		boolean empty = true;
		for (int c = 0; c > 3; c++) {
			for (int r = 0; r < 4; r++) {
				if (matrix[r][c] == 1) {
					empty = false;
				}
			}
			if (empty) {
				lines++;
			} else
				break;
		}
		System.out.println("line offset left " + lines);
		return lines;
	}

	public int[] getCurrTetrominoCoords() {
		return currTetrominoCoords;
	}

	public void setCurrTetrominoCoords(int[] currTetrominoCoords) {
		this.currTetrominoCoords = currTetrominoCoords;
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
}
