package ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import game.Move;
import game.Playfield;
import game.Tetromino;
import test.Test;

public class AI {
	private Move move = new Move();
	private Playfield field = new Playfield();
	private final int BOTTOM = field.getHEIGHT();
	private final int RIGHTBORDER = field.getWIDTH();
	private final int LEFTBORDER = 0;
	private ArrayList<String[]> fMatrix;
	private List<Tetromino> tetrominos;
	private int topRow = 2;

	public AI() {

	}

	/**
	 * @param field
	 *            current Playfield
	 * @param tetrominos
	 *            list of current and following Tetrominos
	 */

	// should return a list of int - i.e. {3, 15, 8} ==> 3x rotate + final row 15 +
	// final column 8
	public int[] start(Playfield field, List<Tetromino> tetrominos) {
		this.field = field;
		this.fMatrix = field.getMatrix();
		this.tetrominos = tetrominos;
		int[][] tmatrix = tetrominos.get(0).getMatrix();
		int fRow = tetrominos.get(0).getRow();
		int fColumn = tetrominos.get(0).getColumn();

		return buildTree(tmatrix, fRow, fColumn);
	}

	private boolean finalPosition(int[][] tmatrix, int fRow, int fColumn) {
		if (move.down(field.getMatrix(), tmatrix, fRow, fColumn))
			return false; // not final position
		for (int tRow = 0; tRow < tmatrix.length; ++tRow) {
			if (fRow + tRow < BOTTOM) {
				for (int tColumn = 0; tColumn < tmatrix.length; ++tColumn) {
					if (tmatrix[tRow][tColumn] == 1 && (fColumn + tColumn >= RIGHTBORDER
							|| fMatrix.get(fRow + tRow)[fColumn + tColumn] != null)) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private int[][] rotate(int[][] tmatrix) {
		int size = tmatrix.length;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tmatrix[size - j - 1][i];
			}
		}
		return rotated;
	}

	private int[] buildTree(int[][] tmatrix, int fRow, int fColumn) {
		int nodeId = 0;
		TreeSet<Node> tree = new TreeSet<Node>();
		int cRow = fRow;
		int cColumn = fColumn;

		Test test = new Test();
		test.printTetromino(tmatrix);

		for (int rotation = 0; rotation < 4; rotation++) {
			while (move.left(fMatrix, tmatrix, cRow, cColumn)) {
				cColumn--;
			}

			while (move.right(fMatrix, tmatrix, cRow, cColumn)) {
				while (move.down(fMatrix, tmatrix, cRow, cColumn)) {
					cRow++;
				}
				tree.add(new Node(nodeId, rotation, cRow, cColumn, buildScore(cRow, cColumn, tmatrix), null));
				nodeId++;
				cColumn++;
			}
			tmatrix = rotate(tmatrix);
			cRow = fRow;
			cColumn = fColumn;

		}

		System.out.println(tree.size() + " tree elements");

		// just checking
		Iterator<Node> itTree = tree.iterator();
		while (itTree.hasNext()) {
			Node it = itTree.next();
			System.out.println("id " + it.getId() + " rotation " + it.getRotation() + " row: " + it.getFrow()
					+ " column: " + it.getFcolumn() + " score: " + it.getScore());
		}

		if (tree.isEmpty())
			tree.add(new Node(nodeId, 0, 18, -2, 0, null));
		Node bestLeaf = tree.last();
		int[] instructions = { bestLeaf.getRotation(), bestLeaf.getFrow(), bestLeaf.getFcolumn() };
		return instructions;
	}

	private int buildScore(int fRow, int fColumn, int[][] tmatrix) {
		int score = 0;

		Playfield field = new Playfield();
		ArrayList<String[]> fmatrix = field.getMatrix();
		int dimension = tmatrix.length;
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				if (tmatrix[r][c] == 1)
					fmatrix.get(fRow + r)[fColumn + c] = "x";
			}
		}

		// adds height
		score += scoreHeight(fmatrix, fRow);
		// clears lines
		score += scoreLines(fmatrix, fRow);
		// covers gaps
		score += scoreGaps();
		System.out.println("Build score " + score);
		return score;
	}

	private int scoreLines(ArrayList<String[]> fmatrix, int row) {
		int score = 0;
		for (int r = row; r <= (row + 3); r++) {
			boolean gap = false;
			if (r < fmatrix.size()) {
				for (String cell : fmatrix.get(r)) {
					if (cell == null) {
						gap = true;
						break;
					}
				}
				if (!gap) {
					score += 100;
				}
			}
		}
		return score;
	}

	private int scoreHeight(ArrayList<String[]> fmatrix, int fRow) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int scoreGaps() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void alphaBeta() {

	}

}