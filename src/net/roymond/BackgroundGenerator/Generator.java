package net.roymond.BackgroundGenerator;
/**
 This program was written by Roy W. Gero.
 Last Updated: Jun 13, 2016
 If you have questions, comments or concerns please contact him on GitHub
 */

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

	/**
	 * This is the builder pattern that I use to create the background images
	 *
	 */

	
	private static Random rand = new Random();
	
	private static final int RECT_MAX_WIDTH = 250;
	private static final int RECT_MAX_HEIGHT = 250;
	private static final int MAX_POLYGON_POINTS = 10;
	private static final int MAX_POLYGON_DISTANCE = 125;
	
	private boolean CIRCLES;
	private boolean SQUARES;
	private boolean POLYGONS;
	private boolean OCTAGONS;
	private int width;
	private int height;
	private float BASE_RED;
	private float BASE_GREEN;
	private float BASE_BLUE;
	private boolean allowRed;
	private boolean allowGreen;
	private boolean allowBlue;
	private float DELTA;
	private int MAX_OBJECT_NUMBER;
	private File exportDir;
	private String filePrefix;
	private String fileExtension;
	
	public static class Builder {
		
		private boolean CIRCLES = false;
		private boolean SQUARES = false;
		private boolean POLYGONS = false;
		private boolean OCTAGONS = false;
		private int width = 1920;
		private int height = 1080;
		private float BASE_RED = rand.nextInt(255);
		private float BASE_GREEN = rand.nextInt(255);
		private float BASE_BLUE = rand.nextInt(255);
		private boolean allowRed = true;
		private boolean allowGreen = true;
		private boolean allowBlue = true;
		private float DELTA = 75;
		private int MAX_OBJECT_NUMBER = 5000;
		private File exportDir = null;
		private String filePrefix = "backgrounds_";
		private String fileExtension = "png";
		
		public Builder setCircles(boolean choice){
			CIRCLES = choice;
			return this;
		}
		
		public Builder setShapes(String shapes){
			try{
				String[] listOfShapes = shapes.split(",");
				for( int i=0; i< listOfShapes.length; i++ ){
					String d = listOfShapes[i].toLowerCase();
					switch(d){
						case "circles" : CIRCLES = true; break;
						case "squares" : SQUARES = true; break;
						case "polygons" : POLYGONS = true; break;
						case "octagons" : OCTAGONS = true; break;
						default:  break;
					}
				}
				
			} catch (Exception e){
				e.printStackTrace();
			}
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
		
		public Builder setOctagons(boolean choice){
			OCTAGONS = choice;
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

		public Builder setExtension(String ext){
			fileExtension = ext;
			return this;
		}

		public Builder setFilePrefix(String str){
			filePrefix = str;
			return this;
		}

		public Builder setExportDir(String dir){
			exportDir = new File(dir);
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

		public Builder setRed(int choice){
			BASE_RED = choice;
			return this;
		}
		public Builder setGreen(int choice){
			BASE_GREEN = choice;
			return this;
		}
		public Builder setBlue(int choice){
			BASE_BLUE = choice;
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
		
		public Builder freezeColor(String choice){
			char[] charChoice = choice.toLowerCase().toCharArray();
			for(int z = 0; z <charChoice.length; z++) {
				char c = charChoice[z];
				switch (c) {
					case 'r':
						allowRed = false;
						break;
					case 'g':
						allowGreen = false;
						break;
					case 'b':
						allowBlue = false;
						break;
					default:
						break;
				}
			}
			return this;
		}

		public Builder freezeRed(boolean choice){
			allowRed = !choice; // True means don't do it... false means do.
			return this;
		}

		public Builder freezeGreen(boolean choice){
			allowGreen = !choice; // True means don't do it... false means do.
			return this;
		}
		public Builder freezeBlue(boolean choice){
			allowBlue = !choice; // True means don't do it... false means do.
			return this;
		}
		
		public Generator build(){
			while (!CIRCLES && !SQUARES && !POLYGONS && !OCTAGONS){
				CIRCLES = rand.nextBoolean();
				SQUARES = rand.nextBoolean();
				POLYGONS = rand.nextBoolean();
				OCTAGONS = rand.nextBoolean();
			}
			return new Generator(this);
		}
	}
	
	private Generator(Builder builder){
		CIRCLES = builder.CIRCLES;
		SQUARES = builder.SQUARES;
		POLYGONS = builder.POLYGONS;
		OCTAGONS = builder.OCTAGONS;
		width = builder.width;
		height = builder.height;
		BASE_RED = builder.BASE_RED;
		BASE_GREEN = builder.BASE_GREEN;
		BASE_BLUE = builder.BASE_BLUE;
		allowRed = builder.allowRed;
		allowGreen = builder.allowGreen;
		allowBlue = builder.allowBlue;
		DELTA = builder.DELTA;
		MAX_OBJECT_NUMBER = builder.MAX_OBJECT_NUMBER;
		filePrefix = builder.filePrefix;
		exportDir = builder.exportDir;
		fileExtension = builder.fileExtension;
	}
	
	public Color chooseColor(){
		float red = BASE_RED/255; float green = BASE_GREEN/255; float blue = BASE_BLUE/255;
		if (allowRed) { red = Math.min((BASE_RED/255) + (rand.nextFloat() * (DELTA/100)),1); }
		if (allowBlue) { blue = Math.min((BASE_BLUE/255) + (rand.nextFloat() * (DELTA/100)),1); }
		if (allowGreen) { green = Math.min((BASE_GREEN/255) + (rand.nextFloat() * (DELTA/100)),1); }
		float alpha = rand.nextFloat();
		return new Color(red,green,blue,alpha);
	}
	
	public void exportFile(Graphics2D graphic, BufferedImage image){
		long currentTime = System.currentTimeMillis();
		if (fileExtension.equals("")) { fileExtension = "png"; }
		String fileName;
		File outputFile;
		if (exportDir == null){
			fileName = filePrefix + "_" + String.valueOf(currentTime) + "." + fileExtension;
			outputFile = new File(fileName);
		} else {
			String ending = filePrefix + "_" + String.valueOf(currentTime) + "." + fileExtension;
			outputFile = new File(exportDir, ending);
		}
		graphic.drawImage(image, null, 0, 0);
		try {
			ImageIO.write(image, fileExtension, outputFile);
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
	
	public void drawOctagons(Graphics2D g){
		int[] xPoints = new int[8];
		int[] yPoints = new int[8];
		
		int centerX = rand.nextInt(width);
		int centerY = rand.nextInt(height);
		int octHeight = rand.nextInt(RECT_MAX_WIDTH/2);
		if(octHeight % 2 != 0){
			octHeight++;
		}
		int rValue = octHeight/2;
		//points starting top left going counter clockwise
		xPoints[0] = centerX - rValue; yPoints[0] = centerY + octHeight;
		xPoints[1] = centerX - octHeight; yPoints[1] = centerY + rValue;
		xPoints[2] = centerX - octHeight; yPoints[2] = centerY - rValue;
		xPoints[3] = centerX - rValue; yPoints[3] = centerY - octHeight;
		xPoints[4] = centerX + rValue; yPoints[4] = centerY - octHeight;
		xPoints[5] = centerX + octHeight; yPoints[5] = centerY - rValue;
		xPoints[6] = centerX + octHeight; yPoints[6] = centerY + rValue;
		xPoints[7] = centerX + rValue; yPoints[7] = centerY + octHeight;
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
		if (OCTAGONS){
			int maxNum = rand.nextInt(MAX_OBJECT_NUMBER);
			for(int it = 0; it <= maxNum; it++)
			{
				drawOctagons(graphic);
			}
		}
		if (POLYGONS){
			int maxNum = rand.nextInt(MAX_OBJECT_NUMBER);
			for(int it = 0; it <= maxNum; it++)
			{
				drawPolygons(graphic);
			}
		}

		
		exportFile(graphic, image);
		System.out.println("Completed");
	}

}
