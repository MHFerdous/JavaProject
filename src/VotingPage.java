import javax.swing.*;
import java.awt.*;
import java.io.*;

public class VotingPage extends JFrame {
    private final Font banglaFont = loadBanglaFont(); // Load Bangla banglaFont
    private JRadioButton radioButton = PrarthiTextWithRadioButton(); // need when connect with database

    public VotingPage(){
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setVisible(true);
        setTitle("ভোট প্রদান");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xFFFFFF));
        SignOutButton();
        voteButton();
        VoteDeleteButton();
        HeadingText();
    }
    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }
    private void HeadingText(){

        JPanel jPanel = new JPanel();
        jPanel.setBounds(165, 45, 270, 30);
        jPanel.setBackground(new Color(0x9CB2FF));
        add(jPanel);

        JLabel label1 = new JLabel("আপনার পছন্দের প্রার্থীকে নির্বাচন করুন");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        label1.setForeground(Color.black);

        jPanel.add(label1);
    }
    private void SignOutButton() {
        JButton SignOutButton = new JButton("প্রস্থান");
        SignOutButton.setBounds(520, 7, 71, 37);
        SignOutButton.setForeground(Color.WHITE);
        SignOutButton.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        SignOutButton.setBackground(new Color(0xFF0000));

        SignOutButton.addActionListener(e -> {
            MainPage loginFrame = new MainPage();
            loginFrame.setVisible(true);
            dispose();
        });

        add(SignOutButton);
    }

    private void voteButton() {
        JButton voteButton = new JButton("ভোট দিন");
        voteButton.setBounds(142, 300, 149, 42);
        voteButton.setForeground(Color.WHITE);
        voteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        voteButton.setBackground(new Color(0x045703));

        voteButton.addActionListener(e -> {
            
            //catch database that table row or colum and count++ 

        });


        add(voteButton);
    }
    private void VoteDeleteButton() {
        JButton VoteDeleteButton = new JButton("বাতিল");
        VoteDeleteButton.setBounds(320, 300, 149, 42);
        VoteDeleteButton.setForeground(Color.WHITE);
        VoteDeleteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        VoteDeleteButton.setBackground(new Color(0xFF1B1B));

        VoteDeleteButton.addActionListener(e -> {
            
            //catch database and delete the table

        });

        add(VoteDeleteButton);
    }
    private JRadioButton PrarthiTextWithRadioButton() {
        radioButton = new JRadioButton("১নং প্রার্থীর নাম");
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
