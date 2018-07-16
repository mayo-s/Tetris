package ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import game.Move;
import game.Playfield;
import game.Tetromino;

public class AI {
	private Move move = new Move();
	private Playfield field = new Playfield();
	private final int BOTTOM = field.getHEIGHT() - 1;
	private final int LEFTBORDER = 0;
	private final int RIGHTBORDER = field.getWIDTH() - 1;
	private ArrayList<String[]> fMatrix;
	private List<Tetromino> tetrominos;
	private int topRow;
	private int highScore;

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
		topRow = topRow();

		return buildTree(tmatrix, fRow, fColumn);
	}

	// find first occupied row (top to bottom)
	private int topRow() {
		for (int fRow = 2; fRow < BOTTOM; fRow++) {
			for (int fColumn = LEFTBORDER; fColumn < RIGHTBORDER; fColumn++) {
				if (fMatrix.get(fRow)[fColumn] != null)
					return fRow;
			}
		}
		return BOTTOM - 1;
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
		TreeMap<Node, Integer> tree = new TreeMap<Node, Integer>();
		int cRow = fRow;
		int cColumn = fColumn;

		for (int rotation = 0; rotation < 4; rotation++) {
			while (move.left(fMatrix, tmatrix, cRow, cColumn)) {
				cColumn--;
			}

			while (move.right(fMatrix, tmatrix, cRow, cColumn)) {
				while (move.down(fMatrix, tmatrix, cRow, cColumn)) {
					cRow++;
				}
				Integer score = buildScore(cRow, cColumn, rotation, tmatrix);
				tree.put(new Node(nodeId, rotation, cRow, cColumn, null), score);
				nodeId++;
				cColumn++;
			}
			tmatrix = rotate(tmatrix);
			cRow = fRow;
			cColumn = fColumn;

		}

		// just checking
		// System.out.println(tree.size() + " tree elements");
		// for (Map.Entry<Node, Integer> entry : tree.entrySet()) {
		// Node node = entry.getKey();
		// Integer score = entry.getValue();
		//
		// System.out.println("id " + node.getId() + " rotation " + node.getRotation() +
		// " row: " + node.getFrow()
		// + " column: " + node.getFcolumn() + " score: " + score);
		// }

		Node bestLeaf = getBestLeaf(tree);
		int[] instructions = { bestLeaf.getRotation(), bestLeaf.getFrow(), bestLeaf.getFcolumn() };
		return instructions;
	}

	private Node getBestLeaf(TreeMap<Node, Integer> tree) {
		Map.Entry<Node, Integer> maxScore = null;

		for (Map.Entry<Node, Integer> entry : tree.entrySet()) {
			if (maxScore == null || entry.getValue().compareTo(maxScore.getValue()) > 0) {
				maxScore = entry;
			}
		}

		return maxScore.getKey();
	}

	private int buildScore(int fRow, int fColumn, int rotation, int[][] tmatrix) {
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
		score -= (fRow - topRow) * 100;
		// need rotation
		score -= rotation * 10;
		// move away from center field
		if (fColumn < 3)
			score += (3 - fColumn) * 10;
		if (fColumn > 3)
			score += (fColumn - 3) * 10;
		// clears lines
		score += scoreLines(fmatrix, fRow);
		// covers gaps
		score += scoreGaps();
		// System.out.println("Build score " + score);
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
					score += 110;
				}
			}
		}
		return score;
	}

	private int scoreGaps() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void alphaBeta() {

	}

}