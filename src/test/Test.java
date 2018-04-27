package test;

import java.util.ArrayList;

public class Test {

	public Test() {

	}

	public void printMatrix(Object object, int type) {

		if (type == 1) {
			int[][] matrix = (int[][]) object;
			for (int[] i : matrix) {
				for (int j : i) {
					System.out.print(" " + j + " ");
				}
				System.out.println();
			}
			System.out.println();
		}else if (type == 2) {
			ArrayList<ArrayList<Boolean>> matrix = (ArrayList<ArrayList<Boolean>>) object;
			for (ArrayList<Boolean> i : matrix) {
				for (boolean b : i) {
					int j = 0;
					if(b) j = 1;
					System.out.print(" " + j + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

}
