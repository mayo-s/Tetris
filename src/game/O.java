package game;

import java.util.List;

public class O  implements Tetromino{
	
	List<int[][]> tetromino;
	
	public O(){
		tetromino.add(new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}});
	}	
	
	@Override
	public List<int[][]> getTetromino() {
		// TODO Auto-generated method stub
		return null;
	}

}
