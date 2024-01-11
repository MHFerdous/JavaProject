import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NcLoginPage extends JFrame {

    private JPasswordField passwordField;
    private JTextField idField;
    private JLabel idFieldValidation;

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
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 18); // Fallback to a default font
        }
    }

    private void addTextFields(Font banglaFont) {
        // ID text field
        idField = new JTextField();
        IdHintText(idField, banglaFont);
        idField.setBounds(340, 131, 227, 37);
        idField.setBackground(new Color(0xD9D9D9));
        add(idField);
        idField.setForeground(Color.GRAY);

        idFieldValidation = new JLabel();
        idFieldValidation.setBounds(340, 98, 227, 37);
        add(idFieldValidation);
        idFieldValidation.setForeground(Color.white);
        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String PATTERN = "[০-৯]+";
                Pattern check = Pattern.compile(PATTERN);
                Matcher matcher = check.matcher(idField.getText());
                if (!matcher.matches()) {
                    idFieldValidation.setText("বাংলায় সংখ্যা লিখুন");
                    idFieldValidation.setFont(banglaFont.deriveFont(Font.BOLD, 16));
                } else {
                    idFieldValidation.setText(null);
                }
            }
        });

        // Password text field
        passwordField = new JPasswordField();
        passwordField.setBounds(340, 181, 227, 37);
        passwordField.setBackground(new Color(0xD9D9D9));
        PasswordHintText(passwordField, banglaFont);
        add(passwordField);
        passwordField.addFocusListener(new FocusAdapter() { // show and hide password hintText
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("পাসওয়ার্ড দিন")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("পাসওয়ার্ড দিন");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.gray);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));

                }
            }
        });
    }

    private void IdHintText(JTextField textField, Font font) {
        textField.setText("নির্বাচন কমিশন আই ডি নাম্বার দিন");
        textField.setForeground(Color.GRAY);
        textField.setFont(font);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("নির্বাচন কমিশন আই ডি নাম্বার দিন")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setFont(font.deriveFont(Font.PLAIN, 17));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("নির্বাচন কমিশন আই ডি নাম্বার দিন");
                    textField.setForeground(Color.GRAY);
                    passwordField.setFont(font.deriveFont(Font.PLAIN, 17));
                }
            }
        });
    }
    private void PasswordHintText(JPasswordField passwordField, Font font) {
        passwordField.setText("পাসওয়ার্ড দিন");
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(font.deriveFont(Font.PLAIN, 17));
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
