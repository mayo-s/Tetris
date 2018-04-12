package test;

public class Test {

	public Test() {
		
	}
	
	public void printMatrix(int[][] matrix) {

		for (int[] i : matrix) {
			for (int j : i) {
				System.out.print(" " + j + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
