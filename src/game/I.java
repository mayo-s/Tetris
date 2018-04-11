package game;

import java.util.List;

public class I implements Tetromino{
	
	List<int[][]> tetromino;
	
	public I(){
		tetromino.add(new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}});
		tetromino.add(new int[][]{
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0}});
	}

	@Override
	public List<int[][]> getTetromino() {
		return tetromino;
	}
	
}
