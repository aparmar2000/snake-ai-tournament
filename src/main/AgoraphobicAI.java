package main;
import java.awt.Point;

/**
 * A <code>SnakeAI</code> which takes whichever direction is a straight line to the food.
 * If there is no such direction, it tries to get as close as possible to obstacles without dying.
 * @author Ashan Parmar
 * @version 1.2
 */
public class AgoraphobicAI extends SnakeAI {
	
	/**
	 * Makes the Agoraphobic AI make a decision for a single game step.  
	 * If any of the available directions is a straight line to the food, the AI will pick that direction.
	 * Otherwise, this AI turns toward the closest obstacle (distance must be greater than 1, to avoid collisions) to the <code>Snake</code>'s head.
	 */
	@Override
	public void update(int gridSize, Point currentFood, Snake currentSnake) {
		Obstacle forwardObstacle = AIUtils.raycast(currentSnake.getHead(), Snake.Direction.UP, gridSize, currentFood, currentSnake);
		if (forwardObstacle.getObstacleType()==Obstacle.Type.FOOD) {//If food ahead, stop here
			return;
		}
		Obstacle leftObstacle = AIUtils.raycast(currentSnake.getHead(), Snake.Direction.LEFT, gridSize, currentFood, currentSnake);
		if (leftObstacle.getObstacleType()==Obstacle.Type.FOOD) {//If food to the left, turn left and stop here
			currentSnake.turnLeft();
			return;
		}
		Obstacle rightObstacle = AIUtils.raycast(currentSnake.getHead(), Snake.Direction.RIGHT, gridSize, currentFood, currentSnake);
		if (rightObstacle.getObstacleType()==Obstacle.Type.FOOD) {//If food to the right, turn right and stop here
			currentSnake.turnRight();
			return;
		}
		
		//If on an edge (not a corner), just go forward
		if (Math.min(leftObstacle.getDistance(), rightObstacle.getDistance())==1 && forwardObstacle.getDistance()>1) {
			return;
		}
		
		//If in a corner, try to escape
		if (Math.min(leftObstacle.getDistance(), rightObstacle.getDistance())==1 && forwardObstacle.getDistance()<=1) {
			if (leftObstacle.getDistance()>rightObstacle.getDistance()) {//If left is the better escape, take it
				currentSnake.turnLeft();
			} else {//Otherwise go right
				currentSnake.turnRight();
			}
			return;
		}
		
		//If forward obstacle is the closest (>1), go that direction
		if ((forwardObstacle.getDistance()<leftObstacle.getDistance()) && (forwardObstacle.getDistance()<rightObstacle.getDistance()) && (forwardObstacle.getDistance()>1)) {
			return;
		}
		//If left obstacle is the closest (>1), go that direction
		if (leftObstacle.getDistance()<rightObstacle.getDistance() && leftObstacle.getDistance()>1) {
			currentSnake.turnLeft();
			return;
		}
		//If we get here, right obstacle is the closest, go that direction
		currentSnake.turnRight();
	}

}
