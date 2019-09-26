package net.roymond.BackgroundGenerator;

/**
        This program was written by Roy W. Gero.
        Last Updated: Jul 1, 2016
        If you have questions, comments or concerns please contact him on GitHub
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roy Gero on 4/11/2016.
 */
public class SetupWindow {

    //These are the parameters for the Setup Window. Not all of these are in use, but they are required for the Frame to work properly.
    static JFrame frame;
    private JPanel SetupWindow;
    private JTextField widthField;
    private JLabel heightText;
    private JLabel widthText;
    private JTextField heightField;
    private JTextField baseRedValue;
    private JTextField baseGreenValue;
    private JTextField baseBlueValue;
    private JLabel blueTitle;
    private JLabel greenTitle;
    private JLabel redTitle;
    private JPanel baseColorManager;
    private JLabel shapesTitle;
    private JCheckBox turnOnCircles;
    private JCheckBox turnOnSquares;
    private JCheckBox turnOnPolygons;
    private JCheckBox turnOnOctagons;
    private JPanel lockShapesPanel;
    private JLabel freezeColorsTitle;
    private JCheckBox freezeRed;
    private JCheckBox freezeGreen;
    private JCheckBox freezeBlue;
    private JTextField exportDirValue;
    private JTextField prefixValue;
    private JButton generateButton;
    private JTextField numberOfRuns;
    private JLabel errorText;
    private JButton resetButton;
    private JList extensionList;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private JSlider deltaSlider;
    private JLabel redSliderLabel;
    private JLabel greenSliderLabel;
    private JLabel blueSliderLabel;
    private JLabel deltaSliderLabel;
    private JButton browse;
    private JTextField deltaValue;
    private JLabel deltaTitle;
    private JPanel freezeColorsPanel;
    private JLabel exportExtTitle;
    private JLabel imagePrefix;
    private JLabel desiredDirTitle;
    private JTextField exportExt;
    private JPanel exportManager;
    private JLabel numRuns;
    private JPanel buttonPanel;
    private JTextField filePath;
    private JButton chooseFileButton;
    private JButton getPopColorButton;
    private JButton resetThisTabButton;
    private JButton firstColor;
    private JButton secondColor;
    private JButton thirdColor;
    private JTextField dispFilePath;
    private JCheckBox enableImage;
    private JComboBox alignment;
    private JPanel popColorTab;
    private JLabel scalingLabel;
    private JLabel xLabel;
    private JTextField desiredX;
    private JLabel yLabel;
    private JTextField desiredY;
    private JPanel sizeManager;
    private JButton selectedColor;
    private JPanel baseColorPanel;
    private JPanel ImagePanel;
    private JCheckBox loadImageCheckbox;
    private JPanel imagePositionPanel;
    private JPanel imageProperties;
    private JPanel optionsPanel;
    private boolean imageLoaded = false;

    private BufferedImage sourceImage;

    /**
     * This is the default constructor for the setup window.
     * The program uses a builder pattern to establish all the variables.
     * The ActionListener on the Generate button actually handles all the execution
     */
    public SetupWindow() {

        errorText.setText(" ");

        // Hiding objects that aren't needed immediately
        imageProperties.setVisible(false);
        firstColor.setVisible(false);
        secondColor.setVisible(false);
        thirdColor.setVisible(false);

        loadImageCheckbox.addChangeListener(e -> imageProperties.setVisible(loadImageCheckbox.isSelected()));

        //Formatting the slider labels so they are consistent.
        deltaSliderLabel.setText(String.valueOf(deltaSlider.getValue()));
        redSliderLabel.setText(String.valueOf(redSlider.getValue()));
        greenSliderLabel.setText(String.valueOf(greenSlider.getValue()));
        blueSliderLabel.setText(String.valueOf(blueSlider.getValue()));

        PlainDocument widthDoc = (PlainDocument) widthField.getDocument();
        widthDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument heightDoc = (PlainDocument) heightField.getDocument();
        heightDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument numberRunsDoc = (PlainDocument) numberOfRuns.getDocument();
        numberRunsDoc.setDocumentFilter(new MyIntFilter());

        PlainDocument desiredWidthDocument = (PlainDocument) desiredX.getDocument();
        desiredWidthDocument.setDocumentFilter(new MyIntFilter());
        PlainDocument desiredHeightDocument = (PlainDocument) desiredY.getDocument();
        desiredHeightDocument.setDocumentFilter(new MyIntFilter());

        //Setting up all the sliders with listeners so they can update the label when the value changes.
        redSlider.addChangeListener(e -> {
            redSlider.setToolTipText(String.valueOf(redSlider.getValue()));
            redSliderLabel.setText(String.valueOf(redSlider.getValue()));
            updatePreviewColor();
        });
        greenSlider.addChangeListener(e -> {
            greenSlider.setToolTipText(String.valueOf(greenSlider.getValue()));
            greenSliderLabel.setText(String.valueOf(greenSlider.getValue()));
            updatePreviewColor();
        });
        blueSlider.addChangeListener(e -> {
            blueSlider.setToolTipText(String.valueOf(blueSlider.getValue()));
            blueSliderLabel.setText(String.valueOf(blueSlider.getValue()));
            updatePreviewColor();
        });
        deltaSlider.addChangeListener(e -> {
            deltaSlider.setToolTipText(String.valueOf(deltaSlider.getValue()));
            deltaSliderLabel.setText(String.valueOf(deltaSlider.getValue()));
        });


        generateButton.addActionListener(e -> {
            try {

                if (filePath.getText() != null && !imageLoaded) {
                    loadImage();
                }
                errorText.setText(" ");
                Generator.Builder gen = new Generator.Builder();


                if (!widthField.getText().equals("")) {  gen.setWidth(Integer.valueOf(widthField.getText())); }
                if (!heightField.getText().equals("")) {  gen.setHeight(Integer.valueOf(heightField.getText()));  }

                if (enableImage.isSelected()){
                    gen.setAlignment(alignment.getSelectedItem().toString());
                    gen.enableImage(sourceImage);
                    if (!desiredX.getText().isEmpty() || !desiredY.getText().isEmpty() ){

                        if (desiredX.getText().isEmpty()){
                            gen.setDesiredDimensions(Integer.MIN_VALUE, Integer.valueOf(desiredY.getText()));
                        } else if (desiredY.getText().isEmpty()){
                            gen.setDesiredDimensions(Integer.valueOf(desiredX.getText()), Integer.MIN_VALUE);
                        } else {
                            gen.setDesiredDimensions(Integer.valueOf(desiredX.getText()), Integer.valueOf(desiredY.getText()));
                        }

                    }
                }


                gen.setRed(redSlider.getValue());
                gen.setGreen(greenSlider.getValue());
                gen.setBlue(blueSlider.getValue());

                gen.setDelta(deltaSlider.getValue());


                gen.setCircles(turnOnCircles.isSelected());
                gen.setSquares(turnOnSquares.isSelected());
                gen.setOctagons(turnOnOctagons.isSelected());
                gen.setPolygons(turnOnPolygons.isSelected());

                gen.freezeRed(freezeRed.isSelected());
                gen.freezeGreen(freezeGreen.isSelected());
                gen.freezeBlue(freezeBlue.isSelected());

                gen.setExportDir(exportDirValue.getText());
                gen.setFilePrefix(prefixValue.getText());



                //Time for the export extension
                String[] options = {"png","jpg","bmp"};
                if (extensionList.isSelectionEmpty()){
                    gen.setExtension("png");
                } else {
                    String option = options[extensionList.getSelectedIndex()];
                    gen.setExtension(option);
                }

                Generator generator = gen.build();

                if(!numberOfRuns.getText().equals("")){
                    errorText.setText("");
                    int num = Integer.valueOf(numberOfRuns.getText());
                    if (num > 20){
                        num = 20;
                        numberOfRuns.setText("20");
                    }
                    for (int i = 0; i < num; i++){
                        errorText.setText(String.valueOf(i) + " of " + String.valueOf(num) + " completed");
                        generator.generateBackground();
                    }
                    errorText.setText("All Completed");
                } else {
                    errorText.setText("The number of backgrounds has to be at least one.");
                }



            } catch (Exception T){
                System.out.println("Error");
                T.printStackTrace();
            }


        });

        chooseFileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG and JPG Files", "png", "jpg");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            int returnVal = chooser.showOpenDialog(SetupWindow);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                filePath.setText( chooser.getSelectedFile().getAbsolutePath() );
                dispFilePath.setText( chooser.getSelectedFile().getAbsolutePath() );

                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
            }
            imageLoaded = false;
        });

        //This is the listener that will enable the user to select an export directory
        browse.addActionListener(e -> {
            JFileChooser exportDirChooser = new JFileChooser();
            exportDirChooser.setCurrentDirectory(new File("."));
            exportDirChooser.setDialogTitle("Select an export directory");
            exportDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            exportDirChooser.setAcceptAllFileFilterUsed(false);
            int returnVal = exportDirChooser.showOpenDialog(SetupWindow);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                String exportPath = exportDirChooser.getSelectedFile().getAbsolutePath();
                exportDirValue.setText(exportPath);

                System.out.println("You chose to open this file: " +
                        exportDirChooser.getSelectedFile().getName());
            }
        });

        //Resets all the values
        resetButton.addActionListener(e -> {
            errorText.setText(" ");
            widthField.setText("");
            heightField.setText("");
            freezeRed.setSelected(false);
            freezeGreen.setSelected(false);
            freezeBlue.setSelected(false);
            turnOnCircles.setSelected(false);
            turnOnOctagons.setSelected(false);
            turnOnSquares.setSelected(false);
            turnOnPolygons.setSelected(false);
            exportDirValue.setText("");
            prefixValue.setText("");
            numberOfRuns.setText("");
            extensionList.clearSelection();
            filePath.setText("");
            dispFilePath.setText("");
            firstColor.setVisible(false);
            firstColor.setEnabled(false);
            firstColor.setBackground(SetupWindow.getBackground());
            secondColor.setVisible(false);
            secondColor.setEnabled(false);
            secondColor.setBackground(SetupWindow.getBackground());
            thirdColor.setVisible(false);
            thirdColor.setEnabled(false);
            thirdColor.setBackground(SetupWindow.getBackground());
        });

        getPopColorButton.addActionListener(e -> {
            boolean success = loadImage();
            if (success) {
                Color[] topColors = getTopColors(3);
                displayTopColors(topColors);
            }
        });

        firstColor.addActionListener(e -> setColors(firstColor.getBackground()));

        secondColor.addActionListener(e -> setColors(secondColor.getBackground()));

        thirdColor.addActionListener(e -> setColors(thirdColor.getBackground()));

    }

    /**
     * Display Top Colors
     * ----
     * Takes the top three colors and puts them onto the JPanel as buttons for the user to select
     * @param topColors - the array of the top colors
     */
    private void displayTopColors(Color[] topColors){
        //We have to check each index in case there are less than three.
        if (topColors[0] != null) {
            firstColor.setVisible(true);
            firstColor.setEnabled(true);
            firstColor.setBackground(topColors[0]);
            firstColor.setOpaque(true);
            firstColor.setBorderPainted(false);
        }
        if (topColors[1] != null) {
            secondColor.setVisible(true);
            secondColor.setEnabled(true);
            secondColor.setBackground(topColors[1]);
            secondColor.setOpaque(true);
            secondColor.setBorderPainted(false);
        }
        if (topColors[2] != null) {
            thirdColor.setVisible(true);
            thirdColor.setEnabled(true);
            thirdColor.setBackground(topColors[2]);
            thirdColor.setOpaque(true);
            thirdColor.setBorderPainted(false);
        }
    }

    private void updatePreviewColor(){
        int red = redSlider.getValue();
        int blue = blueSlider.getValue();
        int green = greenSlider.getValue();

        Color previewColor = new Color(red,green,blue);
        selectedColor.setBackground(previewColor);

    }

    /**
     * Attempts to load the image
     * @return
     * Returns True if it is successful, returns false if it is not.
     */
    private boolean loadImage(){

        sourceImage = null;
        try {
            if (filePath.getText() != null){
                File file = new File(filePath.getText());
                if (file.isFile()) {
                    System.out.println(file.getAbsolutePath());
                    sourceImage = ImageIO.read(file);
                    imageLoaded = true;
                } else {
                    filePath.setText("File not found!");
                    dispFilePath.setText("File not found!");
                    return false;
                }
                return true;
            }
            return false; // If the file path is empty, it isn't successful.

        } catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the top x numbers
     * @param numColors - the number of colors to return
     * @return - An array of colors, indexed in descending order (Most Popular is 0 index).
     */
    private Color[] getTopColors(int numColors){
        Color[] returnColors = new Color[numColors];
        HashMap<Color, Integer> colorMap = new HashMap<>();
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

    private void setColors(Color col){
        redSlider.setValue(col.getRed());
        blueSlider.setValue(col.getBlue());
        greenSlider.setValue(col.getGreen());

    }

    /*Credit where Credit is due. This section was implemented by Hovercraft Full of Eels
        http://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
        It was modified however, the test needs to check for an empty string so the user can
        delete all the values
    */

    class MyIntFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (stringTest(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                errorText.setText("You can only add numbers");
            }
        }

        private boolean stringTest(String text) {
            if (text.equals("")){
                return true;
            }
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (stringTest(sb.toString())) {
                errorText.setText(" ");
                super.replace(fb, offset, length, text, attrs);
            } else {
                errorText.setText("You can only add numbers");
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (stringTest(sb.toString())) {
                super.remove(fb, offset, length);
                errorText.setText("");
            } else {
                errorText.setText("You can only add numbers.");
            }

        }
    }


    public static void main(String[] args) {
        frame = new JFrame("SetupWindow");
        frame.setTitle("Roy's Background Generator");
        frame.setContentPane(new SetupWindow().SetupWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);






    }
}
