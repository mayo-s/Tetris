package game;

import java.util.List;
import java.util.Stack;

public class Game {

	private List<Tetromino> tetrominos;
	private int LIMIT;
	private Stack<Tetromino> nextTetrominos;
	private String player;
	private int score;
	private int lvl;

	public Game() {
		init();

	}

	private void init() {

		tetrominos.add(new I());
		tetrominos.add(new O());
		tetrominos.add(new J());
		tetrominos.add(new L());
		tetrominos.add(new S());
		tetrominos.add(new Z());
		tetrominos.add(new T());

		for (int i = 0; i < LIMIT; i++) {
			nextTetrominos.push(randomTetromino());
		}

	}

	private Tetromino randomTetromino() {

		int tetro = (int) (Math.random() * tetrominos.size());
		return tetrominos.get(tetro);
	}
}
