package beans;

public class Tetromino {
	
	private Block[] blocks;
	
	public Tetromino(Block b1, Block b2, Block b3, Block b4) {
		blocks = new Block[]{b1, b2, b3, b4};			
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}
	
}
