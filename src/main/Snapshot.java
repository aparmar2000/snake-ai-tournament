package main;
import java.awt.Point;
import java.util.LinkedList;

/**
 * A record of all vital game elements (score, grid size, food location, and <code>Snake</code> segments) for a single step
 * @author Ashan Parmar
 * @version 1.1
 */
public class Snapshot {
	private int score;
	private int gridSize;
	private Point food;
	private LinkedList<Point> segments;
	
	/**
	 * Creates a new snapshot of the given game data.
	 * @param currentScore - The current game score.
	 * @param gridSize - The size of the current game grid
	 * @param foodPoint - The <code>Point</code> the food is currently located at.
	 * @param currentSnake - The current <code>Snake</code> object.
	 */
	@SuppressWarnings("unchecked")
	public Snapshot(int currentScore, int gridSize, Point foodPoint, Snake currentSnake) {
		score = currentScore;
		this.gridSize = gridSize;
		food = (Point) foodPoint.clone();
		segments = (LinkedList<Point>) currentSnake.getSegments().clone();
	}
	
	public int getScore() {
		return score;
	}

	public int getGridSize() {
		return gridSize;
	}

	public Point getFood() {
		return food;
	}
	
	public LinkedList<Point> getSegments() {
		return segments;
	}
}
