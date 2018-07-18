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

	// returns a list of int - i.e. {3, 15, 8} ==> 3x rotate + final row 15 +
	// final column 8
	public int[] start(Playfield field, List<Tetromino> tetrominos) {
		this.field = field;
		this.fMatrix = field.getMatrix();
		this.tetrominos = tetrominos;
		int[][] tmatrix = tetrominos.get(0).getMatrix();
		int fRow = tetrominos.get(0).getRow();
		int fColumn = tetrominos.get(0).getColumn();
		topRow = topRow();

		TreeMap<Node, Integer> tree = buildTree(tmatrix, fRow, fColumn);
		Node bestLeaf = getBestNode(tree);
		int[] instructions = { bestLeaf.getRotation(), bestLeaf.getFrow(), bestLeaf.getFcolumn() };

		return instructions;
	}

	// find first occupied row (top to bottom)
	private int topRow() {
		for (int fRow = 2; fRow <= BOTTOM; fRow++) {
			for (int fColumn = LEFTBORDER; fColumn <= RIGHTBORDER; fColumn++) {
				if (fMatrix.get(fRow)[fColumn] != null)
					return fRow;
			}
		}
		return BOTTOM;
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

	private TreeMap<Node, Integer> buildTree(int[][] tmatrix, int startRow, int startColumn) {
		int nodeId = 0;
		TreeMap<Node, Integer> tree = new TreeMap<Node, Integer>();

		for (int rotation = 0; rotation < 4; rotation++) {
			int cRow = startRow;
			int left = startColumn;
			int right = startColumn;
			while (move.left(fMatrix, tmatrix, cRow, left)) {
				left--;
			}
			while (move.right(fMatrix, tmatrix, cRow, right)) {
				right++;
			}

			for (int cColumn = left; cColumn <= right; cColumn++) {
				cRow = startRow; // return to top of field
				// find final row
				while (move.down(fMatrix, tmatrix, cRow, cColumn)) {
					cRow++;
				}

				Integer score = buildScore(buildField(tmatrix, cRow, cColumn), cRow, cColumn, rotation);
				// System.out.println("Add to Tree: Node-" + nodeId + " rotation x" + rotation +
				// " row " + cRow + " column " + cColumn + " score " + score);
				tree.put(new Node(nodeId, rotation, cRow, cColumn, score, null), score);
				nodeId++;
			}

			tmatrix = rotate(tmatrix);
		}

		return tree;
	}

	private Node getBestNode(TreeMap<Node, Integer> tree) {
		Map.Entry<Node, Integer> maxScore = null;
		for (Map.Entry<Node, Integer> entry : tree.entrySet()) {
			if (maxScore == null || entry.getValue().compareTo(maxScore.getValue()) > 0) {
				maxScore = entry;
			}
		}

		return maxScore.getKey();
	}

	private ArrayList<String[]> buildField(int[][] tmatrix, int frow, int fcolumn) {

		ArrayList<String[]> tempMatrix = primitiveMatrix();
		int tdimension = tmatrix.length;

		for (int r = 0; r < tdimension; r++) {
			for (int c = 0; c < tdimension; c++) {
				if (tmatrix[r][c] == 1)
					tempMatrix.get(frow + r)[fcolumn + c] = "x";
			}
		}
		return tempMatrix;
	}

	private ArrayList<String[]> primitiveMatrix() {

		ArrayList<String[]> newMatrix = new ArrayList<String[]>();
		int row = 0;
		for (String[] r : this.fMatrix) {
			newMatrix.add(new String[this.field.getWIDTH()]);
			int column = 0;
			for (String c : r) {
				if (c != null) {
					newMatrix.get(row)[column] = "x";
				}
				column++;
			}
			row++;
		}
		return newMatrix;
	}

	private Integer buildScore(ArrayList<String[]> fmatrix, int fRow, int fColumn, int rotation) {
		int score = 0;

		// adds height
		score += (fRow - topRow) * 14;
		// need rotation
		score -= rotation * 10;
		// move away from center field
		if (fColumn < 3)
			score += (3 - fColumn) * 10;
		if (fColumn > 3)
			score += (fColumn - 3) * 10;
		// clears lines
		score += scoreClearLines(fmatrix, fRow) * 14;
		// covers gaps - negative score
		score -= scoreCoverGaps(fmatrix, fRow, fColumn) * 13;
		return score;
	}

	private int scoreClearLines(ArrayList<String[]> fmatrix, int row) {
		int linesComplete = 0;
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
					linesComplete++;
				}
			}
		}
		return linesComplete;
	}

	private int scoreCoverGaps(ArrayList<String[]> fmatrix, int row, int column) {
		int gaps = 0;

		for (int r = 0; r < 4; r++) {
			if (r + row <= BOTTOM) {
				for (int c = 0; c < 4; c++) {
					if (c + column >= LEFTBORDER && c + column <= RIGHTBORDER
							&& fmatrix.get(r + row)[c + column] != null) {
						int checkRow = r + row + 1;
						while (checkRow <= BOTTOM && fmatrix.get(checkRow)[c + column] == null) {
							gaps += 1;
							checkRow++;
						}
					}
				}
			}
		}
		return gaps;
	}
}