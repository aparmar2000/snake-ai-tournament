package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

/**
 * Contains the method for rendering out a <code>LinkedList</code> of <code>Snapshot</code> objects which contains the record of a full game.
 * @author Ashan Parmar
 * @version 1.1
 */
public class SnakeRenderer {
	
	/**
	 * Renders out the game described by the given <code>LinkedList</code> of <code>Snapshot</code> objects.
	 * Each step of the game is rendered out to two numbered image files in the ".\results" directory.
	 * After the game has been rendered out, a death animation for the snake is rendered out to the same folder.
	 * Once all images have been rendered, they can be used to create a 30 fps video of the gameplay using a tool such as ffmpeg. 
	 * @param gridPixels - How many pixels each grid square should be rendered with.
	 * @param finishedRun - The <code>LinkedList</code> of <code>Snapshot</code> objects containing the game to be rendered.
	 */
	public static void renderSnake(int gridPixels, LinkedList<Snapshot> finishedRun) {
		int gridSize = finishedRun.getFirst().getGridSize();
		System.out.println("Beginning image output generation...");
		long startTime = System.currentTimeMillis();
		
		//Create background image
		int imgGridSize = (gridSize+2)*gridPixels;
		BufferedImage backgroundImage = new BufferedImage(imgGridSize, imgGridSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = backgroundImage.createGraphics();
		g2d.setColor(Color.black);//Draw backing
        g2d.fillRect(0, 0, imgGridSize, imgGridSize);
        
        for (int i=1;i<(gridSize+2);i++) {//Draw grid
        	g2d.setColor(Color.darkGray);
        	g2d.drawLine(gridPixels, i*gridPixels, imgGridSize-gridPixels, i*gridPixels);
        	g2d.drawLine(i*gridPixels, gridPixels, i*gridPixels, imgGridSize-gridPixels);
        }
        g2d.dispose();
		
		BufferedImage bufferedImage = new BufferedImage(imgGridSize, imgGridSize, BufferedImage.TYPE_INT_RGB);
		g2d = bufferedImage.createGraphics();
		int time = 0;
		int id = 0;
		double items = finishedRun.size();
		double eta,ellapsedTime,totalTime;
		File imageFile,imageFile2;
		for (Snapshot s : finishedRun) {//Draw every recorded frame
			
			//Draw game elements
			g2d.drawImage(backgroundImage, null, 0, 0);//Clear background
	        
	        g2d.setColor(Color.yellow);//Draw food
	        g2d.fillOval((s.getFood().x+1)*gridPixels, (s.getFood().y+1)*gridPixels, gridPixels, gridPixels);
	        
	        g2d.setColor(Color.white);//Draw Segments
	        for (Point p : s.getSegments()) {
	        	g2d.fillOval((p.x+1)*gridPixels, (p.y+1)*gridPixels, gridPixels, gridPixels);
	        }
	        
	        if (items-time==1) {//Draw Head
	        	g2d.setColor(Color.RED);
	        } else {
	        	g2d.setColor(Color.green);
	        }
	        g2d.fillOval((s.getSegments().getLast().x+1)*gridPixels, (s.getSegments().getLast().y+1)*gridPixels, gridPixels, gridPixels);
	        
	        g2d.setColor(Color.ORANGE);//Draw Score
	        g2d.drawString(Integer.toString(s.getScore()), 0, 10);
	        
	        //Output to PNG
	        imageFile = new File("results\\snakeRun_"+id+".png");
	        id++;
	        imageFile2 = new File("results\\snakeRun_"+id+".png");
	        id++;
	        try {
				ImageIO.write(bufferedImage, "png", imageFile);
				ImageIO.write(bufferedImage, "png", imageFile2);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        if (Math.round(items*0.05)>0) {
		        if ((time % Math.round(items*0.05) <= 0) && time>0) {
		        	ellapsedTime = (System.currentTimeMillis()-startTime);
		        	eta = Math.round(((ellapsedTime*(items/time))-ellapsedTime)/1000.0);
					System.out.println(time+" / "+items+" gameplay images done. ("+Math.round((time/(items*0.01))*1)+"%) ETA "+eta+" s");
				}
	        }
	        
	        time++;
		}
		totalTime = (System.currentTimeMillis()-startTime);
		g2d.dispose();
		System.out.println("All gameplay image output files generated.  Time taken: "+(totalTime/1000.0)+" s");
		
		//Render death animation
		Snapshot finalStep = finishedRun.getLast();
		BufferedImage baseFrame = new BufferedImage(imgGridSize, imgGridSize, BufferedImage.TYPE_INT_RGB);
		g2d = baseFrame.createGraphics();
		BufferedImage animFrame = new BufferedImage(imgGridSize, imgGridSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D gAnim = animFrame.createGraphics();
		Point explodingSegment;
		Shape explosionRing;
		time = 0;
		items = finalStep.getSegments().size()*5;
		startTime = System.currentTimeMillis();
		while (!finalStep.getSegments().isEmpty()) {
			explodingSegment = finalStep.getSegments().pop();
			
			//Draw game elements
			g2d.drawImage(backgroundImage, null, 0, 0);//Clear background
	        
	        g2d.setColor(Color.yellow);//Draw food
	        g2d.fillOval((finalStep.getFood().x+1)*gridPixels, (finalStep.getFood().y+1)*gridPixels, gridPixels, gridPixels);
	        
	        g2d.setColor(Color.white);//Draw Segments
	        for (Point p : finalStep.getSegments()) {
	        	g2d.fillOval((p.x+1)*gridPixels, (p.y+1)*gridPixels, gridPixels, gridPixels);
	        }
	        
	        //Draw Head
	        if (!finalStep.getSegments().isEmpty()) {
	        	g2d.setColor(Color.RED);
	        	g2d.fillOval((finalStep.getSegments().getLast().x+1)*gridPixels, (finalStep.getSegments().getLast().y+1)*gridPixels, gridPixels, gridPixels);
	        }
	        
	        g2d.setColor(Color.ORANGE);//Draw Score
	        g2d.drawString(Integer.toString(finalStep.getScore()), 0, 10);
			
			for (int f=0;f<5;f++) {//Draw explosion animation
				gAnim.drawImage(baseFrame, null, 0, 0);//Fill background
				
				//Generate ring shape
				explosionRing = createRingShape((explodingSegment.x+1)*gridPixels+(gridPixels/2),
						(explodingSegment.y+1)*gridPixels+(gridPixels/2),
						(gridPixels/2)+(f*2),Math.max((gridPixels/2)-(f*1),1));
				
				gAnim.setColor(lerpColors(Color.RED,Color.YELLOW,(f/5.0)));//Interpolate color
				gAnim.fill(explosionRing);
				
				gAnim.draw(explosionRing);//Draw ring
				
				//Output to PNG
		        imageFile = new File("results\\snakeRun_"+id+".png");
		        id++;
		        try {
					ImageIO.write(animFrame, "png", imageFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        if ((time % Math.round(items*0.05) <= 0) && time>0) {
		        	ellapsedTime = (System.currentTimeMillis()-startTime);
		        	eta = Math.round(((ellapsedTime*(items/time))-ellapsedTime)/1000.0);
					System.out.println(time+" / "+items+" explosion sequence images done. ("+Math.round((time/(items*0.01))*1)+"%) ETA "+eta+" s");
				}
		        time++;
			}
		}
		g2d.dispose();
		
		//Output one last frame to PNG
        imageFile = new File("results\\snakeRun_"+id+".png");
        id++;
        try {
			ImageIO.write(baseFrame, "png", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		totalTime += (System.currentTimeMillis()-startTime);
		System.out.println("All image output files generated.  Time taken: "+(totalTime/1000.0)+" s");
	}	
	
	/**
	 * Linearly interpolates two <code>Color</code>s.
	 * @param color1 - The <code>Color</code> at 0.0 percent.
	 * @param color2 - The <code>Color</code> at 1.0 percent.
	 * @param percent - Interpolation percentade as a double (0.0 to 1.0)
	 * @return - The interpolated <code>Color</code> result.
	 */
	private static Color lerpColors(Color color1, Color color2, double percent){
	      double inverse_percent = 1.0 - percent;
	      int redPart = (int) ((color1.getRed()*inverse_percent) + (color2.getRed()*percent));
	      int greenPart = (int) ((color1.getGreen()*inverse_percent) + (color2.getGreen()*percent));
	      int bluePart = (int) ((color1.getBlue()*inverse_percent) + (color2.getBlue()*percent));
	      return new Color(redPart, greenPart, bluePart);
	}
	
	/**
	 * Generates a <code>Shape</code> for a ring.
	 * @param centerX - The x location of the center of the ring.
	 * @param centerY - The y location of the center of the ring.
	 * @param outerRadius - The radius of the outer edge of the ring.
	 * @param thickness - The thickness of the ring.
	 * @return - A <code>Shape</code> containing the ring.
	 */
	private static Shape createRingShape(double centerX, double centerY, double outerRadius, double thickness) {
      Ellipse2D outer = new Ellipse2D.Double(
          centerX - outerRadius, 
          centerY - outerRadius,
          outerRadius + outerRadius, 
          outerRadius + outerRadius);
      Ellipse2D inner = new Ellipse2D.Double(
          centerX - outerRadius + thickness, 
          centerY - outerRadius + thickness,
          outerRadius + outerRadius - thickness - thickness, 
          outerRadius + outerRadius - thickness - thickness);
      Area area = new Area(outer);
      area.subtract(new Area(inner));
      return area;
  }
}
