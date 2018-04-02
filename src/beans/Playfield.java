package beans;

public class Playfield {

	private final int WIDTH = 10;
	private final int HEIGHT = 22;
	private int[][] field;
	
	public Playfield() {
		setField(new int[WIDTH][HEIGHT]);
	}

	public int[][] getField() {
		return field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}
	
}
