// chnage this a validation pattar use koris sobti
// then if else use koris jodi validation na hoy taile jate vul ta database a na jay.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NcHomepage extends JFrame {
    private JTextField Address,Name,Nid,MobileNumber,Protik;// need when database connect
    private JLabel ValidationErrorText, ErrorText;;
    private final Font banglaFont = loadBanglaFont(); // Load Bangla font

    private Connection connection;

    public NcHomepage() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("বাংলাদেশ নির্বাচন কমিশন");
        getContentPane().setBackground(new Color(0xC4E4DF));

        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/Vote_Box.jpg");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(23, 62, 263, 237);
        add(Image);

        HomeButton();
        SubmitButton();
        ElectionResultButton();
        textFields();
        HeadingText();
        ValidationErrorText();
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        getContentPane().requestFocusInWindow();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
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
        Name = createTextField("প্রার্থীর সম্পূর্ণ নাম", 63, "[০-৯0-9]+", "সংখ্যা না");
        Nid = createTextField("প্রার্থীর এন আই ডি নাম্বার", 113, "[০-৯0-9]+", "সংখ্যা");
        Address = createTextField("প্রার্থীর ঠিকানা", 163, "[a-zআ-ওক-য়]+", "ঠিকানা");
        MobileNumber = createTextField("প্রার্থীর মোবাইল নাম্বার", 213, "[০-৯0-9]+", "মোবাইল");
        Protik = createTextField("প্রার্থীর প্রতীক", 263, "[০-৯0-9]+", "সংখ্যা না");
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
        return validateTextField(Name, "[০-৯0-9]+", "সংখ্যা না") &&
                validateTextField(Nid, "[০-৯0-9]+", "সংখ্যা") &&
                validateTextField(Address, "[a-zআ-ওক-য়]+", "ঠিকানা") &&
                validateTextField(MobileNumber, "[০-৯0-9]+", "মোবাইল") &&
                validateTextField(Protik, "[০-৯0-9]+", "সংখ্যা না");
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
                    ValidationErrorText.setText("ডাটাবেজ সংরক্ষণ হয়েছে");
                    ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));

                } else {
                    ValidationErrorText.setText("ডাটাবেজ সংরক্ষণ হয়নি");
                    ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 16));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving data to the database", "Error", JOptionPane.ERROR_MESSAGE);
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


    public static void main(String[] args) {
        //new NcHomepage();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
        NcHomepage frame = new NcHomepage();
        frame.setVisible(true);
    }
}
