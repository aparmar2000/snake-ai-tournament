package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.LinkedList;

import main.AIUtils;
import main.Obstacle;
import main.Obstacle.Type;
import main.Snake;
import main.Snake.Direction;

import org.junit.Test;

public class TestAIUtils {
	@Test
	public void raycastShouldDetectObstacleTypeAndDistance() {
		Point testFood = new Point(3,5);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,5));//Place snake segments
		testSegments.addFirst(new Point(1,6));
		testSegments.addFirst(new Point(1,7));
		testSegments.addFirst(new Point(2,7));
		testSegments.addFirst(new Point(3,7));
		testSegments.addFirst(new Point(4,7));
		testSegments.addFirst(new Point(5,7));
		testSegments.addFirst(new Point(6,7));
		testSegments.addFirst(new Point(7,7));
		testSegments.addFirst(new Point(8,7));
		testSegments.addFirst(new Point(9,7));
		testSegments.addFirst(new Point(9,6));
		testSegments.addFirst(new Point(9,5));
		Snake testSnake = new Snake(Direction.UP,testSegments);
		Obstacle testResultObstacle;
		
		//Tests from head
		testResultObstacle = AIUtils.raycast(testSnake.getHead(), Direction.UP, 20, testFood, testSnake);
		assertEquals("Raycast from snake head in Direction.UP should be of Type.WALL",Type.WALL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from snake head in Direction.UP should have a distance of 6",6, testResultObstacle.getDistance());
		
		testResultObstacle = AIUtils.raycast(testSnake.getHead(), Direction.RIGHT, 20, testFood, testSnake);
		assertEquals("Raycast from snake head in Direction.RIGHT should be of Type.FOOD",Type.FOOD, testResultObstacle.getObstacleType());
		assertEquals("Raycast from snake head in Direction.RIGHT should have a distance of 2",2, testResultObstacle.getDistance());
		
		testResultObstacle = AIUtils.raycast(testSnake.getHead(), Direction.LEFT, 20, testFood, testSnake);
		assertEquals("Raycast from snake head in Direction.LEFT should be of Type.WALL",Type.WALL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from snake head in Direction.LEFT should have a distance of 2",2, testResultObstacle.getDistance());
		
		testResultObstacle = AIUtils.raycast(testSnake.getHead(), Direction.DOWN, 20, testFood, testSnake);
		assertEquals("Raycast from snake head in Direction.DOWN should be of Type.TAIL",Type.TAIL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from snake head in Direction.DOWN should have a distance of 1",1, testResultObstacle.getDistance());
		
		//Tests from (5,6)
		testResultObstacle = AIUtils.raycast(new Point(5,6), Direction.UP, 20, testFood, testSnake);
		assertEquals("Raycast from Point (5,6) in Direction.UP should be of Type.WALL",Type.WALL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from Point (5,6) in Direction.UP should have a distance of 7",7, testResultObstacle.getDistance());
		
		testResultObstacle = AIUtils.raycast(new Point(5,6), Direction.RIGHT, 20, testFood, testSnake);
		assertEquals("Raycast from Point (5,6) in Direction.RIGHT should be of Type.TAIL",Type.TAIL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from Point (5,6) in Direction.RIGHT should have a distance of 4",4, testResultObstacle.getDistance());
		
		testResultObstacle = AIUtils.raycast(new Point(5,6), Direction.LEFT, 20, testFood, testSnake);
		assertEquals("Raycast from Point (5,6) in Direction.LEFT should be of Type.TAIL",Type.TAIL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from Point (5,6) in Direction.LEFT should have a distance of 4",4, testResultObstacle.getDistance());
		
		testResultObstacle = AIUtils.raycast(new Point(5,6), Direction.DOWN, 20, testFood, testSnake);
		assertEquals("Raycast from Point (5,6) in Direction.DOWN should be of Type.TAIL",Type.TAIL, testResultObstacle.getObstacleType());
		assertEquals("Raycast from Point (5,6) in Direction.DOWN should have a distance of 1",1, testResultObstacle.getDistance());
	}
	
	@Test
	public void glanceFromSnakeShouldCheckAdjacent() {
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(0,5));//Place snake segments
		testSegments.addFirst(new Point(0,6));
		testSegments.addFirst(new Point(1,6));
		testSegments.addFirst(new Point(1,5));
		Snake testSnake = new Snake(Direction.UP,testSegments);
		
		assertEquals("Glance from Snake head in Direction.UP should be false",false,AIUtils.glance(Direction.UP, 20, testSnake));
		assertEquals("Glance from Snake head in Direction.RIGHT should be true",true,AIUtils.glance(Direction.RIGHT, 20, testSnake));
		assertEquals("Glance from Snake head in Direction.DOWN should be true",true,AIUtils.glance(Direction.DOWN, 20, testSnake));
		assertEquals("Glance from Snake head in Direction.LEFT should be true",true,AIUtils.glance(Direction.LEFT, 20, testSnake));
	}
	
	@Test
	public void glanceFromPointShouldCheckAdjacent() {
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(0,5));//Place snake segments
		testSegments.addFirst(new Point(0,6));
		testSegments.addFirst(new Point(0,7));
		testSegments.addFirst(new Point(1,7));
		testSegments.addFirst(new Point(2,7));
		testSegments.addFirst(new Point(2,6));
		testSegments.addFirst(new Point(2,5));
		Snake testSnake = new Snake(Direction.UP,testSegments);
		
		assertEquals("Glance from Point (1,5) in Direction.UP should be false",false,AIUtils.glance(Direction.UP, 20, testSnake, new Point(1,5)));
		assertEquals("Glance from Point (1,5) in Direction.RIGHT should be true",true,AIUtils.glance(Direction.RIGHT, 20, testSnake, new Point(1,5)));
		assertEquals("Glance from Point (1,5) in Direction.DOWN should be false",false,AIUtils.glance(Direction.DOWN, 20, testSnake, new Point(1,5)));
		assertEquals("Glance from Point (1,5) in Direction.LEFT should be true",true,AIUtils.glance(Direction.LEFT, 20, testSnake, new Point(1,5)));
	}
	
	@Test
	public void shiftLeftShouldShiftLeft() {
		assertEquals("Direction.UP shifted left should be Direction.LEFT",Direction.LEFT, AIUtils.shiftLeft(Direction.UP));
		assertEquals("Direction.RIGHT shifted left should be Direction.UP",Direction.UP, AIUtils.shiftLeft(Direction.RIGHT));
		assertEquals("Direction.DOWN shifted left should be Direction.RIGHT",Direction.RIGHT, AIUtils.shiftLeft(Direction.DOWN));
		assertEquals("Direction.LEFT shifted left should be Direction.DOWN",Direction.DOWN, AIUtils.shiftLeft(Direction.LEFT));
	}
	
	@Test
	public void shiftRightShouldShiftRight() {
		assertEquals("Direction.UP shifted right should be Direction.RIGHT",Direction.RIGHT, AIUtils.shiftRight(Direction.UP));
		assertEquals("Direction.RIGHT shifted right should be Direction.DOWN",Direction.DOWN, AIUtils.shiftRight(Direction.RIGHT));
		assertEquals("Direction.DOWN shifted right should be Direction.LEFT",Direction.LEFT, AIUtils.shiftRight(Direction.DOWN));
		assertEquals("Direction.LEFT shifted right should be Direction.UP",Direction.UP, AIUtils.shiftRight(Direction.LEFT));
	}
	
	@Test
	public void shiftForwardShouldShiftForward() {
		Point testPoint = new Point(0,0);
		AIUtils.shiftForward(Direction.UP,testPoint);
		assertEquals("(0,0) shifted Direction.UP should be (0,-1)",new Point(0,-1), testPoint);
		
		testPoint = new Point(0,0);
		AIUtils.shiftForward(Direction.RIGHT,testPoint);
		assertEquals("(0,0) shifted Direction.RIGHT should be (1,0)",new Point(1,0), testPoint);
		
		testPoint = new Point(0,0);
		AIUtils.shiftForward(Direction.DOWN,testPoint);
		assertEquals("(0,0) shifted Direction.DOWN should be (0,1)",new Point(0,1), testPoint);
		
		testPoint = new Point(0,0);
		AIUtils.shiftForward(Direction.LEFT,testPoint);
		assertEquals("(0,0) shifted Direction.LEFT should be (-1,0)",new Point(-1,0), testPoint);
	}
	
	@Test
	public void compareDirectionsShouldCompareDirections() {
		assertEquals("Direction.UP compared to Direction.UP should be Direction.UP",Direction.UP, AIUtils.compareDirections(Direction.UP,Direction.UP));
		assertEquals("Direction.UP compared to Direction.RIGHT should be Direction.RIGHT",Direction.RIGHT, AIUtils.compareDirections(Direction.UP,Direction.RIGHT));
		assertEquals("Direction.UP compared to Direction.DOWN should be Direction.DOWN",Direction.DOWN, AIUtils.compareDirections(Direction.UP,Direction.DOWN));
		assertEquals("Direction.UP compared to Direction.LEFT should be Direction.LEFT",Direction.LEFT, AIUtils.compareDirections(Direction.UP,Direction.LEFT));
		
		assertEquals("Direction.DOWN compared to Direction.UP should be Direction.DOWN",Direction.DOWN, AIUtils.compareDirections(Direction.DOWN,Direction.UP));
		assertEquals("Direction.DOWN compared to Direction.RIGHT should be Direction.LEFT",Direction.LEFT, AIUtils.compareDirections(Direction.DOWN,Direction.RIGHT));
		assertEquals("Direction.DOWN compared to Direction.DOWN should be Direction.UP",Direction.UP, AIUtils.compareDirections(Direction.DOWN,Direction.DOWN));
		assertEquals("Direction.DOWN compared to Direction.LEFT should be Direction.RIGHT",Direction.RIGHT, AIUtils.compareDirections(Direction.DOWN,Direction.LEFT));
	}
}
