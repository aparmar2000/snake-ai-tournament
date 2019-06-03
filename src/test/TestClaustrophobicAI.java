package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.LinkedList;

import main.ClaustrophobicAI;
import main.Snake;
import main.Snake.Direction;
import main.SnakeAI;

import org.junit.Test;

public class TestClaustrophobicAI {
	@Test
	public void claustrophobicAIShouldPrioritizeFood() {
		Point testFood = new Point(10,1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,1));//Place snake segments
		testSegments.addFirst(new Point(1,2));
		testSegments.addFirst(new Point(1,3));
		Snake testSnake;
		SnakeAI ai = new ClaustrophobicAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Claustrophobic AI should prioritize food over all else.",Direction.RIGHT,testSnake.getDirection());
	}
	
	@Test
	public void claustrophobicAIShouldPrioritizeCloserObstacles() {
		Point testFood = new Point(-1,-1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(2,10));//Place snake segments
		testSegments.addFirst(new Point(2,11));
		testSegments.addFirst(new Point(2,12));
		Snake testSnake;
		SnakeAI ai = new ClaustrophobicAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Claustrophobic AI should turn away from the nearest obstacle, barring higher priority tasks.",Direction.RIGHT,testSnake.getDirection());
	}
	
	@Test
	public void claustrophobicAIShouldIgnoreDistantWalls() {
		Point testFood = new Point(-1,-1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(10,6));//Place snake segments
		testSegments.addFirst(new Point(10,7));
		testSegments.addFirst(new Point(10,8));
		Snake testSnake;
		SnakeAI ai = new ClaustrophobicAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Claustrophobic AI should make no action if the nearest wall is >20% of the grid size away.",Direction.UP,testSnake.getDirection());
	}
}
