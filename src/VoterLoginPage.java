import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoterLoginPage extends JFrame {
    private JPasswordField passwordField;
    private JTextField NidField;
    private JLabel ValidationErrorText;
    private JButton VoterLoginButton;
    private Connection connection;
    public VoterLoginPage() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setTitle("ভোটার একাউন্ট");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //window open in center
        setVisible(true);//comment this line if you want to run just this page
        getContentPane().setBackground(new Color(0x007355));

        // Add image
        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison.png");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(0, 0, 306, 360);
        add(Image);

        Font banglaFont = loadBanglaFont(); // Load Bangla font
        addTextFields(banglaFont); // Add text fields
        VoterLoginButton(banglaFont);// Add button
        HomeButton();// Add button
        VoterSignupButton(banglaFont);//add button

        addMouseListener(new java.awt.event.MouseAdapter() {// Request focus for the main panel to start without initial selection
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        getContentPane().requestFocusInWindow();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbabon_commission", "root", "");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "MySQL JDBC Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
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
        // First text field
        NidField = new JTextField();
        IdHintText(NidField, banglaFont);
        NidField.setBounds(340, 131, 227, 37);
        NidField.setBackground(new Color(0xD9D9D9));
        add(NidField);
        NidField.setForeground(Color.GRAY);

        ValidationErrorText = new JLabel();
        ValidationErrorText.setBounds(340, 98, 227, 37);
        add(ValidationErrorText);
        ValidationErrorText.setForeground(Color.white);
        NidField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String PATTERN = "[০-৯0-9]+";
                Pattern check = Pattern.compile(PATTERN);
                Matcher matcher = check.matcher(NidField.getText());
                if (!matcher.matches()) {
                    ValidationErrorText.setText("সংখ্যা লিখুন");
                    ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
                } else {
                    ValidationErrorText.setText(null);
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
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                }
            }
        });
    }

    private void IdHintText(JTextField textField, Font font) {
        textField.setText("এন আই ডি নাম্বার দিন");
        textField.setForeground(Color.GRAY);
        textField.setFont(font);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("এন আই ডি নাম্বার দিন")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    passwordField.setFont(font.deriveFont(Font.PLAIN, 17));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("এন আই ডি নাম্বার দিন");
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
    private void VoterLoginButton(Font banglaFont) {
        VoterLoginButton = new JButton("প্রবেশ করুন");
        VoterLoginButton.setBounds(385, 260, 132, 44);
        VoterLoginButton.setForeground(Color.BLACK);
        VoterLoginButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        VoterLoginButton.setBackground(new Color(0x5FFF95));

        add(VoterLoginButton);
        VoterLoginButton.addActionListener(e -> LoginDatabase());
    }
    private void VoterSignupButton(Font banglaFont) {
        JButton VoterSignupButton = new JButton("নিবন্ধন করুন");
        VoterSignupButton.setBounds(490, 10, 92, 34);
        VoterSignupButton.setForeground(Color.BLACK);
        VoterSignupButton.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        VoterSignupButton.setBackground(new Color(0xFFFFFF));
        VoterSignupButton.addActionListener(e -> {
            new VoterSignup();
            dispose();
        });
        add(VoterSignupButton);
    }

    private void LoginDatabase() {
        String idValue = NidField.getText();
        char[] passwordValue = passwordField.getPassword();

        String idReadSQL = "SELECT NcId FROM nclogin WHERE NcId = ?";
        String passReadSQL = "SELECT NcPass FROM nclogin WHERE NcPass = ?";

        try (PreparedStatement preparedStatementId = connection.prepareStatement(idReadSQL);
             PreparedStatement preparedStatementPass = connection.prepareStatement(passReadSQL)) {

            preparedStatementId.setString(1, idValue);
            preparedStatementPass.setString(1, String.valueOf(passwordValue));

            ResultSet resultSetId = preparedStatementId.executeQuery();
            ResultSet resultSetPass = preparedStatementPass.executeQuery();

            // Check if the ResultSet contains any data
            if (resultSetId.next() && resultSetPass.next()) {
                // page rout
                VoterLoginButton.addActionListener(e -> {
                    new VotingPage();
                    dispose();
                });
                System.out.println("Match found!");
            } else {
                ValidationErrorText.setText("ভুল! সঠিক আইডি ও পাসওয়ার্ড দিন");
            }
        } catch (SQLException exception) {
            ValidationErrorText.setText("কোড এর এস-কিউ-এল ভুল আছে");
        }
    }
    public static void main(String[] args) {
        new VoterLoginPage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VoterLoginPage frame = new VoterLoginPage();
//        frame.setVisible(true);
    }
}
