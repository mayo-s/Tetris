package ai;

public class Node implements Comparable<Node> {
	private int id, rotation, frow, fcolumn, score;
	private Integer prevNode;

	public Node(int id, int rotation, int frow, int fcolumn, int score, Integer prevNode) {
		this.id = id;
		this.rotation = rotation;
		this.frow = frow;
		this.fcolumn = fcolumn;
		this.score = score;
		this.prevNode = prevNode;
	}

	@Override
	public int compareTo(Node o) {
		if (this.id > o.getId())
			return 1;
		else if (this.id < o.getId())
			return -1;

		return 0;
	}

	public int getId() {
		return id;
	}

	public int getRotation() {
		return rotation;
	}

	public int getFrow() {
		return frow;
	}

	public int getFcolumn() {
		return fcolumn;
	}

	public Integer getPrevNode() {
		return prevNode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}