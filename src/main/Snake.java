package main;
import java.awt.Point;
import java.util.LinkedList;

/**
 * A class which holds all information about the snake in a game of Snake.
 * @author Ashan Parmar
 * @version 1.3
 */
public class Snake {
	public enum Direction {
	    RIGHT, 
	    LEFT, 
	    UP, 
	    DOWN;
	}
	
	private LinkedList<Point> segments = new LinkedList<Point>();
	private Direction currentDirection;
	
	/**
	 * Create a new <code>Snake</code> object with the given start point, direction, and size.
	 * @param startPoint - The location this <code>Snake</code> will start at.
	 * @param startDirection - The direction this <code>Snake</code> will start facing.
	 * @param startSize - The length this <code>Snake</code> will start as.
	 */
	public Snake(Point startPoint, Direction startDirection, int startSize) {
		this.currentDirection = startDirection;
		
		for (int i=0;i<startSize;i++) {
			segments.add((Point) startPoint.clone());
		}
	}
	
	/**
	 * Create a new <code>Snake</code> object with the given start direction and segments.
	 * @param startDirection - The direction this <code>Snake</code> will start facing.
	 * @param startSegments - The <code>Point</code> locations of the segments this <code>Snake</code> will start with.
	 */
	public Snake(Direction startDirection, LinkedList<Point> startSegments) {
		this.currentDirection = startDirection;
		this.segments.addAll(startSegments);
	}
	
	/**
	 * Moves this <code>Snake</code> one unit in its current direction.
	 */
	public void update() {
		segments.poll();
		Point newHead = (Point) segments.getLast().clone();
		switch(currentDirection) {
			case RIGHT:
				newHead.translate(1, 0);
				break;
			case LEFT:
				newHead.translate(-1, 0);
				break;
			case UP:
				newHead.translate(0, -1);
				break;
			case DOWN:
				newHead.translate(0, 1);
				break;
		}
		segments.add(newHead);
	}
	
	/**
	 * Rotates the direction of this <code>Snake</code> 90 degrees left.
	 */
	public void turnLeft() {
		switch(currentDirection) {
			case RIGHT:
				currentDirection = Direction.UP;
				break;
			case LEFT:
				currentDirection = Direction.DOWN;
				break;
			case UP:
				currentDirection = Direction.LEFT;
				break;
			case DOWN:
				currentDirection = Direction.RIGHT;
				break;
		}
	}
	
	/**
	 * Rotates the direction of this <code>Snake</code> 90 degrees right.
	 */
	public void turnRight() {
		switch(currentDirection) {
			case RIGHT:
				currentDirection = Direction.DOWN;
				break;
			case LEFT:
				currentDirection = Direction.UP;
				break;
			case UP:
				currentDirection = Direction.RIGHT;
				break;
			case DOWN:
				currentDirection = Direction.LEFT;
				break;
		}
	}
	
	/**
	 * Adds a new segment to this <code>Snake</code>.
	 */
	public void addSegment() {
		segments.add((Point) segments.getLast().clone());
	}
	
	/**
	 * Gets the current location of the head of this <code>Snake</code>.
	 * @return - The <code>Point</code> location of this <code>Snake</code>'s head.
	 */
	public Point getHead() {
		return segments.getLast();
	}
	
	/**
	 * Gets the current direction of the head of this <code>Snake</code>.
	 * @return - The direction this <code>Snake</code> is currently facing.
	 */
	public Direction getDirection() {
		return currentDirection;
	}
	
	/**
	 * Gets this <code>Snake</code>'s current segments.
	 * @return - The <code>LinkedList</code> of <code>Point</code>s containing the location of all segments.
	 */
	public LinkedList<Point> getSegments() {
		return this.segments;
	}
	
	/**
	 * Checks whether a given <code>Point</code> collides with any segments of this <code>Snake</code>.
	 * @param meetingPoint - The <code>Point</code> to be checked for a collision.
	 * @return - <code>true</code> if there is any segment (including the head) at the given <code>Point</code>.
	 */
	public boolean isMeeting(Point meetingPoint) {
		for (Point p : segments) {
			if (p.distance(meetingPoint)==0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds the segment index at the given <code>Point</code>.
	 * @param meetingPoint - The <code>Point</code> to be checked.
	 * @return - The segment index of this <code>Snake</code> at the given <code>Point</code>, or -1 if there is no segment at the <code>Point</code>.
	 */
	public int getMeeting(Point meetingPoint) {
		for (Point p : segments) {
			if (p.distance(meetingPoint)==0) {
				return segments.size()-segments.indexOf(p);
			}
		}
		return -1;
	}
}
