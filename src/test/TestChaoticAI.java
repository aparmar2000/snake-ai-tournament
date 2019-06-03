package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.LinkedList;

import main.ChaoticAI;
import main.Snake;
import main.Snake.Direction;

import org.junit.Test;

public class TestChaoticAI {
	@Test
	public void chaoticAIShouldAvoidCollision() {
		LinkedList<Point> testSegments = new LinkedList<Point>();
		testSegments.addFirst(new Point(0,0));//Place snake segments
		testSegments.addFirst(new Point(0,1));
		testSegments.addFirst(new Point(0,2));
		Snake testSnake;
		ChaoticAI ai = new ChaoticAI();
		
		for (int i=0;i<10000;i++) {
			testSnake = new Snake(Direction.UP,testSegments);
			ai.update(20, new Point(-1,-1), testSnake);
			assertEquals("The Chaotic AI should never turn into an obstacle.",Direction.RIGHT,testSnake.getDirection());
		}
	}
}
