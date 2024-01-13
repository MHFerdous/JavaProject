import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NcLoginPage extends JFrame {

    private JPasswordField passwordField;
    private JTextField idField;
    private JLabel ValidationErrorText;
    private Connection connection;
    private final Font banglaFont = loadBanglaFont(); // Load Bangla banglaFont

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

        addTextFields(); // Add text fields
        
        NcLoginButton();// Add button
        HomeButton();// Add button

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
            return new Font("Arial", Font.PLAIN, 18); // Fallback to a default banglaFont
        }
    }

    private void addTextFields() {
        // ID text field
        idField = new JTextField();
        IdHintText(idField);
        idField.setBounds(340, 131, 227, 37);
        idField.setBackground(new Color(0xD9D9D9));
        add(idField);
        idField.setForeground(Color.GRAY);

        ValidationErrorText = new JLabel();
        ValidationErrorText.setBounds(340, 98, 227, 37);
        add(ValidationErrorText);
        ValidationErrorText.setForeground(Color.white);
        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String PATTERN = "[০-৯0-9]+";
                Pattern check = Pattern.compile(PATTERN);
                Matcher matcher = check.matcher(idField.getText());
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
        PasswordHintText(passwordField);
        add(passwordField);
        passwordField.addFocusListener(new FocusAdapter() { // show and hide password hintText
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("পাসওয়ার্ড দিন")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("পাসওয়ার্ড দিন");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.gray);

                }
            }
        });
    }

    private void IdHintText(JTextField textField) {
        textField.setText("নির্বাচন কমিশন আই ডি নাম্বার দিন");
        textField.setForeground(Color.GRAY);
        textField.setFont(banglaFont);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("নির্বাচন কমিশন আই ডি নাম্বার দিন")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("নির্বাচন কমিশন আই ডি নাম্বার দিন");
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    private void PasswordHintText(JPasswordField passwordField) {
        passwordField.setText("পাসওয়ার্ড দিন");
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
        passwordField.setEchoChar((char) 0);
    }

    private void NcLoginButton() {
        JButton NcLoginButton = new JButton("প্রবেশ করুন");
        NcLoginButton.setBounds(385, 260, 132, 44);
        NcLoginButton.setForeground(Color.BLACK);
        NcLoginButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        NcLoginButton.setBackground(new Color(0x5FFF95));
        add(NcLoginButton);
        NcLoginButton.addActionListener(e -> LoginDatabase());
    }
    private void LoginDatabase() {
        String idValue = idField.getText();
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
                SwingUtilities.invokeLater(() -> {
                    ValidationErrorText.setText("একাউন্টে প্রবেশ করা হচ্ছে");
                    ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
                });

                // Start a separate thread to wait for 3 seconds
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    SwingUtilities.invokeLater(() -> {
                        new NcHomepage();
                        dispose();
                    });
                }).start();
            }
            else {
                ValidationErrorText.setText("ভুল! সঠিক আইডি ও পাসওয়ার্ড দিন");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
            }
        } catch (SQLException exception) {
            ValidationErrorText.setText("কোড এর এস-কিউ-এল ভুল আছে");
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
        }
    }




    public static void main(String[] args) {
        new NcLoginPage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        NcLoginPage frame = new NcLoginPage();
//        frame.setVisible(true);

    }
}
