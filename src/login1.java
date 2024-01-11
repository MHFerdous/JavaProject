import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

public class login1 extends JFrame {

    public login1() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        getContentPane().setBackground(new Color(0x007355));

        // Add image
        ImageIcon imageIcon = new ImageIcon("F:/Study files/JavaProject/img/nirbacon_commison.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 306, 360);
        add(imageLabel);

        Font banglaFont = loadBanglaFont(); // Load Bangla font
        addTextFields(banglaFont); // Add text fields
        // Add buttons
        AddLogButton1(banglaFont);
        HomeButton();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        // Request focus for the main panel to start without initial selection
        getContentPane().requestFocusInWindow();
    }
    private void HomeButton() {
        JButton homeButton = new JButton(new ImageIcon("F:/Study files/JavaProject/img/home-icon.jpg"));
        homeButton.setBounds(314, 10, 20, 20);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);

        homeButton.addActionListener(e -> {
            Main mainFrame = new Main();
            mainFrame.setTitle("Bangladesh Online Voting System");
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null); //window open in center
            mainFrame.setVisible(true);
            dispose();
        });

        add(homeButton);
    }
    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("F:/Study files/JavaProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }

    private void addTextFields(Font banglaFont) {
        // First text field
        JLabel label1 = new JLabel();
        label1.setFont(banglaFont.deriveFont(Font.PLAIN, 14));
        label1.setForeground(new Color(0x545454));
        label1.setBounds(350, 142, 227, 16);
        add(label1);

        JTextField textField1 = new JTextField();
        setHintText(textField1, banglaFont);
        textField1.setBounds(340, 131, 227, 37);
        textField1.setBackground(new Color(0xD9D9D9));
        add(textField1);
        textField1.setForeground(Color.GRAY);

        // Second text field
        JLabel label2 = new JLabel("পাসওয়ার্ড");
        label2.setFont(banglaFont.deriveFont(Font.PLAIN, 14));
        label2.setForeground(new Color(0x545454));
        label2.setBounds(350, 192, 53, 16);
        add(label2);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(340, 181, 227, 37);
        passwordField.setBackground(new Color(0xD9D9D9));
        add(passwordField);

        // Set hint text for the second text field
        passwordField.setText("পাসওয়ার্ড");
        passwordField.setForeground(Color.GRAY);

        // Focus adapter to show/hide password hint
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("পাসওয়ার্ড")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("পাসওয়ার্ড");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 14));
                }
            }
        });
    }

    private void setHintText(JTextField textField, Font font) {
        textField.setText("নির্বাচন কমিশন আই ডি নাম্বার");
        textField.setForeground(Color.GRAY);
        textField.setFont(font);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("নির্বাচন কমিশন আই ডি নাম্বার")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("নির্বাচন কমিশন আই ডি নাম্বার");
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void AddLogButton1(Font banglaFont) {
        JButton LogButton1 = new JButton("প্রবেশ করুন");
        LogButton1.setBounds(385, 260, 132, 44);
        LogButton1.setForeground(Color.BLACK);
        LogButton1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        LogButton1.setBackground(new Color(0x5FFF95));

        LogButton1.addActionListener(e -> {
            // Send data to Firebase using textField1.getText() and passwordField.getPassword()
            System.out.println("Data sent to Firebase");
        });

        add(LogButton1);
    }

    private void formMouseClicked() {
        getContentPane().requestFocusInWindow();
    }

    public static void main(String[] args) {
            login1 frame = new login1();

            //if you want to check just this page then remove below code comment otherwish it was call from main page
//            frame.setTitle("");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLocationRelativeTo(null); //window open in center
//            frame.setVisible(true);
    }
}
