package main;
import java.awt.Point;

/**
 * A class holding a number of utility functions for Snake-playing AIs
 * @author Ashan Parmar
 * @version 1.4
 */
public class AIUtils {
	
	/**
	 * Performs a raycast from a given <code>Point</code> in a given direction and returns the first obstacle hit.
	 * @param origin - The <code>Point</code> to cast the ray from.
	 * @param rayDelta - The direction (relative to that of the snake) to cast the ray in.
	 * @param gridSize - The current size of the game grid.
	 * @param currentFood - The current <code>Point</code> the food is located at.
	 * @param currentSnake - The current <code>Snake</code> object.
	 * @return - An instance of <code>Obstacle</code> containing the type of obstacle and the distance of said obstacle from the origin of the ray.
	 */
	public static Obstacle raycast(Point origin, Snake.Direction rayDelta, int gridSize, Point currentFood, Snake currentSnake) {
		Point testLoc = (Point) origin.clone();
		int distance = 0;
		switch(rayDelta) {
			case RIGHT:
				rayDelta = shiftRight(currentSnake.getDirection());
				break;
			case LEFT:
				rayDelta = shiftLeft(currentSnake.getDirection());
				break;
			case UP:
				rayDelta = currentSnake.getDirection();
				break;
			case DOWN:
				rayDelta = shiftRight(shiftRight(currentSnake.getDirection()));
				break;
		}
		do {
			shiftForward(rayDelta, testLoc);
			distance++;
			if (currentSnake.isMeeting(testLoc)) {
				return new Obstacle(Obstacle.Type.TAIL,distance);
			}
			if (testLoc.distance(currentFood)==0) {
				return new Obstacle(Obstacle.Type.FOOD,distance);
			}
		} while (testLoc.x>-1 && testLoc.y>-1  && testLoc.x<gridSize && testLoc.y<gridSize);
		return new Obstacle(Obstacle.Type.WALL,distance);
	}
	
	/**
	 * Looks one space ahead from the given <code>Snake</code>'s head in the given direction for dangerous obstacles.
	 * @param rayDelta - The direction, relative to the <code>Snake</code>'s head, to check.
	 * @param gridSize - The current size of the game grid.
	 * @param currentSnake - The current <code>Snake</code> instance.
	 * @return - True if there is a wall or snake segment one square in the given relative direction away from the head of the given <code>Snake</code>.
	 */
	public static boolean glance(Snake.Direction rayDelta, int gridSize, Snake currentSnake) {
		Point testLoc = (Point) currentSnake.getHead().clone();
		switch(rayDelta) {
			case RIGHT:
				rayDelta = shiftRight(currentSnake.getDirection());
				break;
			case LEFT:
				rayDelta = shiftLeft(currentSnake.getDirection());
				break;
			case UP:
				rayDelta = currentSnake.getDirection();
				break;
			case DOWN:
				rayDelta = shiftRight(shiftRight(currentSnake.getDirection()));
				break;
		}
		shiftForward(rayDelta, testLoc);
		if (!(testLoc.x>-1 && testLoc.y>-1  && testLoc.x<gridSize && testLoc.y<gridSize)) {
			return true;
		}
		if (currentSnake.isMeeting(testLoc)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Looks one space ahead from the given <code>Point</code> in the given direction for dangerous obstacles.
	 * @param rayDelta - The direction to check.
	 * @param gridSize - The current size of the game grid.
	 * @param currentSnake - The current <code>Snake</code> instance.
	 * @param origin - The <code>Point</code> to check from.
	 * @return - True if there is a wall or snake segment one square in the given direction away from the given <code>Point</code>.
	 */
	public static boolean glance(Snake.Direction rayDelta, int gridSize, Snake currentSnake, Point origin) {
		Point testLoc = (Point) origin.clone();
		shiftForward(rayDelta, testLoc);
		if (!(testLoc.x>-1 && testLoc.y>-1  && testLoc.x<gridSize && testLoc.y<gridSize)) {
			return true;
		}
		if (currentSnake.isMeeting(testLoc)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Shift the given direction 90 degrees left.
	 * @param currentDirection - The direction to shift.
	 * @return - The direction after it has been shifted.
	 */
	public static Snake.Direction shiftLeft(Snake.Direction currentDirection) {
		switch(currentDirection) {
			case RIGHT:
				return Snake.Direction.UP;
			case LEFT:
				return Snake.Direction.DOWN;
			case UP:
				return Snake.Direction.LEFT;
			case DOWN:
				return Snake.Direction.RIGHT;
		}
		return currentDirection;
	}
	
	/**
	 * Shift the given direction 90 degrees right.
	 * @param currentDirection - The direction to shift.
	 * @return - The direction after it has been shifted.
	 */
	public static Snake.Direction shiftRight(Snake.Direction currentDirection) {
		switch(currentDirection) {
			case RIGHT:
				return Snake.Direction.DOWN;
			case LEFT:
				return Snake.Direction.UP;
			case UP:
				return Snake.Direction.RIGHT;
			case DOWN:
				return Snake.Direction.LEFT;
		}
		return currentDirection;
	}
	
	/**
	 * Shift the given <code>Point</code> 1 unit in the given direction
	 * @param currentDirection - The given direction to shift the point in.
	 * @param currentPoint - The given <code>Point</code> to shift from.
	 */
	public static void shiftForward(Snake.Direction currentDirection, Point currentPoint) {
		switch(currentDirection) {
			case RIGHT:
				currentPoint.translate(1, 0);
				break;
			case LEFT:
				currentPoint.translate(-1, 0);
				break;
			case UP:
				currentPoint.translate(0, -1);
				break;
			case DOWN:
				currentPoint.translate(0, 1);
				break;
		}
	}
	
	/**
	 * Compare two directions.
	 * @param direction1 - The direction to compare from.
	 * @param direction2 - The direction to compare to.
	 * @return - The direction that <code>direction1</code> must be rotated to reach <code>direction2</code>.
	 */
	public static Snake.Direction compareDirections(Snake.Direction direction1, Snake.Direction direction2) {
		if (direction1 == direction2) {
			return Snake.Direction.UP;
		}
		
		if (shiftRight(direction1) == direction2) {
			return Snake.Direction.RIGHT;
		}
		
		if (shiftLeft(direction1) == direction2) {
			return Snake.Direction.LEFT;
		}
		
		return Snake.Direction.DOWN;
	}
	
	/**
	 * Calculates the Manhattan distance between two <code>Point</code>s.
	 * @param begin - The <code>Point</code> to begin at.
	 * @param end - The <code>Point</code> to end at.
	 * @return - The Manhattan distance between the two given <code>Point</code>s.
	 */
	public static double manhattanDistance(Point begin, Point end) {
		return Math.abs(begin.x-end.x)+Math.abs(begin.y-end.y);
	}
}
