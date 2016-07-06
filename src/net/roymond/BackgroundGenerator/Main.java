package net.roymond.BackgroundGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Random;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		/*
			I don't want to use the UI for all 30 NHL teams, so I'm going to do it through here.

		 */

		int numberColors = 3;
		float deltaValue = 20;
		int numberOfBackgrounds = 10;
		Random rand = new Random();

		try {
			Files.walk(Paths.get("C:\\Users\\gero\\Desktop\\NHL Project")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
					BufferedImage dispImg = HelperFunctions.loadImage(filePath.toString());
					Color[] topColors = HelperFunctions.getTopColors(numberColors,dispImg);

					Generator.Builder gen = new Generator.Builder();
					for (int i = 0; i < topColors.length; i++){
						for(int k = 0; k < numberOfBackgrounds; k++ ) {
							gen.setRed(topColors[i].getRed());
							gen.setBlue(topColors[i].getBlue());
							gen.setGreen(topColors[i].getGreen());
							try {
								gen.setDelta(deltaValue);
							} catch (InvalidValueException e) {
								e.printStackTrace();
							}

							gen.setSquares(rand.nextBoolean());
							gen.setCircles(rand.nextBoolean());
							gen.setPolygons(rand.nextBoolean());
							gen.setOctagons(rand.nextBoolean());

							gen.freezeBlue(rand.nextBoolean());
							gen.freezeGreen(rand.nextBoolean());
							gen.freezeRed(rand.nextBoolean());

							gen.enableImage(dispImg);
							gen.setDesiredDimensions(600, 600);
							gen.setAlignment("Center");

							gen.setExportDir("C:\\Users\\gero\\Google Drive\\NHL Backgrounds");

							String pattern = Pattern.quote(System.getProperty("file.separator"));
							String[] brokenPath = filePath.toString().split(pattern);
							String teamName = brokenPath[brokenPath.length - 1].split("\\.")[0];

							gen.setFilePrefix(teamName + "_");

							gen.build().generateBackground();
						}





					}
                }
            });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
