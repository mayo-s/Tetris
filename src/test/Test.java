package test;

import java.util.ArrayList;

public class Test {

	public Test() {

	}

	/**
	 * This will print out your given 2D matrix
	 * @param object can be int[][] or ArrayList<ArrayList<Boolean>>
	 * @param type specify your input: 1 for int[][] or for ArrayList<ArrayList<Boolean>>
	 * 
	 */
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
			@SuppressWarnings("unchecked")
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
