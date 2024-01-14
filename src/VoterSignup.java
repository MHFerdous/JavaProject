import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class VoterSignup extends JFrame {
    private JPasswordField passTxtF; //need when database connect
    private JTextField nameTxtF; //need when database connect
    private JTextField nidTxtF; //need when database connect
    private JTextField emailTxtF;//need when database connect
    private JTextField mobileTxtF;//need when database connect
    private final Font banglaFont = loadBanglaFont(); // Load Bangla banglaFont
    private JPasswordField passwordField;
    public VoterSignup() {
        setSize(612, 400);
        setTitle("ভোটার নিবন্ধন");
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(new Color(0x007355));
        setLayout(null);
        setImageIcons();
        BackButton();
        textFields();
        signupButton();
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
    private void setImageIcons() {
        // Add image
        ImageIcon image = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison.png");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(0, 0, 306, 363);
        add(imageLabel);

        // Add frameIcon
        ImageIcon frameIcon = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png");
        setIconImage(frameIcon.getImage());
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 17); // Fallback to a default font
        }
    }

    private void textFields() {
        nameTxtF = createTextField("সম্পূর্ণ নাম", 51);
        nidTxtF = createTextField("এন আই ডি নাম্বার", 101);
        passTxtF = createPasswordField();
        emailTxtF = createTextField("ই-মেইল", 201);
        mobileTxtF = createTextField("মোবাইল নাম্বার", 251);
    }

    private JTextField createTextField(String hintText, int y) {
        JTextField textField = new JTextField(hintText);
        textField.setBounds(337, y, 227, 37);
        textField.setBackground(new Color(0xD9D9D9));
        textField.setForeground(Color.GRAY);
        textField.setFont(banglaFont.deriveFont(Font.PLAIN,17));

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

    private JPasswordField createPasswordField() {
        passwordField = new JPasswordField();
        passwordField.setBounds(337, 151, 227, 37);
        passwordField.setBackground(new Color(0xD9D9D9));
        passwordField.setFont(banglaFont.deriveFont(Font.PLAIN,17));
        PasswordHintText(passwordField);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("পাসওয়ার্ড")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("পাসওয়ার্ড");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
        add(passwordField);
        return passwordField;
    }
    private void PasswordHintText(JPasswordField passTxtF) {
        passTxtF.setText("পাসওয়ার্ড");
        passTxtF.setForeground(Color.GRAY);
        passTxtF.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
        passTxtF.setEchoChar((char) 0);
    }
    private void BackButton() {
        ImageIcon homeIconButton = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/home-icon.jpg");
        JButton BackButton = new JButton(homeIconButton);
        BackButton.setBounds(317, 10, 22, 22);
        add(BackButton);

        BackButton.addActionListener(e -> {
            new VoterLoginPage();
            dispose();
        });
    }
    private void signupButton() {
        JButton signButton = new JButton("নিবন্ধন করুন");
        signButton.setBounds(385,300,132,44);
        signButton.setForeground(Color.BLACK);
        signButton.setBackground(new Color(0x5FFF95));
        signButton.setFont(banglaFont.deriveFont(Font.BOLD,20));
        add(signButton);
        signButton.addActionListener(e -> System.out.println("Signup Button clicked"));
    }
    public static void main(String[] args) {
        new VoterSignup();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VoterSignup frame = new VoterSignup();
//        frame.setVisible(true);
    }
}