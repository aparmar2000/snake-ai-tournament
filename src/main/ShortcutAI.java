package main;
import java.awt.Point;

/**
 * A <code>SnakeAI</code> which turns toward withever space takes it closest to the food without instantly killing it.
 * @author Ashan Parmar
 * @version 1.2
 */
public class ShortcutAI extends SnakeAI {
	
	/**
	 * Makes the Shortcut AI make a decision for a single game step.  
	 * This AI turns in the direction which will bring the <code>Snake</code>'s head closest to the food without running into an obstacle.  
	 * If this is not possible the AI makes no action.
	 */
	@Override
	public void update(int gridSize, Point currentFood, Snake currentSnake) {
		boolean forwardObstacle = AIUtils.glance(Snake.Direction.UP, gridSize, currentSnake);
		boolean leftObstacle = AIUtils.glance(Snake.Direction.LEFT, gridSize, currentSnake);
		boolean rightObstacle = AIUtils.glance(Snake.Direction.RIGHT, gridSize, currentSnake);
		
		Point nextStep = (Point) currentSnake.getHead().clone();//Calculate forward distance to food
		AIUtils.shiftForward(currentSnake.getDirection(), nextStep);
		double forwardDist = nextStep.distance(currentFood);
		nextStep = (Point) currentSnake.getHead().clone();//Calculate left distance to food
		AIUtils.shiftForward(AIUtils.shiftLeft(currentSnake.getDirection()), nextStep);
		double leftDist = nextStep.distance(currentFood);
		nextStep = (Point) currentSnake.getHead().clone();//Calculate right distance to food
		AIUtils.shiftForward(AIUtils.shiftRight(currentSnake.getDirection()), nextStep);
		double rightDist = nextStep.distance(currentFood);
		
		/*
		if (Math.min(Math.min(leftDist, rightDist),forwardDist) == 0) {
			System.out.println("\tForward distance: "+forwardDist);
			System.out.println("\tLeft distance: "+leftDist);
			System.out.println("\tRight distance: "+rightDist);
			System.out.println("\t\t=====");
			System.out.println("\tHead Direction: "+currentSnake.getDirection());
			System.out.println("\tHead Position: "+currentSnake.getHead());
			System.out.println("\tFood Position: "+currentFood);
			System.out.println("-----------------------------------------");
		}
		*/
		
		//If next to the food, eat it
		if (currentSnake.getHead().distance(currentFood)==1 && (!currentSnake.isMeeting(currentFood))) {
			if (forwardDist==0) {
				return;
			}
			if (leftDist==0) {
				currentSnake.turnLeft();
				return;
			}
			if (rightDist==0) {
				currentSnake.turnRight();
				return;
			}
		}
		
		//If forward is closest to the food, go that way
		if ((!forwardObstacle) && (forwardDist<leftDist && forwardDist<rightDist)) {
			return;
		}
		
		//If left is closest to the food, go that way
		if ((!leftObstacle) && (leftDist<rightDist)) {
			currentSnake.turnLeft();
			return;
		}
		
		//If right is closest to the food, go that way
		if (!rightObstacle) {
			currentSnake.turnRight();
		}
		
		//If about to die, try to escape
		if (forwardObstacle) {
			if (rightObstacle) {//If left is the better escape, take it
				currentSnake.turnLeft();
			} else {//Otherwise go right
				currentSnake.turnRight();
			}
			return;
		}
	}

}
