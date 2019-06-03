package main;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * A <code>SnakeAI</code> which makes its decisions based on a recursive Best-First Search.
 * @author Ashan Parmar
 * @version 1.5
 */
public class BestFirstAI extends SnakeAI {
	private Random rand = new Random(System.currentTimeMillis());
	int options,time,timeCap;
	
	/**
	 * Makes the Best First AI make a decision for a single game step.  
	 * This AI performs a recursive Best-First Search to locate a path to the food.  
	 * If the food is underneath a segment of the <code>Snake</code>, the destination of the pathfinding is instead moved to a random free spot on the game grid.
	 * If this search reaches a depth greater than ten times the size of the game grid or if the search takes more than 100000 steps it is aborted.
	 * After performing the path search, this AI turns in the direction of the first step in the path.
	 */
	@Override
	public void update(int gridSize, Point currentFood, Snake currentSnake) {
		options = 0;
		time = 0;
		timeCap = 100000;
		
		Point currentDestination = (Point) currentFood.clone();
		while(currentSnake.isMeeting(currentDestination)) {
			currentDestination.move(rand.nextInt(gridSize), rand.nextInt(gridSize));
		}
		
		Snake.Direction chosenDirection = AIUtils.compareDirections(currentSnake.getDirection(), bestFirstSearch(gridSize,currentSnake,currentSnake.getDirection(),currentSnake.getHead(),currentDestination));
		
		switch (chosenDirection) {
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
	
	private Snake.Direction bestFirstSearch(int gridSize, Snake currentSnake, Snake.Direction currentDirection, Point origin, Point destination) {
		time++;
		if (origin.distance(destination)==0) {
			return currentDirection;
		}
		
		PriorityQueue<Node> branchOptions = new PriorityQueue<Node>();
		
		boolean forwardObstacle = AIUtils.glance(currentDirection, gridSize, currentSnake, origin);
		boolean leftObstacle = AIUtils.glance(AIUtils.shiftLeft(currentDirection), gridSize, currentSnake, origin);
		boolean rightObstacle = AIUtils.glance(AIUtils.shiftRight(currentDirection), gridSize, currentSnake, origin);
		
		if (!forwardObstacle) {//Add forward node if direction is clear
			Point forwardPoint = (Point) origin.clone();
			AIUtils.shiftForward(currentDirection, forwardPoint);
			branchOptions.add(new Node(forwardPoint,currentDirection,forwardPoint.distance(destination)));
			options++;
		}
		if (!leftObstacle) {//Add left node if direction is clear
			Point leftPoint = (Point) origin.clone();
			AIUtils.shiftForward(AIUtils.shiftLeft(currentDirection), leftPoint);
			branchOptions.add(new Node(leftPoint,AIUtils.shiftLeft(currentDirection),leftPoint.distance(destination)));
			options++;
		}
		if (!rightObstacle) {//Add right node if direction is clear
			Point rightPoint = (Point) origin.clone();
			AIUtils.shiftForward(AIUtils.shiftRight(currentDirection), rightPoint);
			branchOptions.add(new Node(rightPoint,AIUtils.shiftRight(currentDirection),rightPoint.distance(destination)));
			options++;
		}
		
		while (!branchOptions.isEmpty()) {
			Node n = branchOptions.poll();
			options--;
			if (bestFirstSearch(gridSize, currentSnake, n, destination, 0)) {
				return n.getDirection();
			}
		}
		
		return currentDirection;
	}
	
	private boolean bestFirstSearch(int gridSize, Snake currentSnake, Node originNode, Point destination, int depth) {
		time++;
		Point origin = originNode.getLocation();
		if (origin.distance(destination)==0) {
			return true;
		}
		
		if (depth>(gridSize*10)) {
			return true;
		}
		
		if (time>timeCap) {
			return true;
		}
		
		PriorityQueue<Node> branchOptions = new PriorityQueue<Node>();
		Snake.Direction currentDirection = originNode.getDirection();
		
		boolean forwardObstacle = AIUtils.glance(currentDirection, gridSize, currentSnake, origin);
		boolean leftObstacle = AIUtils.glance(AIUtils.shiftLeft(currentDirection), gridSize, currentSnake, origin);
		boolean rightObstacle = AIUtils.glance(AIUtils.shiftRight(currentDirection), gridSize, currentSnake, origin);
		
		if (!forwardObstacle) {//Add forward node if direction is clear
			Point forwardPoint = (Point) origin.clone();
			AIUtils.shiftForward(currentDirection, forwardPoint);
			branchOptions.add(new Node(forwardPoint,currentDirection,forwardPoint.distance(destination)));
			options++;
		}
		if (!leftObstacle) {//Add left node if direction is clear
			Point leftPoint = (Point) origin.clone();
			AIUtils.shiftForward(AIUtils.shiftLeft(currentDirection), leftPoint);
			branchOptions.add(new Node(leftPoint,AIUtils.shiftLeft(currentDirection),leftPoint.distance(destination)));
			options++;
		}
		if (!rightObstacle) {//Add right node if direction is clear
			Point rightPoint = (Point) origin.clone();
			AIUtils.shiftForward(AIUtils.shiftRight(currentDirection), rightPoint);
			branchOptions.add(new Node(rightPoint,AIUtils.shiftRight(currentDirection),rightPoint.distance(destination)));
			options++;
		}
		
		while (!branchOptions.isEmpty()) {
			Node n = branchOptions.poll();
			options--;
			if (bestFirstSearch(gridSize, currentSnake, n, destination, depth+1)) {
				return true;
			}
		}
		
		return false;
	}
	
	private class Node implements Comparable<Node> {
		private Point location;
		private Snake.Direction direction;
		private double distance;
		
		private Node(Point newLocation, Snake.Direction newDirection, double newDistance) {
			this.location = newLocation;
			this.direction = newDirection;
			this.distance = newDistance;
		}

		private Point getLocation() {
			return location;
		}
		
		private Snake.Direction getDirection() {
			return direction;
		}

		@Override
		public int compareTo(BestFirstAI.Node other) {			
			return (int) Math.signum(this.distance-other.distance);
		}
		
	}
}
