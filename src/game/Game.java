package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elements.Playfield;
import elements.Tetromino;

public class Game {

	private List<Tetromino> tetrominos;
	private Playfield field = new Playfield();
	private final int LIMIT = 2; // current + next
	private final int STARTROW = 0;
	private final int STARTCOLUMN = 3;
	private final int[] POINTS = { 0, 100, 300, 500, 800 };
	// field outline
	private final int LEFTEDGE = 0;
	private final int RIGHTEDGE = field.getWIDTH() - 1;
	private final int BOTTOM = field.getHEIGHT() - 1;

	private boolean gameOver;
	private int lvl;
	private int tcount; // tetromino count
	private int lcount; // eliminated line count
	private int score;
	private int oldScore;
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
			tetrominos.remove(0);
			tetrominos.add(randomTetromino());
		} else {
			System.out.println("GAME OVER/n");
			System.out.println(tcount + " Tetrominos used");
			System.out.println(lcount + " lines cleared");
			System.out.println(score + " final score");
		}
	}

	private void tetrominoToField() {

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

	boolean newTetro() {
		if (tetrominos.get(0) != oldTetro) {
			oldTetro = tetrominos.get(0);
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

	boolean moveDown() {

		ArrayList<String[]> fmatrix = field.getMatrix();
		int[][] tmatrix = tetrominos.get(0).getMatrix();
		int row = tetrominos.get(0).getRow();
		int column = tetrominos.get(0).getColumn();

		for (int r = 0; r < tmatrix.length; r++) {
			for (int c = 0; c < tmatrix.length; c++) {
				if (tmatrix[r][c] == 1 && (row + r >= BOTTOM || fmatrix.get(row + r + 1)[column + c] != null)) {
					tetrominoToField();
					next();
					return false;
				}
			}
		}
		score++;
		tetrominos.get(0).setRow(row + 1);
		return true;
	}

	boolean moveLeft() {
		ArrayList<String[]> fmatrix = field.getMatrix();
		int[][] tmatrix = tetrominos.get(0).getMatrix();
		int row = tetrominos.get(0).getRow();
		int column = tetrominos.get(0).getColumn();
		int tdimension = tmatrix.length;

		for (int c = 0; c < tdimension; c++) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (column + c <= LEFTEDGE || fmatrix.get(row + r)[column + c - 1] != null)) {

					return false;
				}
			}
		}
		tetrominos.get(0).setColumn(column - 1);
		return true;
	}

	boolean moveRight() {
		ArrayList<String[]> fmatrix = field.getMatrix();
		int[][] tmatrix = tetrominos.get(0).getMatrix();
		int row = tetrominos.get(0).getRow();
		int column = tetrominos.get(0).getColumn();
		int tdimension = tmatrix.length;

		for (int c = tdimension - 1; c >= 0; c--) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (column + c >= RIGHTEDGE || fmatrix.get(row + r)[column + c + 1] != null)) {
					return false;
				}
			}
		}
		tetrominos.get(0).setColumn(column + 1);
		return true;
	}

	boolean moveRotate() {
		ArrayList<String[]> fmatrix = field.getMatrix();
		int[][] tmatrix = rotateTetromino(tetrominos.get(0).getMatrix());
		int row = tetrominos.get(0).getRow();
		int column = tetrominos.get(0).getColumn();
		int tdimension = tmatrix.length;

		for (int c = 0; c < tdimension; c++) {
			for (int r = 0; r < tdimension; r++) {
				if (tmatrix[r][c] == 1 && (column + c >= RIGHTEDGE || column + c <= LEFTEDGE
						|| fmatrix.get(row + r)[column + c] != null)) {
					return false;
				}
			}
		}
		tetrominos.get(0).setMatrix(tmatrix);
		return true;
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
