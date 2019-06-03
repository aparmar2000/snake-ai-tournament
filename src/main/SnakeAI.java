package main;
import java.awt.Point;

/**
 * An abstract class for Snake-playing AIs.
 * @author Ashan Parmar
 * @version 1.1
 */
public abstract class SnakeAI {
	/**
	 * Makes the AI make a decision for a single game step
	 * @param gridSize - The <code>int</code> size of the game's grid
	 * @param currentFood - The current <code>Point</code> location of the food
	 * @param currentSnake - The current <code>Snake</code> object
	 */
	abstract public void update(int gridSize, Point currentFood, Snake currentSnake);
}
