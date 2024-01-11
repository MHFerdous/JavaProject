import javax.swing.*;
import java.awt.*;
import java.io.*;

public class VotingPage extends JFrame {

    public VotingPage(){
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setVisible(true);
        setTitle("ভোট প্রদান");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xFFFFFF));

        Font banglaFont = loadBanglaFont();
        BackButton1(banglaFont);
        voteButton(banglaFont);
        cancelButton(banglaFont);
        addTextField(banglaFont);

        JRadioButton radioButton1 = addTextWithRadioButton(banglaFont);
    }
    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }
    private void BackButton1(Font banglaFont) {
        JButton BackButton1 = new JButton("প্রস্থান");
        BackButton1.setBounds(520, 7, 71, 37);
        BackButton1.setForeground(Color.WHITE);
        BackButton1.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        BackButton1.setBackground(new Color(0xFF0000));

        BackButton1.addActionListener(e -> {
            MainPage loginFrame = new MainPage();
            loginFrame.setVisible(true);
            dispose();
        });

        add(BackButton1);
    }

    private void voteButton(Font banglaFont) {
        JButton voteButton = new JButton("ভোট দিন");
        voteButton.setBounds(142, 300, 149, 42);
        voteButton.setForeground(Color.WHITE);
        voteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        voteButton.setBackground(new Color(0x045703));

        voteButton.addActionListener(e -> {

        });


        add(voteButton);
    }
    private void cancelButton(Font banglaFont) {
        JButton cancelButton = new JButton("বাতিল");
        cancelButton.setBounds(320, 300, 149, 42);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        cancelButton.setBackground(new Color(0xFF1B1B));

        cancelButton.addActionListener(e -> {

        });

        add(cancelButton);
    }
    private void addTextField(Font banglaFont){

        JPanel jPanel = new JPanel();
        jPanel.setBounds(165, 45, 270, 30);
        jPanel.setBackground(new Color(0x9CB2FF));
        add(jPanel);

        JLabel label1 = new JLabel("আপনার পছন্দের প্রার্থীকে নির্বাচন করুন");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        label1.setForeground(Color.black);
        label1.setBounds(195, 40, 319, 24);

        jPanel.add(label1);
    }
    private JRadioButton addTextWithRadioButton(Font banglaFont) {
        JRadioButton radioButton = new JRadioButton("১নং প্রার্থীর নাম");
        radioButton.setBounds(175, 90, 250, 25);
        radioButton.setFont(banglaFont.deriveFont(Font.BOLD, 15));
        radioButton.setBackground(new Color(0xA7FFAA));
        radioButton.setForeground(Color.BLACK);

        radioButton.addActionListener(e -> {

        });
        add(radioButton);
        return radioButton;
    }
    public static void main(String[] args) {
        new VotingPage(); //add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VotingPage frame = new VotingPage();
//        frame.setVisible(true);
    }
}
