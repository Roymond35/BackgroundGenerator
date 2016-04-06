import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;

public class BackgroundGenerator {
	
	public static Random rand;
	public static int width;
	public static int height;
	
	public static final int RECT_MAX_WIDTH = 250;
	public static final int RECT_MAX_HEIGHT = 250;
	
	public static boolean CIRCLES = true;
	public static boolean SQUARES = true;
	
	public static void exportFile(Graphics2D graphic, BufferedImage image){
		long currentTime = System.currentTimeMillis();
		String fileName = "Backgrounds\\background_" + String.valueOf(currentTime) + ".png";
		File outputFile = new File(fileName);
		graphic.drawImage(image, null, 0, 0);
		try {
			ImageIO.write(image, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void drawCenteredCircle(Graphics2D g) {
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		int r = rand.nextInt(100)+50;
		x = x-(r/2);
		y = y-(r/2);
		  
	  	float red = rand.nextFloat();
		float blue = rand.nextFloat();
		float green = rand.nextFloat();
		float alpha = rand.nextFloat();
		Color circleColor = new Color(red,blue,green,alpha);
		g.setColor(circleColor);
		g.fillOval(x,y,r,r);
	}
	
	public static void drawSquare(Graphics2D g){
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		int width, height;
		if (rand.nextBoolean()){
			width = rand.nextInt(RECT_MAX_WIDTH);
			height = width;
		} else {
			width = rand.nextInt(RECT_MAX_WIDTH);
			height = rand.nextInt(RECT_MAX_HEIGHT);
		}
	  	float red = rand.nextFloat();
		float blue = rand.nextFloat();
		float green = rand.nextFloat();
		float alpha = rand.nextFloat();
		Color squareColor = new Color(red,blue,green,alpha);
		g.setColor(squareColor);
		g.fill(new Rectangle(x,y,width,height));
	}
	
	public static void generateBackground(){
		width = 1920;
		height = 1080;
		rand = new Random();
				
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);


		Graphics2D graphic = image.createGraphics();
		if (SQUARES){
			int maxNumSquares = rand.nextInt(1000);
			for(int squares = 0; squares <= maxNumSquares; squares ++){
				drawSquare(graphic);
			}
		}
		if (CIRCLES){
			int maxNumCircles = rand.nextInt(500);
			for(int circles = 0; circles <= maxNumCircles; circles++)
			{
				drawCenteredCircle(graphic);
			}
		}


		
		
		exportFile(graphic, image);
		System.out.println("Completed");
	}

	public static void main(String[] args) {
		//for(int counter = 0; counter < 5; counter++){
			generateBackground();
		//}
	}

}
