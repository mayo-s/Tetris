package actions;

import beans.Block;
import beans.Tetramino;

public class TetraminoAction {

	private Tetramino[] tetraminos;
	
	public void createTetraminos() {
		
		tetraminos = new Tetramino[7];		
		// O
		tetraminos[0] = new Tetramino(new Block(0, 4), new Block(0, 5), new Block(1, 4), new Block(1, 5));
		// I
		tetraminos[1] = new Tetramino(new Block(1, 4), new Block(1, 5), new Block(1, 6), new Block(1, 7));
		// J
		tetraminos[2] = new Tetramino(new Block(0, 4), new Block(1, 4), new Block(1, 5), new Block(1, 6));
		// L
		tetraminos[3] = new Tetramino(new Block(1, 4), new Block(1, 5), new Block(1, 6), new Block(0, 6));
		// S
		tetraminos[4] = new Tetramino(new Block(0, 4), new Block(0, 5), new Block(1, 5), new Block(1, 6));
		// Z
		tetraminos[5] = new Tetramino(new Block(1, 4), new Block(1, 5), new Block(0, 5), new Block(0, 6));
		// T
		tetraminos[6] = new Tetramino(new Block(0, 4), new Block(0, 5), new Block(1, 5), new Block(0, 6));
					
	}
	
	public Tetramino chooseTetramino() {
		int random = (int) (Math.random() * 7);
			
		return tetraminos[random];		
	}

	
}
