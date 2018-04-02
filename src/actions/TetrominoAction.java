package actions;

import beans.Block;
import beans.Tetromino;

public class TetrominoAction {

	private Tetromino[] tetrominos;
	
	public void createTetrominos() {
		
		tetrominos = new Tetromino[7];		
		// O
		tetrominos[0] = new Tetromino(new Block(0, 4), new Block(0, 5), new Block(1, 4), new Block(1, 5));
		// I
		tetrominos[1] = new Tetromino(new Block(1, 4), new Block(1, 5), new Block(1, 6), new Block(1, 7));
		// J
		tetrominos[2] = new Tetromino(new Block(0, 4), new Block(1, 4), new Block(1, 5), new Block(1, 6));
		// L
		tetrominos[3] = new Tetromino(new Block(1, 4), new Block(1, 5), new Block(1, 6), new Block(0, 6));
		// S
		tetrominos[4] = new Tetromino(new Block(0, 4), new Block(0, 5), new Block(1, 5), new Block(1, 6));
		// Z
		tetrominos[5] = new Tetromino(new Block(1, 4), new Block(1, 5), new Block(0, 5), new Block(0, 6));
		// T
		tetrominos[6] = new Tetromino(new Block(0, 4), new Block(0, 5), new Block(1, 5), new Block(0, 6));
					
	}
	
	public Tetromino chooseTetromino() {
		int random = (int) (Math.random() * 7);
			
		return tetrominos[random];		
	}

	
}
