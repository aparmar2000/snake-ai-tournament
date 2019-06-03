package main;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/**
 * A <code>SnakeAI</code> which makes its decisions based on random chance.
 * @author Ashan Parmar
 * @version 1.2
 */
public class ChaoticAI extends SnakeAI {
	private Random decision = new Random(System.currentTimeMillis());
	
	/**
	 * Makes the Chaotic AI make a decision for a single game step.  
	 * The Chaotic AI has a 50% chance of not turning, a 25% chance of turning left, and a 25% chance of turning right.  
	 * The AI will remove any directional options that will result in a collision on the next game step.
	 */
	@Override
	public void update(int gridSize, Point currentFood, Snake currentSnake) {
		boolean forwardObstacle = AIUtils.glance(Snake.Direction.UP, gridSize, currentSnake);
		boolean leftObstacle = AIUtils.glance(Snake.Direction.LEFT, gridSize, currentSnake);
		boolean rightObstacle = AIUtils.glance(Snake.Direction.RIGHT, gridSize, currentSnake);
		
		LinkedList<Snake.Direction> options = new LinkedList<Snake.Direction>();
		if (!forwardObstacle) {
			options.add(Snake.Direction.UP);
			options.add(Snake.Direction.UP);
		}
		if (!leftObstacle) {
			options.add(Snake.Direction.LEFT);
		}
		if (!rightObstacle) {
			options.add(Snake.Direction.RIGHT);
		}
		
		if (options.size()<1) {
			return;
		}
		switch (options.get(decision.nextInt(options.size()))) {//Pick direction at random (avoiding instant death)
			case LEFT:
				currentSnake.turnLeft();
				break;
			case RIGHT:
				currentSnake.turnRight();
				break;
			default:
				break;
		}
	}

}
