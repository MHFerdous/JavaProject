import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NcLoginPage extends JFrame {

    private JPasswordField passwordField;

    public NcLoginPage() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setTitle("বাংলাদেশ নির্বাচন কমিশন");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //window open in center
        setVisible(true);
        getContentPane().setBackground(new Color(0x007355));

        // Add image
        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison.png");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(0, 0, 306, 360);
        add(Image);

        Font banglaFont = loadBanglaFont(); // Load Bangla font
        addTextFields(banglaFont); // Add text fields
        
        NcLoginButton(banglaFont);// Add button
        HomeButton();// Add button

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

    private void HomeButton() {
        JButton HomeButton = new JButton(new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/home-icon.jpg"));
        HomeButton.setBounds(314, 10, 20, 20);
        HomeButton.addActionListener(e -> {
            MainPage frame = new MainPage();
            dispose();
            frame.setVisible(true);
        });
        add(HomeButton);
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.BOLD, 14);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }

    private void addTextFields(Font banglaFont) {
        // ID text field
        JTextField idField = new JTextField();
        IdHintText(idField, banglaFont);
        idField.setBounds(340, 131, 227, 37);
        idField.setBackground(new Color(0xD9D9D9));
        add(idField);
        idField.setForeground(Color.GRAY);

        // Password text field
        passwordField = new JPasswordField();
        passwordField.setBounds(340, 181, 227, 37);
        passwordField.setBackground(new Color(0xD9D9D9));
        PasswordHintText(passwordField, banglaFont);
        add(passwordField);
        passwordField.addFocusListener(new FocusAdapter() { // show and hide password hintText
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("পাসওয়ার্ড")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.gray);
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
    private void IdHintText(JTextField textField, Font font) {
        textField.setText("নির্বাচন কমিশন আই ডি নাম্বার");
        textField.setForeground(Color.GRAY);
        textField.setFont(font);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("নির্বাচন কমিশন আই ডি নাম্বার")) {
                    textField.setText("");
                    textField.setForeground(Color.GRAY);
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
    private void PasswordHintText(JPasswordField passwordField, Font font) {
        passwordField.setText("পাসওয়ার্ড");
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(font.deriveFont(Font.PLAIN, 14));
        passwordField.setEchoChar((char) 0);
    }

    private void NcLoginButton(Font banglaFont) {
        JButton NcLoginButton = new JButton("প্রবেশ করুন");
        NcLoginButton.setBounds(385, 260, 132, 44);
        NcLoginButton.setForeground(Color.BLACK);
        NcLoginButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        NcLoginButton.setBackground(new Color(0x5FFF95));

        add(NcLoginButton);
    }

    public static void main(String[] args) {
        new NcLoginPage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        NcLoginPage frame = new NcLoginPage();
//        frame.setVisible(true);
    }
}
