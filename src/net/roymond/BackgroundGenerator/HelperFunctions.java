package net.roymond.BackgroundGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gero on 7/6/2016.
 */
public class HelperFunctions {

    /**
     * Attempts to load the image
     * @return
     * Returns True if it is successful, returns false if it is not.
     */
    public static BufferedImage loadImage(String filePath){
        BufferedImage sourceImage;
        sourceImage = null;
        try {
            if (filePath != null){
                File file = new File(filePath);
                if (file.isFile()) {
                    sourceImage = ImageIO.read(file);
                } else {
                    System.out.println("File Not found");
                }
            }
            return sourceImage; // If the file path is empty, it isn't successful.

        } catch (IOException ex){
            ex.printStackTrace();
            return sourceImage;
        }
    }

    /**
     * Gets the top x numbers
     * @param numColors - the number of colors to return
     * @return - An array of colors, indexed in descending order (Most Popular is 0 index).
     */
    public static Color[] getTopColors(int numColors, BufferedImage sourceImage){
        Color[] returnColors = new Color[numColors];
        HashMap<Color, Integer> colorMap = new HashMap<Color, Integer>();
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color pixelColor = new Color(sourceImage.getRGB(i,j), true);
                if (pixelColor.getAlpha() != 0) {
                    if (colorMap.containsKey(pixelColor)) {
                        colorMap.put(pixelColor, colorMap.get(pixelColor) + 1);
                    } else {
                        colorMap.put(pixelColor, 1);
                    }
                }
            }
        }
        for( int c = 0; c < numColors; c++){
            Color topColor = null;
            int max = -1;
            for (Map.Entry<Color, Integer> entry : colorMap.entrySet())
            {
                if ( max < entry.getValue() )
                {
                    topColor = entry.getKey();
                    max = entry.getValue();
                }
            }
            colorMap.remove(topColor);
            returnColors[c] = topColor;
        }
        return returnColors;
    }


}
