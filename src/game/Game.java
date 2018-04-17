package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import test.Test;

public class Game {

	Test test = new Test();

	private List<Tetromino> tetrominos;
	private Queue<Tetromino> nextTetrominos;
	private int[] currTetrominoCoords; // row + column
	private final int LIMIT = 2; // current + next
	private Playfield field;
	private String player;
	private int score;
	private int lvl;
	private boolean gameOver;

	public Game() {
		init();
		pseudoGame();
	}

	private void init() {
		gameOver = false;
		tetrominos = new ArrayList<>();
		nextTetrominos = new ArrayBlockingQueue<Tetromino>(LIMIT);
		field = new Playfield();
		currTetrominoCoords = new int[2];
		resetCoords();
		setScore(0);
		setLvl(1);

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
			nextTetrominos.remove();
			nextTetrominos.add(randomTetromino());
			test.printMatrix(nextTetrominos.element().getMatrix());
			resetCoords();
		}
		if (!gameOver) {
			int[][] currTetromino = nextTetrominos.element().getMatrix();
			int[][] field = this.field.getMatrix();
			for (int row = 0; row < currTetromino.length; row++) {
				for (int column = 0; column < currTetromino.length; column++) {
					if (field[0][column + 3] == 0 && currTetromino[row][column] == 1)
						field[row][column + 3] = currTetromino[row][column];
				}
			}

		} else
			System.out.println("GAME OVER");
	}

	private boolean gameOver() {
		if (currTetrominoCoords[0] == 0 && currTetrominoCoords[1] == 3)
			return true;

		return false;
	}

	private int[][] rotateTetromino(int[][] tetromino) {
		// int[][] tetromino = nextTetrominos.element().getMatrix();
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
		int[][] currTetromino = nextTetrominos.element().getMatrix();
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];
		boolean failed = false;

		if (!collisionDown()) {

			for (int r = 3; r >= 0; r--) {
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
			currTetrominoCoords[0] = row + 1;
			field.setMatrix(newMatrix);
		}
	}

	private void moveLeft() {
		int[][] newMatrix = this.field.getMatrix();
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];

		if (!collisionLeft()) {

			for (int c = 0; c < 4; c++) {
				for (int r = 0; r < 4; r++) {
					newMatrix[row + r][column + c - 1] = newMatrix[row + r][column + c];
					newMatrix[row + r][column + c] = 0;
				}
			}
			currTetrominoCoords[1] = column - 1;
		}

		this.field.setMatrix(newMatrix);
	}

	private void moveRight() {
		int[][] newMatrix = this.field.getMatrix();
		int row = currTetrominoCoords[0];
		int column = currTetrominoCoords[1];

		if (!collisionRight()) {

			for (int c = 3; c >= 0; c--) {
				for (int r = 0; r < 4; r++) {

					newMatrix[row + r][column + c + 1] = newMatrix[row + r][column + c];
					newMatrix[row + r][column + c] = 0;
				}
			}
			currTetrominoCoords[1] = column + 1;
		}

		this.field.setMatrix(newMatrix);
	}

	private boolean collisionDown() {
		boolean collision = false;
		int row = currTetrominoCoords[0] + 3;
		if (row >= field.getHEIGHT() - 1)
			return true;

		// if (newMatrix[row + r + 1][column + c] == 0 && newMatrix[row + r][column + c]
		// == 1) {

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

	private void resetCoords() {
		currTetrominoCoords[0] = 0;
		currTetrominoCoords[1] = 3;
	}

	// private int[][] getCurrentTetromino() {
	// int row = currTetrominoCoords[0];
	// int column = currTetrominoCoords[1];
	// int[][] field = this.field.getMatrix();
	// int[][] matrix = new int[4][4];
	//
	// for (int r = 0; r <= 3; r++) {
	// for (int c = 0; c <= 3; c++) {
	// matrix[r][c] = field[row + r][column + c];
	// }
	// }
	// return matrix;
	// }

	private void pseudoGame() {

		while (!gameOver) {
			// for(int i = 0; i < 40; i++) {
			moveDown();
			test.printMatrix(field.getMatrix());
		}

	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int[] getCurrTetrominoCoords() {
		return currTetrominoCoords;
	}

	public void setCurrTetrominoCoords(int[] currTetrominoCoords) {
		this.currTetrominoCoords = currTetrominoCoords;
	}

}
