package main;
/**
 * An object containing information about a given obstacle, namely they type of obstacle and its distance.
 * @author Ashan Parmar
 * @version 1.2
 */
public class Obstacle {
	public enum Type {
		WALL,
		FOOD,
		TAIL
	}
	private Type obstacleType;
	private int distance;
	
	/**
	 * Creates a new <code>Obstacle</code> instance containing the given data.
	 * @param newType - The type of the obstacle - must be an <code>Obstacle.Type</code> enum.
	 * @param newDistance - The distance from whatever point is being searched from to the obstacle.
	 */
	public Obstacle(Type newType, int newDistance) {
		obstacleType = newType;
		distance = newDistance;
	}

	public Type getObstacleType() {
		return obstacleType;
	}

	public int getDistance() {
		return distance;
	}
}
