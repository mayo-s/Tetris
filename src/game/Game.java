package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import test.Test;

public class Game {

	private Test testing = new Test();

	private List<Tetromino> tetrominos;
	private Queue<Tetromino> nextTetrominos;
	private final int LIMIT = 2; // current + next
	private Playfield field;
	private String player;
	private int score;
	private int lvl;

	public Game() {
		init();
		test();
	}

	private void init() {
		tetrominos = new ArrayList<>();
		nextTetrominos = new ArrayBlockingQueue<Tetromino>(LIMIT);
		field = new Playfield();

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
		return tetrominos.get(tetro);
	}

	private void tetrominoToField() {

		int[][] currTetromino = nextTetrominos.element().getMatrix();

		for (int row = 0; row < currTetromino.length; row++) {
			for (int column = 0; column < currTetromino.length; column++) {

				field.getMatrix()[row][column + 3] = currTetromino[row][column];				
			}
		}
		field.setCurrTetrominoCoords(new int[]{0, 3});
	}

	private Tetromino rotateTetromino(Tetromino tetromino) {
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

	private void test() {

//		testing.printMatrix(field.getMatrix());
//		Tetromino testTetro = nextTetrominos.element();
//		for (int i = 0; i < 4; i++) {
//			testing.printMatrix(testTetro.getMatrix());
//			testTetro.setMatrix(rotateTetromino(testTetro).getMatrix());
//		}
		tetrominoToField();
		testing.printMatrix(field.getMatrix());
	}

}
