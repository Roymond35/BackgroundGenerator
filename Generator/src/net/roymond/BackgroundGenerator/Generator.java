package net.roymond.BackgroundGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Generator {
	
	private static Random rand = new Random();
	
	private static final int RECT_MAX_WIDTH = 250;
	private static final int RECT_MAX_HEIGHT = 250;
	private static final int MAX_POLYGON_POINTS = 10;
	private static final int MAX_POLYGON_DISTANCE = 125;
	
	private boolean CIRCLES;
	private boolean SQUARES;
	private boolean POLYGONS;
	private int width;
	private int height;
	private float BASE_RED;
	private float BASE_GREEN;
	private float BASE_BLUE;
	private float DELTA;
	private int MAX_OBJECT_NUMBER;
	
	public static class Builder {
		
		private boolean CIRCLES = false;
		private boolean SQUARES = false;
		private boolean POLYGONS = false;
		private int width = 1920;
		private int height = 1080;
		private float BASE_RED = rand.nextInt(255);
		private float BASE_GREEN = rand.nextInt(255);
		private float BASE_BLUE = rand.nextInt(255);
		private float DELTA = 75;
		private int MAX_OBJECT_NUMBER = 5000;
		
		public Builder setCircles(boolean choice){
			CIRCLES = choice;
			return this;
		}
		
		public Builder setSquares(boolean choice){
			SQUARES = choice;
			return this;
		}
		
		public Builder setPolygons(boolean choice){
			POLYGONS = choice;
			return this;
		}
		
		public Builder setWidth(int choice){
			width = choice;
			return this;
		}
		
		public Builder setHeight(int choice){
			height = choice;
			return this;
		}
		
		public Builder setRGB(float red, float green, float blue) throws InvalidValueException{
			if (red <= 255 && red >=0){
				BASE_RED = red;
			} else {
				throw new InvalidValueException("Red Value must be between 0 and 255"); 
			}
			if (green <= 255 && green >=0){
				BASE_GREEN = green;
			} else {
				throw new InvalidValueException("Green Value must be between 0 and 255"); 
			}
			if (blue <= 255 && blue >=0){
				BASE_BLUE = blue;
			} else {
				throw new InvalidValueException("Blue Value must be between 0 and 255"); 
			}
			return this;
		}
		
		public Builder setDelta(float value) throws InvalidValueException{
			if (value <= 100 && value >=0){
				DELTA = value;
			} else {
				throw new InvalidValueException("Delta Value must be between 0 and 255"); 
			}
			return this;
		}
		
		public Builder setMaxObjects(int value) throws InvalidValueException{
			if (value < 1){
				throw new InvalidValueException("Max Objects Value must be greater than one"); 
			} else {
				MAX_OBJECT_NUMBER = value;
			}
			return this;
		}
		
		public Generator build(){
			while (!CIRCLES && !SQUARES && !POLYGONS){
				CIRCLES = rand.nextBoolean();
				SQUARES = rand.nextBoolean();
				POLYGONS = rand.nextBoolean();	
			}
			return new Generator(this);
		}
	}
	
	private Generator(Builder builder){
		CIRCLES = builder.CIRCLES;
		SQUARES = builder.SQUARES;
		POLYGONS = builder.POLYGONS;
		width = builder.width;
		height = builder.height;
		BASE_RED = builder.BASE_RED;
		BASE_GREEN = builder.BASE_GREEN;
		BASE_BLUE = builder.BASE_BLUE;
		DELTA = builder.DELTA;
		MAX_OBJECT_NUMBER = builder.MAX_OBJECT_NUMBER;
	}
	
	public Color chooseColor(){
	  	float red = Math.min((BASE_RED/255) + (rand.nextFloat() * (DELTA/100)),1);
		float blue = Math.min((BASE_BLUE/255) + (rand.nextFloat() * (DELTA/100)),1);
		float green = Math.min((BASE_GREEN/255) + (rand.nextFloat() * (DELTA/100)),1);
		float alpha = rand.nextFloat();
		Color chosenColor = new Color(red,green,blue,alpha);
		return chosenColor;
	}
	
	public void exportFile(Graphics2D graphic, BufferedImage image){
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
	
	public void drawCenteredCircle(Graphics2D g) {
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		int r = rand.nextInt(100)+50;
		x = x-(r/2);
		y = y-(r/2);
		g.setColor( chooseColor() );
		g.fillOval(x,y,r,r);
	}
	
	public void drawSquare(Graphics2D g){
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

		g.setColor( chooseColor() );
		g.fill(new Rectangle(x,y,width,height));
	}
	
	public void drawPolygons(Graphics2D g){
		int polygonPoints = rand.nextInt(MAX_POLYGON_POINTS);
		int[] xPoints = new int[polygonPoints];
		int[] yPoints = new int[polygonPoints];
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		for(int i = 0; i < polygonPoints; i++){
			xPoints[i] = x + (rand.nextInt(MAX_POLYGON_DISTANCE) - MAX_POLYGON_DISTANCE/2);
		}
		for(int j = 0; j < polygonPoints; j++){
			yPoints[j] = y + (rand.nextInt(MAX_POLYGON_DISTANCE) - MAX_POLYGON_DISTANCE/2);
		}
		
		g.setColor( chooseColor() );
		g.fill(new Polygon(xPoints, yPoints, xPoints.length));
	}
	
	public void generateBackground(){
		
		
				
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphic = image.createGraphics();
		graphic.setColor(new Color(BASE_RED/255,BASE_GREEN/255,BASE_BLUE/255,1));
		graphic.fillRect(0, 0, width, height);
		
		if (SQUARES){
			int maxNumSquares = rand.nextInt(MAX_OBJECT_NUMBER);
			for(int squares = 0; squares <= maxNumSquares; squares ++){
				drawSquare(graphic);
			}
		}
		if (CIRCLES){
			int maxNumCircles = rand.nextInt(MAX_OBJECT_NUMBER);
			for(int circles = 0; circles <= maxNumCircles; circles++)
			{
				drawCenteredCircle(graphic);
			}
		}
		if (POLYGONS){
			int maxNumCircles = rand.nextInt(MAX_OBJECT_NUMBER);
			for(int circles = 0; circles <= maxNumCircles; circles++)
			{
				drawPolygons(graphic);
			}
		}
		
		exportFile(graphic, image);
		System.out.println("Completed");
	}

}
