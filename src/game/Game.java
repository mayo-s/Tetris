package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Game {

	private List<Tetromino> tetrominos;
	private final int LIMIT = 2;
	private Queue<Tetromino> nextTetrominos;
	private String player;
	private int score;
	private int lvl;

	public Game() {
		init();

		// testing rotation
		Tetromino testTetro = nextTetrominos.element();
		for (int i = 0; i < 4; i++) {
			printMatrix(testTetro);
			testTetro.setMatrix(rotateTetromino(testTetro).getMatrix());
		}
	}

	private void init() {
		tetrominos = new ArrayList<>();
		nextTetrominos = new ArrayBlockingQueue<Tetromino>(LIMIT);

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

	private Tetromino rotateTetromino(Tetromino tetromino) {
		int size = 4;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tetromino.getMatrix()[size - j - 1][i];
			}
		}

		tetromino.setMatrix(rotated);
		return tetromino;
	}

	private void printMatrix(Tetromino tetromino) {

		int[][] matrix = tetromino.getMatrix();

		for (int[] i : matrix) {
			for (int j : i) {
				System.out.print(" " + j + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
