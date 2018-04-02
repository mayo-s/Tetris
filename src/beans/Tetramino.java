package beans;

public class Tetramino {
	
//	public enum Tetramino {
//		O("o"), I("i"), J("j"), L("l"), S("s"), Z("z"), T("t");
//
//		private String type;
//
//		private Tetramino(String type) {
//			this.type = type;
//		}
//
//		public static Tetramino getTetramino(String type) {
//			for (Tetramino tetramino : values()) {
//				if (type.equalsIgnoreCase(tetramino.type)) {
//					return tetramino;
//				}
//			}
//			return null;
//		}
//	}
	
	private Block[] blocks;
	
	public Tetramino(Block b1, Block b2, Block b3, Block b4) {
		blocks = new Block[]{b1, b2, b3, b4};			
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}
	
}
