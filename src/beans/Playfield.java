package beans;

import java.util.ArrayList;
import java.util.Arrays;

public class Playfield {

	private final int WIDTH = 10;
	private final int HEIGHT = 22;
	private int[][] fields;
	private ArrayList<Block> blocks;
	
	public Playfield() {
		setFields(new int[WIDTH][HEIGHT]);
		setBlocks(new ArrayList<Block>());
		
		for (int[] f : fields) Arrays.fill(f, 0);
	}

	public int[][] getFields() {
		return fields;
	}

	public void setFields(int[][] fields) {
		this.fields = fields;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	
}
