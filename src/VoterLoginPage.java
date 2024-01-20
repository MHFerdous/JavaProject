import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoterLoginPage extends JFrame {
    private JPasswordField passwordField;
    private JTextField NidField;
    private JLabel ValidationErrorText;
    private final Font banglaFont = loadBanglaFont(); // Load Bangla banglaFont
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

        addTextFields(); // Add text fields
        VoterLoginButton();// Add button
        HomeButton();// Add button
        VoterSignupButton();//add button

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

    private void addTextFields() {
        NidField = new JTextField();
        IdHintText(NidField);
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
                String PATTERN = "[০-৯0-9]{10}";
                Pattern check = Pattern.compile(PATTERN);
                Matcher matcher = check.matcher(NidField.getText());
                if (!matcher.matches()) {
                    ValidationErrorText.setText("১০ ডিজিটের জাতীয় পরিচয়পত্র নম্বর দিন");
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

    private void IdHintText(JTextField textField) {
        textField.setText("এন আই ডি নাম্বার দিন");
        textField.setForeground(Color.GRAY);
        textField.setFont(banglaFont);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("এন আই ডি নাম্বার দিন")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("এন আই ডি নাম্বার দিন");
                    textField.setForeground(Color.GRAY);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
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
    private void VoterLoginButton() {
        JButton voterLoginButton = new JButton("প্রবেশ করুন");
        voterLoginButton.setBounds(385, 260, 132, 44);
        voterLoginButton.setForeground(Color.BLACK);
        voterLoginButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        voterLoginButton.setBackground(new Color(0x5FFF95));

        add(voterLoginButton);
        voterLoginButton.addActionListener(e -> {
            VoterDbConnection dbConnect = new VoterDbConnection(banglaFont);
            if (dbConnect.voterlogin(NidField, passwordField)) {
                ValidationErrorText.setText("একাউন্টে প্রবেশ করা হচ্ছে");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));

                Timer timer = new Timer(2000, evt -> {
                    new VotingPage(NidField);
                    dispose();
                });

                timer.setRepeats(false);
                timer.start();
            } else {
                ValidationErrorText.setText("ভুল! সঠিক আইডি ও পাসওয়ার্ড দিন");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
            }
        });
    }

    private void VoterSignupButton() {
        JButton VoterSignupButton = new JButton("নিবন্ধন করুন");
        VoterSignupButton.setBounds(490, 10, 92, 35);
        VoterSignupButton.setForeground(Color.BLACK);
        VoterSignupButton.setFont(banglaFont.deriveFont(Font.BOLD, 13));
        VoterSignupButton.setBackground(new Color(0xFFFFFF));
        VoterSignupButton.addActionListener(e -> {
            new VoterSignup();
            dispose();
        });
        add(VoterSignupButton);
    }


    public static void main(String[] args) {
       new VoterLoginPage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VoterLoginPage frame = new VoterLoginPage();
//        frame.setVisible(true);
    }
}
