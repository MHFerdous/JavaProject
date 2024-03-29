import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VoterSignup extends JFrame {
    private JTextField nameTxtF,nidTxtF,emailTxtF,mobileTxtF;//need when database connect
    private JLabel ValidationErrorText,ErrText;
    private JPasswordField passwordField;

    private final Font banglaFont = loadBanglaFont();// Load Bangla banglaFont

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
        ValidErrorText();

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
        nameTxtF = createTextField("সম্পূর্ণ নাম.", 51,"^[a-zA-Z ]+|[অ-ঔক-য়\\s]+$","সঠিক নাম বাংলায় / ইংরেজিতে প্রদান করুন");
        nidTxtF = createTextField("এন আই ডি নাম্বার", 101,"^[0-9০-৯]{10}$","১০ ডিজিটের জাতীয় পরিচয়পত্র নম্বর দিন");
        passwordField = createPasswordField();
        emailTxtF = createTextField("ই-মেইল", 201,"^[a-z0-9]+@gmail\\.com$","সঠিক ই-মেইল প্রদান করুন");
        mobileTxtF = createTextField("মোবাইল নাম্বার", 251,"^((\\+88)?01[2-9]\\d{8})|((\\+৮৮)?০১[২-৯][০-৯]{8})$","+৮৮ সহ বা ছাড়া ১১ ডিজিটের নম্বর দিন");

    }

    private JTextField createTextField( String hintText, int y, String regex, String errorText) {
        JTextField textField = new JTextField(hintText);
        textField.setBounds(337, y, 227, 37);
        textField.setBackground(new Color(0xD9D9D9));
        textField.setForeground(Color.GRAY);
        textField.setFont(banglaFont.deriveFont(Font.PLAIN,17));
        add(textField);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(hintText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
                ErrText.setText("");

            }
            @Override
            public void focusLost(FocusEvent e) {
                ErrText = new JLabel("পূরণ করুন");
                ErrText.setBounds(500, y + 32, 227, 37);
                add(ErrText);
                ErrText.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                setComponentZOrder(ErrText, 0);

                if (textField.getText().isEmpty()) {
                    textField.setText(hintText);
                    textField.setForeground(Color.GRAY);
                    ErrText.setForeground(Color.RED);
                } else {
                    ErrText.setVisible(false); // Hide ErrorText if there's text
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validateTextField(textField, regex, errorText);//live error dekhaibo jokhono vul input dimu. ota add na korle button a click korle then error dekhte parlam one so ota problem. that's why live error show korar lagi ota use hoise.
            }
        });


        return textField;
    }


    private boolean validateAllFields() {
        return validateTextField(nameTxtF,"^[a-zA-Z ]+|[অ-ঔক-য়\\s]+$","সঠিক নাম বাংলায়/ইংরেজিতে প্রদান করুন") &&
                validateTextField(nidTxtF,"^[0-9০-৯]{10}$","১০ ডিজিটের জাতীয় পরিচয়পত্র নম্বর দিন") &&
                validatePassField() &&
                validateTextField(emailTxtF,"^[a-z0-9]+@gmail\\.com$","সঠিক ই-মেইল প্রদান করুন" ) &&
                validateTextField(mobileTxtF,"^((\\+88)?01[2-9]\\d{8})|((\\+৮৮)?০১[২-৯][০-৯]{8})$","+৮৮ সহ বা ছাড়া ১১ ডিজিটের নম্বর দিন" );
    }

    private boolean validateTextField(JTextField textField, String regex, String errorText) { // boolean validateAllFields method tone sob abar same pattarn and text catch korsi jate submit button a click korle aye boolen method call hoya check kore sob true ase ni.
        Pattern check = Pattern.compile(regex);
        Matcher matcher = check.matcher(textField.getText());
        if (!matcher.matches()) {
            ValidationErrorText.setText(errorText);
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
            return false;
        } else {
            ValidationErrorText.setText(null);
            return true;
        }
    }
    private boolean validatePassField() { // boolean validateAllFields method tone sob abar same pattarn and text catch korsi jate submit button a click korle aye boolen method call hoya check kore sob true ase ni.
        Pattern check = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?!.*[অ-ঔক-য়০-৯]).{8,}");

        Matcher matcher = check.matcher(String.valueOf(passwordField.getPassword()));
        if (!matcher.matches()) {
            ValidationErrorText.setText("সঠিক ভাবে পাসওয়ার্ড প্রদান করুন");
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
            return false;

        } else {
            ValidationErrorText.setText(null);
            return true;
        }
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
                ErrText.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {

                ErrText = new JLabel("পূরণ করুন");
                ErrText.setBounds(500, 183, 227, 37);
                add(ErrText);
                ErrText.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                setComponentZOrder(ErrText, 0);

                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("পাসওয়ার্ড");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                    passwordField.setForeground(Color.GRAY);
                    ErrText.setForeground(Color.RED);

                }else{
                    ErrText.setVisible(false); // Hide ErrorText if there's text
                }
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validatePassField();//live error dekhaibo jokhono vul input dimu. ota add na korle button a click korle then error dekhte parlam one so ota problem. that's why live error show korar lagi ota use hoise.
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
        BackButton.setBounds(312, 5, 22, 22);
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
        signButton.addActionListener(e ->
                {

                    if (validateAllFields()){
                        saveToDB();
                    }
                    else{
                        ValidationErrorText.setText("দুঃখিত! তথ্য সঠিকভাবে প্রদান করুন");
                        ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
                    }
                }
        );
    }

    private void saveToDB() {

        String name = nameTxtF.getText();
        String nid = nidTxtF.getText();
        String pass = String.valueOf(passwordField.getPassword());
        String email = emailTxtF.getText();
        String mobile = mobileTxtF.getText();

        VoterDbConnection dbConnect = new VoterDbConnection(banglaFont);
        if (dbConnect.VoterSignupDB(name,nid,pass,email,mobile)) {
            ValidationErrorText.setText("তথ্য সংরক্ষণ হয়েছে-একাউন্টে প্রবেশ করুণ");
            ValidationErrorText.setBounds(330, 23, 230, 37);
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));

            Timer timer = new Timer(2000, evt -> {
                new VoterLoginPage();
                dispose();
            });

            timer.setRepeats(false);
            timer.start();
        } else {
            ValidationErrorText.setText("দুঃখিত! ডাটাবেজে সংরক্ষণ হয়নি");
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
        }

    }

    private void ValidErrorText(){
        ValidationErrorText = new JLabel();
        ValidationErrorText.setBounds(336, 23, 230, 37);
        add(ValidationErrorText);
        ValidationErrorText.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        new VoterSignup();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VoterSignup frame = new VoterSignup();
//        frame.setVisible(true);
    }
}