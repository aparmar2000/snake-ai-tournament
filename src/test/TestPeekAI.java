package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.LinkedList;

import main.PeekAI;
import main.Snake;
import main.Snake.Direction;
import main.SnakeAI;

import org.junit.Test;

public class TestPeekAI {
	@Test
	public void peekAIShouldPrioritizeFood() {
		Point testFood = new Point(10,1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,1));//Place snake segments
		testSegments.addFirst(new Point(1,2));
		testSegments.addFirst(new Point(1,3));
		Snake testSnake;
		SnakeAI ai = new PeekAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Peek AI should prioritize food over all else.",Direction.RIGHT,testSnake.getDirection());
	}
	
	@Test
	public void peekAIShouldPrioritizeCloserObstacles() {
		Point testFood = new Point(-1,-1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(5,10));//Place snake segments
		testSegments.addFirst(new Point(5,11));
		testSegments.addFirst(new Point(5,12));
		Snake testSnake;
		SnakeAI ai = new PeekAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Peek AI should make no action if there is no obstacle directly ahead and the food is not within line-of-sight.",Direction.UP,testSnake.getDirection());
	}
	
	@Test
	public void peekAIShouldEscapeCorners() {
		Point testFood = new Point(-1,-1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(2,0));//Place snake segments
		testSegments.addFirst(new Point(2,1));
		testSegments.addFirst(new Point(2,2));
		Snake testSnake;
		SnakeAI ai = new PeekAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Peek AI should turn toward the most empty direction to avoid an imminent collision.",Direction.RIGHT,testSnake.getDirection());
	}
}
