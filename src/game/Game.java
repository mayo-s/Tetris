package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

	private Playfield field = new Playfield();
	private final int LIMIT = 3; // current + next
	private final int STARTROW = 0;
	private final int STARTCOLUMN = 3;
	private final int[] POINTS = { 0, 100, 300, 500, 800 };

	private boolean gameOver;
	private int lvl;
	private int tcount; // tetromino count
	private int lcount; // eliminated line count
	private int score;
	private int oldScore;
	private List<Tetromino> tetrominos;
	private Tetromino oldTetro;

	public Game() {
		init();
	}

	private void init() {
		gameOver = false;
		tetrominos = new ArrayList<>(LIMIT);
		score = 0;
		oldScore = score;
		lvl = 1;
		tcount = 1;
		lcount = 0;

		for (int i = 0; i < LIMIT; i++) {
			tetrominos.add(randomTetromino());
		}
		oldTetro = tetrominos.get(0);

		System.out.println("START\n");
	}

	private Tetromino randomTetromino() {
		String[] tetroModel = { "I", "J", "L", "O", "S", "T", "Z" };
		Tetromino newTetromino = new Tetromino(tetroModel[(int) (Math.random() * 7)]);
		// Tetromino newTetromino = new Tetromino("I");
		int randomRotation = (int) (Math.random() * 4);
		for (int i = 0; i < randomRotation; i++) {
			newTetromino.setMatrix(newTetromino.rotate());
		}
		return newTetromino;
	}

	void next() {
		gameOver = gameOver();
		if (!gameOver) {
			tcount++;
			ArrayList<Boolean> cr = completeRows();
			if (cr.contains(true)) {
				removeCompleteRows(cr);
				updateScore(cr);
			}
			tetrominos.remove(0);
			tetrominos.add(randomTetromino());
		} else {
			System.out.println("GAME OVER");
			System.out.println(tcount + " Tetrominos used");
			System.out.println(lcount + " lines cleared");
			System.out.println(score + " final score");
		}
	}

	void tetrominoToField() {

		int[][] tetromino = tetrominos.get(0).getMatrix();
		String color = tetrominos.get(0).getColor();
		int row = tetrominos.get(0).getRow();
		int dimension = tetromino.length;
		int column = tetrominos.get(0).getColumn();

		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				if (tetromino[r][c] == 1)
					field.getMatrix().get(row + r)[column + c] = color;
			}
		}
	}

	boolean gameOver() {
		if (tetrominos.get(0).getRow() == STARTROW && tetrominos.get(0).getColumn() == STARTCOLUMN)
			return true;
		return false;
	}

	boolean newScore() {
		if (score != oldScore) {
			oldScore = score;
			return true;
		}
		return false;
	}

	public boolean newTetro() {
		if (tetrominos.get(0) != oldTetro) {
			oldTetro = tetrominos.get(0);
			return true;
		}
		return false;
	}

	public boolean levelUp() {
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
		score += (POINTS[count] * lvl);
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

	void down() {
		score++;
		tetrominos.get(0).setRow(tetrominos.get(0).getRow() + 1);
	}
	
	void left() {
		tetrominos.get(0).setColumn(tetrominos.get(0).getColumn() - 1);
	}
	
	void right() {
		tetrominos.get(0).setColumn(tetrominos.get(0).getColumn() + 1);
	}
	
	void rotate() {
		int[][] rtmatrix = tetrominos.get(0).rotate();
		tetrominos.get(0).setMatrix(rtmatrix);
	}
	
	public List<Tetromino> getNextTetrominos() {
		return tetrominos;
	}

	public Playfield getField() {
		return field;
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
