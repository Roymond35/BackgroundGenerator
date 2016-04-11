package net.roymond.BackgroundGenerator;

import javax.swing.*;
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

    //Actual Variables
    int imageWidth;
    int imageHeight;
    int baseRed;
    int baseGreen;
    int baseBlue;

    boolean widthSet = false;
    boolean heightSet = false;


    public SetupWindow() {

        PlainDocument widthDoc = (PlainDocument) widthField.getDocument();
        widthDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument heightDoc = (PlainDocument) heightField.getDocument();
        heightDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument redDoc = (PlainDocument) baseRedValue.getDocument();
        redDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument greenDoc = (PlainDocument) baseGreenValue.getDocument();
        greenDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument blueDoc = (PlainDocument) baseBlueValue.getDocument();
        blueDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument deltaDoc = (PlainDocument) deltaValue.getDocument();
        deltaDoc.setDocumentFilter(new MyIntFilter());
        PlainDocument numberRunsDoc = (PlainDocument) numberOfRuns.getDocument();
        numberRunsDoc.setDocumentFilter(new MyIntFilter());

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I was touched... I'm excited");
                try {

                    Generator.Builder gen = new Generator.Builder();


                    if (!widthField.getText().equals("")) {  gen.setWidth(Integer.valueOf(widthField.getText())); }
                    if (!heightField.getText().equals("")) {  gen.setHeight(Integer.valueOf(heightField.getText()));  }

                    gen.setRed(checkColor(baseRedValue));
                    gen.setGreen(checkColor(baseGreenValue));
                    gen.setBlue(checkColor(baseBlueValue));
                    gen.setDelta(checkColor(deltaValue));

                    gen.setCircles(turnOnCircles.isSelected());
                    gen.setSquares(turnOnSquares.isSelected());
                    gen.setOctagons(turnOnOctagons.isSelected());
                    gen.setPolygons(turnOnPolygons.isSelected());

                    gen.freezeRed(freezeRed.isSelected());
                    gen.freezeGreen(freezeGreen.isSelected());
                    gen.freezeBlue(freezeBlue.isSelected());

                    gen.setExportDir(exportDirValue.getText());
                    gen.setFilePrefix(prefixValue.getText());
                    if(exportExt.getText().equals("")) { gen.setExtension("png"); } else { gen.setExtension(exportExt.getText()); } // This might barf...

                    Generator generator = gen.build();

                    if(!numberOfRuns.getText().equals("")){
                        errorText.setText("");
                        int num = Integer.valueOf(numberOfRuns.getText());
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


    //Credit where Credit is due. This section was implemented by Hovercraft Full of Eels
    // http://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
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
                // warn the user and don't allow the insert
            }
        }

        private boolean test(String text) {
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
                super.replace(fb, offset, length, text, attrs);
            } else {
                // warn the user and don't allow the insert
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
            } else {
                // warn the user and don't allow the insert
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
