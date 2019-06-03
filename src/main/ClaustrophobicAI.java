package main;
import java.awt.Point;

/**
 * A <code>SnakeAI</code> which takes whichever direction is a straight line to the food.
 * If there is no such direction, it tries to avoid getting too close to obstacles.
 * @author Ashan Parmar
 * @version 1.3
 */
public class ClaustrophobicAI extends SnakeAI {
	
	/**
	 * Makes the Claustrophobic AI make a decision for a single game step.  
	 * If any of the available directions is a straight line to the food, the AI will pick that direction.
	 * Otherwise, this AI turns away from the closest obstacle (distance must be less than 20% of the size of the game grid) to the <code>Snake</code>'s head.
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
		
		//If closest obstacle is >20% of gridSize away, just go forward
		if (Math.min(Math.min(leftObstacle.getDistance(), rightObstacle.getDistance()), forwardObstacle.getDistance())>=(gridSize*0.2)) {
			return;
		}
		
		//If forward obstacle is the closest, go go either left or right, whichever's farther away
		if ((forwardObstacle.getDistance()<=leftObstacle.getDistance()) && (forwardObstacle.getDistance()<=rightObstacle.getDistance())) {
			if (leftObstacle.getDistance()>rightObstacle.getDistance()) {//If left is further away, go that direction
				currentSnake.turnLeft();
				return;
			}
			currentSnake.turnRight();//Otherwise, go right
			return;
		}
		//If left obstacle is the closest, go right
		if (leftObstacle.getDistance()<rightObstacle.getDistance()) {
			currentSnake.turnRight();
			return;
		}
		//If we get here, right obstacle is the closest, go left
		currentSnake.turnLeft();
	}

}
