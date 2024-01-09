import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {

    private final JRadioButton radioButton1;
    private final JRadioButton radioButton2;
    private final JRadioButton radioButton3;

    public Main() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        getContentPane().setBackground(new Color(0x007355));

        // Load Bangla font
        Font banglaFont = loadBanglaFont();

        // Add image
        ImageIcon imageIcon = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 306, 360);
        add(imageLabel);

        // Add text with radio buttons
        radioButton1 = addTextWithRadioButton("নির্বাচন কমিশন", 98, banglaFont);
        radioButton2 = addTextWithRadioButton("প্রাথী আবেদন", 148, banglaFont);
        radioButton3 = addTextWithRadioButton("ভোটার", 198, banglaFont);
        addButton(banglaFont);
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Nikosh", Font.PLAIN, 20);
        }
    }

    private JRadioButton addTextWithRadioButton(String text, int y, Font banglaFont) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBounds(397, y, 200, 24);
        radioButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        radioButton.setBackground(new Color(0x007355));
        radioButton.setForeground(Color.WHITE);

        radioButton.addActionListener(e -> {
            if (e.getSource() == radioButton1) {
                radioButton2.setSelected(false);
                radioButton3.setSelected(false);
            } else if (e.getSource() == radioButton2) {
                radioButton1.setSelected(false);
                radioButton3.setSelected(false);
            } else if (e.getSource() == radioButton3) {
                radioButton1.setSelected(false);
                radioButton2.setSelected(false);
            }
        });
        add(radioButton);
        return radioButton;
    }

    private void addButton(Font banglaFont) {
        JButton button = new JButton("এগিয়ে যান");
        button.setBounds(390, 260, 132, 44);
        button.setForeground(Color.BLACK);
        button.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        button.setBackground(new Color(0x5FFF95));

        button.addActionListener(e -> {
            if (radioButton1.isSelected()) {

                    login1 loginFrame = new login1();
                    loginFrame.setTitle("Bangladesh Online Voting System - Login1");
                    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    loginFrame.setVisible(true);
                    dispose();

            } else if (radioButton2.isSelected()) {
                login2 loginFrame = new login2();
                loginFrame.setTitle("Bangladesh Online Voting System - Login2");
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginFrame.setVisible(true);
                dispose();
            } else if (radioButton3.isSelected()) {
                login3 loginFrame = new login3();
                loginFrame.setTitle("Bangladesh Online Voting System - Login3");
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginFrame.setVisible(true);
                dispose();
            }
        });


        add(button);
    }

    public static void main(String[] args) {

            Main frame = new Main();
            frame.setTitle("Bangladesh Online Voting System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

    }
}
