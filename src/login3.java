import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

public class login3 extends JFrame {

    public login3() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        getContentPane().setBackground(new Color(0x007355));

        // Add image
        ImageIcon imageIcon = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 306, 360);
        add(imageLabel);

        Font banglaFont = loadBanglaFont(); // Load Bangla font
        addTextFields(banglaFont); // Add text fields
        // Add buttons
        AddLogButton3(banglaFont);
        HomeButton3();
        AddSignupButton2(banglaFont);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        // Request focus for the main panel to start without initial selection
        getContentPane().requestFocusInWindow();
    }
    private void HomeButton3() {
        JButton homeButton3 = new JButton(new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/home-icon.jpg"));
        homeButton3.setBounds(314, 10, 20, 20);
        homeButton3.setBorderPainted(false);
        homeButton3.setContentAreaFilled(false);
        homeButton3.setFocusPainted(false);

        homeButton3.addActionListener(e -> {
            Main loginFrame = new Main();
            loginFrame.setTitle("Bangladesh Online Voting System");
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            loginFrame.setVisible(true);
            dispose();
        });

        add(homeButton3);
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 14);
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

        // Set hint text for the first text field

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
        textField.setText("এন আই ডি নাম্বার");
        textField.setForeground(Color.GRAY);
        textField.setFont(font);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("এন আই ডি নাম্বার")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("এন আই ডি নাম্বার");
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void AddLogButton3(Font banglaFont) {
        JButton button = new JButton("প্রবেশ করুন");
        button.setBounds(385, 260, 132, 44);
        button.setForeground(Color.BLACK);
        button.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        button.setBackground(new Color(0x5FFF95));

        button.addActionListener(e -> {
            // Send data to Firebase using textField1.getText() and passwordField.getPassword()
            System.out.println("Data sent to Firebase");
        });

        add(button);
    }
    private void AddSignupButton2(Font banglaFont) {
        JButton SignupButton2 = new JButton("নিবন্ধন করুন");
        SignupButton2.setBounds(490, 10, 92, 34);
        SignupButton2.setForeground(Color.BLACK);
        SignupButton2.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        SignupButton2.setBackground(new Color(0xFFFFFF));

        SignupButton2.addActionListener(e -> {
            // go to reg2
            System.out.println("reg3");
        });

        add(SignupButton2);
    }

    private void formMouseClicked() {
        getContentPane().requestFocusInWindow();
    }

    public static void main(String[] args) {
        login3 frame = new login3();
        frame.setTitle("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
