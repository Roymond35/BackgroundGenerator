package net.roymond.BackgroundGenerator;

import javax.swing.*;

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
