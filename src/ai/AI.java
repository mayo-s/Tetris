package ai;

import java.util.ArrayList;
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
	private final int BORDER = field.getWIDTH();
	private ArrayList<String[]> fMatrix;
	private List<Tetromino> tetrominos;

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
		
		return buildTree();
	}

	private boolean finalPosition(int[][] tmatrix, int fRow, int fColumn) {
		if (move.down(field.getMatrix(), tmatrix, fRow, fColumn))
			return false; // not final position
		for (int tRow = 0; tRow < tmatrix.length; ++tRow) {
			if (fRow + tRow < BOTTOM) {
				for (int tColumn = 0; tColumn < tmatrix.length; ++tColumn) {
					if (tmatrix[tRow][tColumn] == 1
							&& (fColumn + tColumn >= BORDER || fMatrix.get(fRow + tRow)[fColumn + tColumn] != null)) {
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

	// find first occupied row (top to bottom)
	private int topRow() {
		for (int fRow = 2; fRow < BOTTOM; fRow++) {
			for (int fColumn = 0; fColumn < BORDER; fColumn++) {
				if (fMatrix.get(fRow)[fColumn] != null)
					return fRow;
			}
		}
		return BOTTOM - 1;
	}

	// private int findLowestRow(ArrayList<Tetromino> options) {
	// int lowest = 0;
	// int index = 0;
	// for(int i = 0; i < options.size(); i++) {
	// Tetromino t = options.get(i);
	// if(t.getRow() < lowest) {
	// lowest = t.getRow();
	// index = i;
	// }
	// }
	// return index;
	// }

	private int[] buildTree() {
		int nodeId = 0;
		TreeSet<Node> tree = new TreeSet<Node>();
				
		Tetromino t = tetrominos.get(0);
		int[][] tmatrix = t.getMatrix();
		Test test = new Test();
		test.printTetromino(tmatrix);
		// String tcolor = t.getColor();

		for (int fRow = topRow() - 4; fRow < BOTTOM; fRow++) {
			for (int fColumn = 0; fColumn < BORDER; fColumn++) {
				for (int rotation = 0; rotation < 4; rotation++) {
					if (finalPosition(tmatrix, fRow, fColumn)) {
						tree.add(new Node(nodeId, rotation, fRow, fColumn, buildScore(field, fRow, fColumn, tmatrix), null));
						nodeId++;
					}
					tmatrix = rotate(tmatrix);
				}
			}
		}

		System.out.println(tree.size() + " tree elements");

		// just checking
		Iterator<Node> itTree = tree.iterator();
		while (itTree.hasNext()) {
			Node it = itTree.next();
			System.out.println("id " + it.getId() + " rotation " + it.getRotation() + " row: " + it.getFrow()
					+ " column: " + it.getFcolumn() + " score: " + it.getScore());
		}
		
		int[] instructions = {tree.last().getRotation(), tree.last().getFcolumn()};
		return instructions;
	}

	private int buildScore(Playfield field, int fRow, int fColumn, int[][] tmatrix) {
		int score = 0;
		
		// adds height - return int (how much)
		// clears lines - return int (how many)
		// covers gaps - return int (how many)
		
		
		return score;
	}

	private void alphaBeta() {

	}

}