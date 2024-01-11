import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class voting_page extends JFrame {

    public voting_page(){
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        getContentPane().setBackground(new Color(0xFFFFFF));

        Font banglaFont = loadBanglaFont();
        HomeButton2();
        BackButton1(banglaFont);
        voteButton(banglaFont);
        cancelButton(banglaFont);
        addTextField(banglaFont);

        JRadioButton radioButton1 = addTextWithRadioButton(banglaFont);
    }
    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("F:/Study files/JavaProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }
    private void HomeButton2() {
        JButton homeButton2 = new JButton(new ImageIcon("F:/Study files/JavaProject/img/home-icon.jpg"));
        homeButton2.setBounds(23, 16, 20, 20);
        homeButton2.setBorderPainted(false);
        homeButton2.setContentAreaFilled(false);
        homeButton2.setFocusPainted(false);

        homeButton2.addActionListener(e -> {
            Main mainFrame = new Main();
            mainFrame.setTitle("Bangladesh Online Voting System");
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null); //window open in center
            mainFrame.setVisible(true);
            dispose();
        });

        add(homeButton2);
    }
    private void BackButton1(Font banglaFont) {
        JButton BackButton1 = new JButton("প্রস্থান");
        BackButton1.setBounds(520, 7, 71, 37);
        BackButton1.setForeground(Color.WHITE);
        BackButton1.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        BackButton1.setBackground(new Color(0xFF0000));

        BackButton1.addActionListener(e -> {
            login2 loginFrame = new login2();
            loginFrame.setTitle("Bangladesh Online Voting System");
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            loginFrame.setLocationRelativeTo(null); //window open in center
            loginFrame.setVisible(true);
            dispose();
        });

        add(BackButton1);
    }

    private void voteButton(Font banglaFont) {
        JButton button = new JButton("ভোট দিন");
        button.setBounds(142, 300, 149, 42);
        button.setForeground(Color.WHITE);
        button.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        button.setBackground(new Color(0x045703));

        button.addActionListener(e -> {

        });


        add(button);
    }
    private void cancelButton(Font banglaFont) {
        JButton button = new JButton("বাতিল");
        button.setBounds(320, 300, 149, 42);
        button.setForeground(Color.WHITE);
        button.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        button.setBackground(new Color(0xFF1B1B));

        button.addActionListener(e -> {

        });

        add(button);
    }

    private void panel(){

    }
    private void addTextField(Font banglaFont){

        JPanel jPanel = new JPanel();
        jPanel.setBounds(150, 43, 270, 30);
        jPanel.setBackground(new Color(0x9CB2FF));
        add(jPanel);

        JLabel label1 = new JLabel("আপনার পছন্দের প্রার্থীকে নির্বাচন করুন");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        label1.setForeground(new Color(0x545454));
        label1.setBounds(180, 40, 319, 24);

        jPanel.add(label1);
    }
    private JRadioButton addTextWithRadioButton(Font banglaFont) {
        JRadioButton radioButton = new JRadioButton("১নং প্রার্থীর নাম");
        radioButton.setBounds(160, 90, 250, 25);
        radioButton.setFont(banglaFont.deriveFont(Font.BOLD, 15));
        radioButton.setBackground(new Color(0xA7FFAA));
        radioButton.setForeground(Color.BLACK);

        radioButton.addActionListener(e -> {

        });
        add(radioButton);
        return radioButton;
    }

}
