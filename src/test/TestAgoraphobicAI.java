package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.LinkedList;

import main.AgoraphobicAI;
import main.Snake;
import main.Snake.Direction;
import main.SnakeAI;

import org.junit.Test;

public class TestAgoraphobicAI {
	@Test
	public void agoraphobicAIShouldPrioritizeFood() {
		Point testFood = new Point(10,1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,1));//Place snake segments
		testSegments.addFirst(new Point(1,2));
		testSegments.addFirst(new Point(1,3));
		Snake testSnake;
		SnakeAI ai = new AgoraphobicAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Agoraphobic AI should prioritize food over all else.",Direction.RIGHT,testSnake.getDirection());
	}
	
	@Test
	public void agoraphobicAIShouldPrioritizeCloserObstacles() {
		Point testFood = new Point(-1,-1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(5,10));//Place snake segments
		testSegments.addFirst(new Point(5,11));
		testSegments.addFirst(new Point(5,12));
		Snake testSnake;
		SnakeAI ai = new AgoraphobicAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Agoraphobic AI should turn toward the nearest obstacle, barring higher priority tasks.",Direction.LEFT,testSnake.getDirection());
	}
	
	@Test
	public void agoraphobicAIShouldEscapeCorners() {
		Point testFood = new Point(-1,-1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(0,0));//Place snake segments
		testSegments.addFirst(new Point(0,1));
		testSegments.addFirst(new Point(0,2));
		Snake testSnake;
		SnakeAI ai = new AgoraphobicAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Agoraphobic AI should prioritize escaping a corner if food is not within line-of-sight.",Direction.RIGHT,testSnake.getDirection());
	}
}
