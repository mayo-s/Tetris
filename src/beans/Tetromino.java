package beans;

public class Tetromino {
	
//	public enum Tetromino {
//		O("o"), I("i"), J("j"), L("l"), S("s"), Z("z"), T("t");
//
//		private String type;
//
//		private Tetromino(String type) {
//			this.type = type;
//		}
//
//		public static Tetromino getTetromino(String type) {
//			for (Tetromino tetromino : values()) {
//				if (type.equalsIgnoreCase(tetromino.type)) {
//					return tetromino;
//				}
//			}
//			return null;
//		}
//	}
	
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
