package net.roymond.BackgroundGenerator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Roymond on 4/11/2016.
 */
public class SetupWindow {
    static JFrame frame;
    private JPanel SetupWindow;
    private JLabel title;
    private JPanel sizeManager;
    private JLabel sizeTitle;
    private JTextField widthField;
    private JLabel heightText;
    private JLabel widthText;
    private JTextField heightField;
    private JLabel baseColor;
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
    private JTextField deltaValue;
    private JLabel deltaTitle;
    private JPanel freezeColorsPanel;
    private JLabel exportTitle;
    private JTextField exportDirValue;
    private JTextField prefixValue;
    private JLabel exportExtTitle;
    private JLabel imagePrefix;
    private JLabel desiredDirTitle;
    private JTextField exportExt;
    private JPanel exportManager;
    private JButton generateButton;
    private JTextField numberOfRuns;
    private JLabel numRuns;
    private JLabel errorText;
    private JButton resetButton;
    private JPanel buttonPanel;
    private JList list1;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private JSlider deltaSlider;
    private JLabel redSliderLabel;
    private JLabel greenSliderLabel;
    private JLabel blueSliderLabel;
    private JLabel deltaSliderLabel;

    //Actual Variables
    int imageWidth;
    int imageHeight;
    int baseRed;
    int baseGreen;
    int baseBlue;

    boolean widthSet = false;
    boolean heightSet = false;


    public SetupWindow() {

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

        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                redSlider.setToolTipText(String.valueOf(redSlider.getValue()));
                redSliderLabel.setText(String.valueOf(redSlider.getValue()));
            }
        });
        greenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                greenSlider.setToolTipText(String.valueOf(greenSlider.getValue()));
                greenSliderLabel.setText(String.valueOf(greenSlider.getValue()));
            }
        });
        blueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                blueSlider.setToolTipText(String.valueOf(blueSlider.getValue()));
                blueSliderLabel.setText(String.valueOf(blueSlider.getValue()));
            }
        });
        deltaSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                deltaSlider.setToolTipText(String.valueOf(deltaSlider.getValue()));
                deltaSliderLabel.setText(String.valueOf(deltaSlider.getValue()));
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I was touched... I'm excited");
                try {

                    Generator.Builder gen = new Generator.Builder();


                    if (!widthField.getText().equals("")) {  gen.setWidth(Integer.valueOf(widthField.getText())); }
                    if (!heightField.getText().equals("")) {  gen.setHeight(Integer.valueOf(heightField.getText()));  }

                    gen.setRed(redSlider.getValue());
                    gen.setGreen(greenSlider.getValue());
                    gen.setBlue(blueSlider.getValue());

                    if (!heightField.getText().equals("")){
                        float delta = Float.valueOf(deltaValue.getText());
                        if (delta > 100){
                            delta = 100;
                            deltaValue.setText(String.valueOf(delta));
                        }
                        gen.setDelta(delta);
                    }

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
                    if (list1.isSelectionEmpty()){
                        gen.setExtension("png");
                    } else {
                        String option = options[list1.getSelectedIndex()];
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
                            generator.generateBackground();
                        }
                    } else {
                        errorText.setText("The number of backgrounds has to be at least one.");
                    }



                } catch (Exception T){
                    System.out.println("Error");
                    T.printStackTrace();
                }


            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                list1.clearSelection();
            }
        });
    }

    private int checkColor(JTextField f){
        int dColor;
        if (f.getText().isEmpty()){
            return 0;
        } else {
            dColor = Integer.valueOf(f.getText());
            if (dColor > 255) {
                dColor = 255;
                f.setText(String.valueOf(dColor));
            }
        }
        return dColor;
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

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                errorText.setText("You can only add numbers");
            }
        }

        private boolean test(String text) {
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

            if (test(sb.toString())) {
                errorText.setText("");
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

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
                errorText.setText("");
            } else {
                errorText.setText("You can only add numbers");
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
