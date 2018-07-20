package test;

import java.util.ArrayList;
import java.util.List;

import game.Tetromino;

public class Test {

	public Test() {

	}

	public void printTetromino(int[][] matrix) {

		for (int[] i : matrix) {
			for (int j : i) {
				System.out.print(" " + j + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printAllTetrominos(List<Tetromino> list) {
		int dimension = list.get(0).getMatrix().length;
		for (int r = 0; r < dimension; r++) {
			for (Tetromino t : list) {
				int[] row = t.getMatrix()[r];
				for (int c : row) {
					System.out.print(" " + c + " ");
				}
				System.out.print("    <    ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printField(ArrayList<String[]> matrix) {
		for (String[] i : matrix) {
			for (String cell : i) {
				if (cell == null)
					System.out.print(" 0 ");
				else
					System.out.print(" 1 ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
