package test;

import java.util.ArrayList;

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
