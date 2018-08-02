package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Move;
import game.Playfield;
import game.Tetromino;

public class AI {
	private Move move;
	private Playfield field = new Playfield();
	private final int BOTTOM = field.getHEIGHT() - 1;
	private final int LEFTBORDER = 0;
	private final int RIGHTBORDER = field.getWIDTH() - 1;
	private List<Tetromino> tetrominos;

	public AI(Move move) {
		this.move = move;
	}

	/**
	 * 
	 * @param field
	 *            current Playfield
	 * @param tetrominos
	 *            list of current and following Tetrominos
	 * @return a list of int - i.e. {3, 15, 8} ==> 3x rotate + final row 15 + final column 8
	 */
	public int[] start(Playfield field, List<Tetromino> tetrominos) {

		this.field = field;
		this.tetrominos = tetrominos;

		Map<Node, Integer> tree = findBestPosition(field.getMatrix(), 0);
		Node bestLeaf = getBestNode(tree);
		int[] instructions = { bestLeaf.getRotation(), bestLeaf.getFrow(), bestLeaf.getFcolumn() };

		return instructions;
	}

	/**
	 * @param fmatrix
	 *            current field matrix to check
	 * @param tetro
	 *            current Tetromino to check
	 * @param tetroListPosition
	 *            position in List of Tetrominos
	 * @return TreeMap<Node, Integer> with instructions (rotation, final field row
	 *         and column) and score
	 */
	private Map<Node, Integer> findBestPosition(ArrayList<String[]> fmatrix, int tetroListPosition) {
		Tetromino tetro = tetrominos.get(tetroListPosition);
		int[][] tmatrix = tetro.getMatrix();
		int fRow = tetro.getRow();
		int fColumn = tetro.getColumn();

		Map<Node, Integer> tree = buildTree(fmatrix, tmatrix, fRow, fColumn);

		// BUILD SUBTREE
		if (tetroListPosition + 1 < this.tetrominos.size()) {
			tetroListPosition++;

			for (Map.Entry<Node, Integer> entry : tree.entrySet()) {
				Node currLeaf = entry.getKey();
				int[][] rmatrix = tetro.copy();
				int rotation = currLeaf.getRotation();
				for (int i = 0; i < rotation; i++) {
					rmatrix = rotateMatrix(rmatrix);
				}

				ArrayList<String[]> nextField = buildField(fmatrix, rmatrix, currLeaf.getFrow(), currLeaf.getFcolumn());

				Map<Node, Integer> subTree = findBestPosition(nextField, tetroListPosition);
				Node bestLeaf = getBestNode(subTree);
				int newScore = currLeaf.getScore() + bestLeaf.getScore();
				currLeaf.setScore(newScore);
				entry.setValue(newScore);
			}
		}
		return tree;
	}

	private Map<Node, Integer> buildTree(ArrayList<String[]> fmatrix, int[][] tmatrix, int startRow, int startColumn) {
		int leafId = 0;
		int topRow = topRow(fmatrix);
		Map<Node, Integer> tree = new HashMap<Node, Integer>();

		for (int rotation = 0; rotation < 4; rotation++) {
			int cRow = startRow;
			int left = startColumn;
			int right = startColumn;

			while (move.left(fmatrix, tmatrix, cRow, left, false)) {
				left--;
			}
			while (move.right(fmatrix, tmatrix, cRow, right, false)) {
				right++;
			}

			for (int cColumn = left; cColumn <= right; cColumn++) {
				cRow = startRow; // return to top of field
				// find final row
				while (move.down(fmatrix, tmatrix, cRow, cColumn, false)) {
					cRow++;
				}

				Integer score = buildScore(buildField(fmatrix, tmatrix, cRow, cColumn), topRow, cRow, cColumn,
						rotation);
//				 System.out.println("Add to Tree: Node-" + nodeId + " rotation x" + rotation +" row " + cRow  + " column " + cColumn + " score " + score);
				tree.put(new Node(leafId, rotation, cRow, cColumn, score), score);
				leafId++;
			}
			tmatrix = rotateMatrix(tmatrix);
		}
		return tree;
	}

	// find highest occupied row (top to bottom)
	private int topRow(ArrayList<String[]> matrix) {
		for (int fRow = 2; fRow <= BOTTOM; fRow++) {
			for (int fColumn = LEFTBORDER; fColumn <= RIGHTBORDER; fColumn++) {
				if (matrix.get(fRow)[fColumn] != null)
					return fRow;
			}
		}
		return BOTTOM;
	}

	private Integer buildScore(ArrayList<String[]> fmatrix, int topRow, int fRow, int fColumn, int rotation) {
		int score = 0;

		// adds height
		score += Math.pow(fRow, 2);
		// penalty for adding height
		 int heightPenalty = (int) (Math.pow(fRow - topRow, 2) * Math.signum(fRow - topRow));
		 score += heightPenalty;
		// move away from center field
		if (fColumn < 3)
			score += Math.pow(2, (3 - fColumn));
		if (fColumn > 3)
			score += Math.pow(2, (fColumn - 3));
		// clears lines
		int lines = countClearableLines(fmatrix, fRow);
		score += Math.pow(20, lines);
		// covers gaps - negative score
		score -= countCoveredGaps(fmatrix, fRow, fColumn) * 20;

		return score;
	}

	private int countClearableLines(ArrayList<String[]> fmatrix, int row) {
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

	private int countCoveredGaps(ArrayList<String[]> fmatrix, int row, int column) {
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

	private Node getBestNode(Map<Node, Integer> tree) {
		Map.Entry<Node, Integer> maxScore = null;
		for (Map.Entry<Node, Integer> entry : tree.entrySet()) {
			if (maxScore == null || entry.getValue().compareTo(maxScore.getValue()) > 0) {
				maxScore = entry;
			}
		}
		return maxScore.getKey();
	}

	private ArrayList<String[]> buildField(ArrayList<String[]> fmatrix, int[][] tmatrix, int frow, int fcolumn) {
		ArrayList<String[]> tempMatrix = primitiveMatrix(fmatrix);
		int tdimension = tmatrix.length;

		for (int r = 0; r < tdimension; r++) {
			for (int c = 0; c < tdimension; c++) {
				if (tmatrix[r][c] == 1)
					tempMatrix.get(frow + r)[fcolumn + c] = "x";
			}
		}
		return tempMatrix;
	}

	private ArrayList<String[]> primitiveMatrix(ArrayList<String[]> fmatrix) {

		ArrayList<String[]> newMatrix = new ArrayList<String[]>();
		int row = 0;
		for (String[] r : fmatrix) {
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

	private int[][] rotateMatrix(int[][] tmatrix) {
		int size = tmatrix.length;
		int[][] rotated = new int[size][size];

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				rotated[i][j] = tmatrix[size - j - 1][i];
			}
		}
		return rotated;
	}
}