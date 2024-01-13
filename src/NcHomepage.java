import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NcHomepage extends JFrame {
    private JTextField Address; //need when database connect
    private JTextField Name; //need when database connect
    private JTextField Nid; //need when database connect
    private JTextField MobileNumber;//need when database connect
    private JTextField Protik;//need when database connect
    public NcHomepage() {

        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("বাংলাদেশ নির্বাচন কমিশন");
        getContentPane().setBackground(new Color(0xC4E4DF));

        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/Vote_Box.jpg");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(23, 62, 263, 237);
        add(Image);

        Font banglaFont = loadBanglaFont();
        HomeButton(banglaFont);
        SubmitButton(banglaFont);
        ElectionResultButton(banglaFont);
        textFields(banglaFont);
        HeadingText(banglaFont);
        addMouseListener(new java.awt.event.MouseAdapter() {// Request focus for the main panel to start without initial selection
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        getContentPane().requestFocusInWindow();
    }
    private void formMouseClicked() {
        getContentPane().requestFocusInWindow();
    }
    private void HeadingText(Font banglaFont) {
        JLabel HeadingText = new JLabel();
        HeadingText.setText("নির্বাচন কমিশন বিভাগ");
        HeadingText.setBounds(216,15,179,24);
        HeadingText.setFont(banglaFont.deriveFont(Font.BOLD, 24));
        add(HeadingText);
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 18);
        }}
    private void textFields(Font font) {
        Name = createTextField("প্রার্থীর সম্পূর্ণ নাম", 63, font);
        Nid = createTextField("প্রার্থীর এন আই ডি নাম্বার", 113, font);
        Address = createTextField("প্রার্থীর ঠিকানা", 163, font);
        MobileNumber = createTextField("প্রার্থীর মোবাইল নাম্বার", 213, font);
        Protik = createTextField("প্রার্থীর প্রতীক", 263, font);
    }
    private JTextField createTextField(String hintText, int y, Font font) {
        JTextField textField = new JTextField(hintText);
        textField.setBounds(312, y, 227, 37);
        textField.setBackground(Color.white);
        textField.setForeground(Color.GRAY);
        textField.setFont(font);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(hintText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(hintText);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        add(textField);
        return textField;
    }
    private void HomeButton(Font banglaFont) {
        JButton HomeButton = new JButton("প্রস্থান");
        HomeButton.setBounds(10, 8, 70, 35);
        HomeButton.setForeground(Color.WHITE);
        HomeButton.setFont(banglaFont.deriveFont(Font.BOLD, 17));
        HomeButton.setBackground(Color.red);

        HomeButton.addActionListener(e -> {
            MainPage frame = new MainPage();
            dispose();
            frame.setVisible(true);
        });

        add(HomeButton);
    }


    private void SubmitButton(Font banglaFont) {
        JButton LogButton1 = new JButton("সংরক্ষণ");
        LogButton1.setBounds(373, 310, 102, 44);
        LogButton1.setForeground(Color.BLACK);
        LogButton1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        LogButton1.setBackground(new Color(0x5FFF95));

        LogButton1.addActionListener(e -> {
            // Send data to Firebase using textField1.getText() and passwordField.getPassword()

            dispose();
        });

        add(LogButton1);
    }
    private void ElectionResultButton(Font banglaFont) {
        JButton LogButton1 = new JButton("নির্বাচনী ফলাফল");
        LogButton1.setBounds(72, 310, 159, 44);
        LogButton1.setForeground(Color.WHITE);
        LogButton1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        LogButton1.setBackground(new Color(0x037C5A));

        LogButton1.addActionListener(e -> {
            // Send data to Firebase using textField1.getText() and passwordField.getPassword()

            dispose();
        });

        add(LogButton1);
    }

    public static void main(String[] args) {
        new NcHomepage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        NcHomepage frame = new NcHomepage();
//        frame.setVisible(true);
    }
}
