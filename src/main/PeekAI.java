package main;
import java.awt.Point;

/**
 * A <code>SnakeAI</code> which peeks at the next step of each option.
 * Options leading to a straight line to the food are preferred.
 * If no such options are available, the AI will make no action unless death is imminent, in which case the option with the most empty space is taken.
 * @author Ashan Parmar
 * @version 1.1
 */
public class PeekAI extends SnakeAI {
	
	/**
	 * Makes the Peek AI make a decision for a single game step.  
	 * If any of the available directions is a straight line to the food, the AI will pick that direction.
	 * Otherwise, this AI checks if it will run into an obstacle in the next game step.
	 * If it will, the AI turns in the direction with the greatest distance to the next obstacle.
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
		
		//If about to run into something, try to escape
		if (forwardObstacle.getDistance()<=1) {
			if (leftObstacle.getDistance()>rightObstacle.getDistance()) {//If left is the better escape, take it
				currentSnake.turnLeft();
			} else {//Otherwise go right
				currentSnake.turnRight();
			}
			return;
		}
	}

}
