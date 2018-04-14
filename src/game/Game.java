package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Game {
	
	private List<Tetromino> tetrominos;
	private Queue<Tetromino> nextTetrominos;
	private int[] currTetrominoCoords;
	private final int LIMIT = 2; // current + next
	private Playfield field;
	private String player;
	private int score;
	private int lvl;

	public Game() {
		init();
		tetrominoToField(nextTetrominos.element().getMatrix());
	}

	private void init() {
		tetrominos = new ArrayList<>();
		nextTetrominos = new ArrayBlockingQueue<Tetromino>(LIMIT);
		field = new Playfield();
		currTetrominoCoords = new int[2];
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
		return tetrominos.get(tetro);
	}

	private void tetrominoToField(int[][] currTetromino) {

		for (int row = 0; row < currTetromino.length; row++) {
			for (int column = 0; column < currTetromino.length; column++) {

				field.getMatrix()[row][column + 3] = currTetromino[row][column];
			}
		}
		setCurrTetrominoCoords(new int[] { 0, 3 });
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
