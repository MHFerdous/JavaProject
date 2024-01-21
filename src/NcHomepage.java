import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NcHomepage extends JFrame {
    private JTextField Address,Name,Nid,MobileNumber,Protik;// need when database connect
    private JLabel ValidationErrorText, ErrorText;
    private final Font banglaFont = loadBanglaFont(); // Load Bangla font

    private Connection connection;

    public NcHomepage() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("বাংলাদেশ নির্বাচন কমিশন");
        getContentPane().setBackground(new Color(0xC4E4DF));
        setVisible(true);//comment this line if you want to run just this page
        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/Vote_Box.jpg");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(23, 62, 263, 237);
        add(Image);
        ImageIcon frameIcon = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png");
        setIconImage(frameIcon.getImage());

        HomeButton();
        SubmitButton();
        ElectionResultButton();
        textFields();
        HeadingText();
        ValidationErrorText();
        Poriborton();
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        getContentPane().requestFocusInWindow();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
        } catch (ClassNotFoundException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>মাইএসকুয়েল জেডবিসি ড্রাইভার পাওয়া যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "্রাইভার ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>ডাটাবেসে সংযোগ স্থাপন করা যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ডাটাবেসে ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void formMouseClicked() {
        getContentPane().requestFocusInWindow();
    }

    private void HeadingText() {
        JLabel HeadingText = new JLabel();
        HeadingText.setText("নির্বাচন কমিশন বিভাগ");
        HeadingText.setForeground(Color.black);
        HeadingText.setBounds(216, 15, 179, 24);
        HeadingText.setFont(banglaFont.deriveFont(Font.BOLD, 24));
        add(HeadingText);
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 18);
        }
    }

    private void textFields() {
        Name = createTextField("প্রার্থীর সম্পূর্ণ নাম.", 63, "^[a-zA-Z ]+|[অ-ঔক-য়\\s]+$","সঠিক নাম বাংলায়/ইংরেজিতে প্রদান করুন");
        Nid = createTextField("প্রার্থীর এন আই ডি নাম্বার", 113, "^[0-9০-৯]{10}$","১০ ডিজিটের জাতীয় পরিচয়পত্র নম্বর দিন");
        Address = createTextField("প্রার্থীর ঠিকানা.", 163, "^[a-zA-Z0-9 ]+|[০-৯অ-ঔক-য়\\s]+$", "সঠিক ঠিকানা প্রদান করুন");
        MobileNumber = createTextField("প্রার্থীর মোবাইল নাম্বার", 213, "^((\\+88)?01[2-9]\\d{8})|((\\+৮৮)?০১[২-৯][০-৯]{8})$","+৮৮০ সহ বা ছাড়া ১১ ডিজিটের নম্বর দিন");
        Protik = createTextField("প্রার্থীর প্রতীক.", 263, "^[a-zA-Z ]+|[অ-ঔক-য়\\s]+$","সঠিক প্রতীক নাম বাংলায়/ইংরেজিতে দিন");
    }

    private JTextField createTextField(String hintText, int y, String pattern, String errorText) {//ono pattern and errorText catch korsi karon jate live error dekhaite pari. ono  textField.addKeyListener use kora hoise.
        JTextField textField = new JTextField(hintText);
        textField.setBounds(312, y, 227, 37);
        textField.setBackground(Color.white);
        textField.setForeground(Color.GRAY);
        textField.setFont(banglaFont.deriveFont(Font.BOLD, 17));

        textField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(hintText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
                ErrorText.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                ErrorText = new JLabel("পূরণ করুন");
                ErrorText.setBounds(480, y + 30, 227, 37);
                add(ErrorText);
                ErrorText.setFont(banglaFont.deriveFont(Font.PLAIN, 17));
                setComponentZOrder(ErrorText, 0);

                if (textField.getText().isEmpty()) {
                    textField.setText(hintText);
                    textField.setForeground(Color.GRAY);
                    ErrorText.setForeground(Color.RED);
                } else {
                    ErrorText.setVisible(false); // Hide ErrorText if there's text
                }
            }

        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validateTextField(textField, pattern, errorText);//live error dekhaibo jokhono vul input dimu. ota add na korle button a click korle then error dekhte parlam one so ota problem. that's why live error show korar lagi ota use hoise.
            }
        });

        add(textField);
        return textField;
    }

    private boolean validateAllFields() {
        return validateTextField(Name, "^[a-zA-Z ]+|[অ-ঔক-য়\\s]+$","সঠিক নাম বাংলায়/ইংরেজিতে প্রদান করুন") &&
                validateTextField(Nid, "^[0-9০-৯]{10}$","১০ ডিজিটের জাতীয় পরিচয়পত্র নম্বর দিন") &&
                validateTextField(Address, "^[a-zA-Z0-9 ]+|[০-৯অ-ঔক-য়\\s]+$", "সঠিক ঠিকানা প্রদান করুন") &&
                validateTextField(MobileNumber, "^((\\+88)?01[2-9]\\d{8})|((\\+৮৮)?০১[২-৯][০-৯]{8})$","+৮৮০ সহ বা ছাড়া ১১ ডিজিটের নম্বর দিন") &&
                validateTextField(Protik, "^[a-zA-Z ]+|[অ-ঔক-য়\\s]+$","সঠিক প্রতীক নাম বাংলায়/ইংরেজিতে দিন");
    }
    private boolean validateTextField(JTextField textField, String pattern, String text) { // boolean validateAllFields method tone sob abar same pattarn and text catch korsi jate submit button a click korle aye boolen method call hoya check kore sob true ase ni.
        Pattern check = Pattern.compile(pattern);
        Matcher matcher = check.matcher(textField.getText());
        if (!matcher.matches()) {
            ValidationErrorText.setText(text);
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
            return false;
        }  else {
            ValidationErrorText.setText(null);
            return true;
        }
    }
    private void SubmitButton() {
        JButton ResultButton = new JButton("মনোনয়ন দাখিল");
        ResultButton.setBounds(353, 310, 145, 44);
        ResultButton.setForeground(Color.BLACK);
        ResultButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        ResultButton.setBackground(new Color(0x5FFF95));

        ResultButton.addActionListener(e -> {
            if (validateAllFields()) {//boolean method a giya dekbo sob true ni. hoile save korbo
                saveToDatabase();
            }
            else {
                ValidationErrorText.setText("দুঃখিত! তথ্য সঠিকভাবে প্রদান করুন");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
            }
        });

        add(ResultButton);
    }
    private void saveToDatabase() {
        try {
            String insertSQL = "INSERT INTO nchome (Prarthi_Name, Prarthi_Nid, Prarthi_Thikana, Prarthi_Mobile_Number, Protik) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, Name.getText());
                preparedStatement.setString(2, Nid.getText());
                preparedStatement.setString(3, Address.getText());
                preparedStatement.setString(4, MobileNumber.getText());
                preparedStatement.setString(5, Protik.getText());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    ValidationErrorText.setText("ডাটাবেজে সংরক্ষণ হয়েছে");
                    ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));

                } else {
                    ValidationErrorText.setText("ডাটাবেজে সংরক্ষণ হয়নি");
                    ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
                }
            }
        } catch (SQLException e) {
            JLabel label = new JLabel("ভুল! ডাটাবেজ সংরক্ষণ হয়নি");
            label.setFont(banglaFont);
            JOptionPane.showMessageDialog(null, label, "ডাটাবেস ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void ElectionResultButton() {
        JButton ResultButton = new JButton("নির্বাচনী ফলাফল");
        ResultButton.setBounds(72, 310, 159, 44);
        ResultButton.setForeground(Color.WHITE);
        ResultButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        ResultButton.setBackground(new Color(0x037C5A));

        ResultButton.addActionListener(e -> {
            try {
                new Result();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });

        add(ResultButton);
    }
    private void HomeButton() {
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


    private void ValidationErrorText(){
        ValidationErrorText = new JLabel();
        ValidationErrorText.setBounds(312, 35, 227, 37);
        add(ValidationErrorText);
        ValidationErrorText.setForeground(Color.RED);
    }
    private void Poriborton() {
        JButton backButton = new JButton("পরিবর্তন");
        backButton.setBounds(520, 8, 70, 35);
        backButton.setForeground(Color.black);
        backButton.setFont(banglaFont.deriveFont(Font.BOLD, 10));
        backButton.setBackground(Color.GREEN);

        backButton.addActionListener(e -> {
            new NcDatabase();
            dispose();
        });

        add(backButton);
    }


    public static void main(String[] args) {
        new NcHomepage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        NcHomepage frame = new NcHomepage();
//        frame.setVisible(true);
    }
}
