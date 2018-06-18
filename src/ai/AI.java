package ai;

import java.util.ArrayList;
import java.util.List;

import elements.Playfield;
import elements.Tetromino;

public class AI {

	Playfield pf;
	List<Tetromino> tetrominos;

	/**
	 * @param pf
	 *            current field
	 * @param tetrominos
	 *            list of current and following Tetrominos
	 */
	public AI(Playfield pf, List<Tetromino> tetrominos) {
		this.pf = pf;
		this.tetrominos = tetrominos;

//		evaluate();
	}

//	private void evaluate() {
//		createGraph();
//	}

	// find lowest accessible gap
//	private void createGraph() {
//		int row = emptyRow();
//		int column = 0;
//		ArrayList<String[]> matrix = pf.getMatrix();
//
//	}

//	private int emptyRow() {
//
//		for (String[] row : pf.getMatrix()) {
//			if (row.contains(true))
//				return row.indexOf(row);
//		}
//
//		return 0;
//	}

}