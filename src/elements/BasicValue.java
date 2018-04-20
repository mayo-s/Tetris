package elements;

public class BasicValue {

	int fieldWidth;
	int fieldHeight;
	int tetrominoWidth;
	int tetrominoHeight;
	String player;
	String controls;
	int score;
	int lvl;
	
	public BasicValue() {
		fieldWidth = 10;
		fieldHeight = 22;
		tetrominoWidth = 4;
		tetrominoHeight = 4;
		player = "DummyName";
		controls = "P - Play/Pause\n^ - Rotate\n< - move left\n> - move right\nv - drop";
		score = 0;
		lvl = 1;
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public int getFieldHeight() {
		return fieldHeight;
	}

	public int getTetrominoWidth() {
		return tetrominoWidth;
	}

	public int getTetrominoHeight() {
		return tetrominoHeight;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getControls() {
		return controls;
	}

	public int getScore() {
		return score;
	}

	public int getLvl() {
		return lvl;
	}
	
	
}
