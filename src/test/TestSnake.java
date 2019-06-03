package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.LinkedList;

import main.Snake;
import main.Snake.Direction;

import org.junit.Test;

public class TestSnake {
	@Test
	public void UpdateShouldMoveForward() {
		Snake testSnake;
		
		testSnake = new Snake(new Point(5,5),Direction.UP,3);
		testSnake.update();
		assertEquals("Snake.update() when facing Direction.UP should move to (5,4)",new Point(5,4), testSnake.getHead());
		
		testSnake = new Snake(new Point(5,5),Direction.RIGHT,3);
		testSnake.update();
		assertEquals("Snake.update() when facing Direction.RIGHT should move to (6,5)",new Point(6,5), testSnake.getHead());
		
		testSnake = new Snake(new Point(5,5),Direction.LEFT,3);
		testSnake.update();
		assertEquals("Snake.update() when facing Direction.LEFT should move to (4,5)",new Point(4,5), testSnake.getHead());
	}
	
	@Test
	public void turnLeftShouldTurnLeft() {
		Snake testSnake;
		
		testSnake = new Snake(new Point(5,5),Direction.UP,3);
		testSnake.turnLeft();
		assertEquals("Direction.UP turned left should be Direction.LEFT",Direction.LEFT, testSnake.getDirection());
		
		testSnake = new Snake(new Point(5,5),Direction.RIGHT,3);
		testSnake.turnLeft();
		assertEquals("Direction.RIGHT turned left should be Direction.UP",Direction.UP, testSnake.getDirection());
		
		testSnake = new Snake(new Point(5,5),Direction.DOWN,3);
		testSnake.turnLeft();
		assertEquals("Direction.DOWN turned left should be Direction.RIGHT",Direction.RIGHT, testSnake.getDirection());
		
		testSnake = new Snake(new Point(5,5),Direction.LEFT,3);
		testSnake.turnLeft();
		assertEquals("Direction.LEFT turned left should be Direction.DOWN",Direction.DOWN, testSnake.getDirection());
	}
	
	@Test
	public void turnRightShouldTurnRight() {
		Snake testSnake;
		
		testSnake = new Snake(new Point(5,5),Direction.UP,3);
		testSnake.turnRight();
		assertEquals("Direction.UP shifted right should be Direction.RIGHT",Direction.RIGHT, testSnake.getDirection());
		
		testSnake = new Snake(new Point(5,5),Direction.RIGHT,3);
		testSnake.turnRight();
		assertEquals("Direction.RIGHT shifted right should be Direction.DOWN",Direction.DOWN, testSnake.getDirection());

		testSnake = new Snake(new Point(5,5),Direction.DOWN,3);
		testSnake.turnRight();
		assertEquals("Direction.DOWN shifted right should be Direction.LEFT",Direction.LEFT, testSnake.getDirection());
		
		testSnake = new Snake(new Point(5,5),Direction.LEFT,3);
		testSnake.turnRight();
		assertEquals("Direction.LEFT shifted right should be Direction.UP",Direction.UP, testSnake.getDirection());
	}
	
	@Test
	public void isMeetingShouldDetectIntersection() {
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,5));//Place snake segments
		testSegments.addFirst(new Point(1,6));
		testSegments.addFirst(new Point(1,7));
		Snake testSnake = new Snake(Direction.UP,testSegments);
		
		assertEquals("isMeeting() for (0,6) should be false",false,testSnake.isMeeting(new Point(0,6)));
		assertEquals("isMeeting() for (1,6) should be true",true,testSnake.isMeeting(new Point(1,6)));
		assertEquals("isMeeting() for (2,6) should be false",false,testSnake.isMeeting(new Point(2,6)));
		assertEquals("isMeeting() for (1,5) should be true",true,testSnake.isMeeting(new Point(1,5)));
		assertEquals("isMeeting() for (1,7) should be true",true,testSnake.isMeeting(new Point(1,7)));
	}
	
	@Test
	public void getMeetingShouldIdentifyIntersection() {
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,5));//Place snake segments
		testSegments.addFirst(new Point(1,6));
		testSegments.addFirst(new Point(1,7));
		Snake testSnake = new Snake(Direction.UP,testSegments);
		
		assertEquals("getMeeting() for (0,6) should be -1",-1,testSnake.getMeeting(new Point(0,6)));
		assertEquals("getMeeting() for (1,6) should be 2",2,testSnake.getMeeting(new Point(1,6)));
		assertEquals("getMeeting() for (2,6) should be -1",-1,testSnake.getMeeting(new Point(2,6)));
		assertEquals("getMeeting() for (1,5) should be 1",1,testSnake.getMeeting(new Point(1,5)));
		assertEquals("getMeeting() for (1,7) should be 3",3,testSnake.getMeeting(new Point(1,7)));
	}
}
