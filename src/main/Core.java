package main;
import java.awt.Point;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;

/**
 * The core class of the Snake game.
 * The game simulation is handled by this class, as are some of the filesystem actions.
 * @author Ashan Parmar
 * @version 1.7
 */
public class Core {
	
	/**
	 * The main game simulation method.
	 * It simulates a certain number of games for the given <code>SnakeAI</code> and records the best.
	 * It then empties the ".\results" directory and calls the <code>renderSnake</code> method in the <code>SnakeRenderer</code> class to render out the game.
	 * @param args - These are ignored.  Currently, all variables are set in the code.
	 */
	public static void main(String[] args) {
		int stepLimit = (int) Math.floor(1.5*60*30);
		int gridSize = 25;
		int trials = 10000;
		SnakeAI currentAI = new BestFirstAI();
		
		Random rand = new Random(System.currentTimeMillis());
		Point foodLoc = new Point(0,0);
		Snake snake;
		int score,age,trial;
		int lastScore = 0;
		LinkedList<Snapshot> currentRun = new LinkedList<Snapshot>();
		LinkedList<Snapshot> lastRun = new LinkedList<Snapshot>();
		
		System.out.println("Setup Done.");
		long startTime = System.currentTimeMillis();
		trial = 0;
		for (int r=0;r<trials;r++) {
			trial++;
			System.out.println("Trial "+trial+" Beginning.");
			foodLoc.move(rand.nextInt(gridSize), rand.nextInt(gridSize));
			snake = new Snake(new Point(gridSize/2, gridSize/2),Snake.Direction.UP,3);
			score = 0;
			age = 0;
			currentRun.clear();
			
			for (int t=0;t<stepLimit;t++) {
				score++;
				age++;
				//System.out.println("\tStep: "+t);
				if (snake.getHead().distance(foodLoc)==0) {
					score+=stepLimit/10;
					snake.addSegment();
					foodLoc.move(rand.nextInt(gridSize), rand.nextInt(gridSize));
				}
				
				currentAI.update(gridSize, foodLoc, snake);
				snake.update();
				currentRun.add(new Snapshot(score,gridSize,foodLoc,snake));
				
				//If wall collision, break
				if (snake.getHead().getX()<0 || snake.getHead().getX()>=gridSize) {
					System.out.println("\tDeath by wall collision at time: "+t+".");
					break;
				}
				if (snake.getHead().getY()<0 || snake.getHead().getY()>=gridSize) {
					System.out.println("\tDeath by wall collision at time: "+t+".");
					break;
				}
				//If tail collision, break;
				if (snake.getMeeting(snake.getHead())>1 && t>1) {
					System.out.println("\tDeath by self collision at time: "+t+" and segment: "+snake.getMeeting(snake.getHead())+".");
					break;
				}
			}
			System.out.println("Trial "+trial+" Done.  Final Score: "+score);
			//After a run
			if (score>lastScore && age>(30*30)) {
				lastRun.clear();
				lastRun.addAll(currentRun);
				lastScore = score;
			}
			
			if (!((r+1)<trials) && lastScore == 0) {
				r--;
			}
		}
		System.out.println("All "+trial+" Trials Done in "+(System.currentTimeMillis()-startTime)+" ms.  Final Top Score: "+lastScore+" Best Length: "+lastRun.getLast().getSegments().size());
		
		//After all runs, output the best one
		emptyFolder(new File("results"));//Empty the directory if it already exists
		new File("results").mkdirs();//Create the empty directory
		
		SnakeRenderer.renderSnake(14, lastRun);
	}
	
	/**
	 * Empties a folder of all files.
	 * @param folder - The <code>File</code> object representing the directory to be emptied
	 */
	public static void emptyFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	        	f.delete();
	        }
	    }
	}
}
