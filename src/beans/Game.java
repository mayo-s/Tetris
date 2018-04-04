package beans;

public class Game {
	
	private Playfield field;
	private int score;
	private String player;
	
	public Game() {
		setField(new Playfield());
		setScore(0);
	}

	public Playfield getField() {
		return field;
	}

	public void setField(Playfield field) {
		this.field = field;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
}
