package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.awt.Point;
import java.util.LinkedList;

import main.ShortcutAI;
import main.Snake;
import main.Snake.Direction;
import main.SnakeAI;

import org.junit.Test;

public class TestShortcutAI {
	@Test
	public void shortcutAIShouldPrioritizeFood() {
		Point testFood = new Point(2,1);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(1,1));//Place snake segments
		testSegments.addFirst(new Point(1,2));
		testSegments.addFirst(new Point(1,3));
		Snake testSnake;
		SnakeAI ai = new ShortcutAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertEquals("The Shortcut AI should prioritize adjacent food over all else.",Direction.RIGHT,testSnake.getDirection());
	}
	
	@Test
	public void shortcutAIShouldAvoidObstacles() {
		Point testFood = new Point(2,0);
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(2,2));//Place snake segments
		testSegments.addFirst(new Point(2,3));
		testSegments.addFirst(new Point(1,3));
		testSegments.addFirst(new Point(0,3));
		testSegments.addFirst(new Point(0,2));
		testSegments.addFirst(new Point(0,1));
		testSegments.addFirst(new Point(1,1));
		testSegments.addFirst(new Point(2,1));
		testSegments.addFirst(new Point(3,1));
		Snake testSnake;
		SnakeAI ai = new ShortcutAI();
		
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertNotSame("The Shortcut AI should avoid running into forward obstacles.",Direction.UP,testSnake.getDirection());
		
		testFood.setLocation(0, 2);
		testSegments.clear();
		testSegments.addFirst(new Point(2,2));//Place snake segments
		testSegments.addFirst(new Point(2,3));
		testSegments.addFirst(new Point(1,3));
		testSegments.addFirst(new Point(1,2));
		testSegments.addFirst(new Point(1,1));
		testSegments.addFirst(new Point(1,0));
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertNotSame("The Shortcut AI should avoid running into left obstacles.",Direction.LEFT,testSnake.getDirection());
		
		testFood.setLocation(4, 2);
		testSegments.clear();
		testSegments.addFirst(new Point(2,2));//Place snake segments
		testSegments.addFirst(new Point(2,3));
		testSegments.addFirst(new Point(3,3));
		testSegments.addFirst(new Point(3,2));
		testSegments.addFirst(new Point(3,1));
		testSegments.addFirst(new Point(3,0));
		testSnake = new Snake(Direction.UP,testSegments);
		ai.update(20, testFood, testSnake);
		assertNotSame("The Shortcut AI should avoid running into right obstacles.",Direction.RIGHT,testSnake.getDirection());
	}
}
