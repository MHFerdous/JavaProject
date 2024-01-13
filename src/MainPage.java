import javax.swing.*;
import java.awt.*;
import java.io.*;


public class MainPage extends JFrame {
    private final JRadioButton radioButton1;
    private final JRadioButton radioButton2;

    public MainPage() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setTitle("বাংলাদেশ অনলাইন ভোটিং সিস্টেম");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // window open in center
        getContentPane().setBackground(new Color(0x007355));


        Font banglaFont = loadBanglaFont(); // Load Bangla font

        // Add image
        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison.png");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(0, 0, 306, 360);
        add(Image);

        // Add text with radio buttons
        radioButton1 = addTextWithRadioButton("নির্বাচন কমিশন", 127, banglaFont);
        radioButton2 = addTextWithRadioButton("ভোটার", 178, banglaFont);
        SubmitButton(banglaFont);
    }

    public static void main(String[] args) {

        MainPage frame = new MainPage();
        frame.setVisible(true);


    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.BOLD, 14);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 20);
        }
    }

    private JRadioButton addTextWithRadioButton(String text, int y, Font banglaFont) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBounds(393, y, 200, 24);
        radioButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        radioButton.setBackground(new Color(0x007355));
        radioButton.setForeground(Color.WHITE);

        radioButton.addActionListener(e -> {
            if (e.getSource() == radioButton1) {
                radioButton2.setSelected(false);
            } else if (e.getSource() == radioButton2) {
                radioButton1.setSelected(false);
            }
        });
        add(radioButton);
        return radioButton;
    }

    private void SubmitButton(Font banglaFont) {
        JButton SubmitButton = new JButton("এগিয়ে যান");
        SubmitButton.setBounds(398, 240, 132, 44);
        SubmitButton.setForeground(Color.BLACK);
        SubmitButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        SubmitButton.setBackground(new Color(0x5FFF95));

        SubmitButton.addActionListener(e -> {
            if (radioButton1.isSelected()) {
                new NcLoginPage();
                dispose();
            } else if (radioButton2.isSelected()) {
                new VoterLoginPage();
                dispose();
            }
        });
        add(SubmitButton);
    }
}