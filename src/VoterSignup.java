import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class VoterSignup extends JFrame {
    private JPasswordField passTxtF;
    public VoterSignup() {
        setSize(612, 400);
        setTitle("ভোটার নিবন্ধন");
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(new Color(0x007355));
        setLayout(null);
        setImageIcons();
        homeButton();
        Font font = loadBanglaFont();
        textFields(font);
        signupButton(font);
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

    private void homeButton() {
        ImageIcon homeIconButton = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/home-icon.jpg");
        JButton homeButton = new JButton(homeIconButton);
        homeButton.setBounds(314, 10, 22, 22);
        add(homeButton);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage frame = new MainPage();
                dispose();
                frame.setVisible(true);
            }
        });
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 14);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }

    private void textFields(Font font) {
        JTextField nameTxtF = new JTextField("সম্পূর্ণ নাম");
        nameTxtF.setBounds(337,51,227,37);
        nameTxtF.setBackground(new Color(0xD9D9D9));
        nameTxtF.setForeground(Color.gray);
        nameTxtF.setFont(font);
        add(nameTxtF);
        nameTxtF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nameTxtF.getText().equals("সম্পূর্ণ নাম")){
                    nameTxtF.setText(" ");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(nameTxtF.getText().equals(" ")){
                    nameTxtF.setText("সম্পূর্ণ নাম");
                    nameTxtF.setForeground(Color.gray);
                }
            }
        });
        JTextField nidTxtF = new JTextField("এন আই ডি নাম্বার");
        nidTxtF.setBounds(337,101,227,37);
        nidTxtF.setBackground(new Color(0xD9D9D9));
        nidTxtF.setForeground(Color.gray);
        add(nidTxtF);
        nidTxtF.setFont(font);
        nidTxtF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nidTxtF.getText().equals("এন আই ডি নাম্বার")){
                    nidTxtF.setText(" ");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(nidTxtF.getText().equals(" ")){
                    nidTxtF.setText("এন আই ডি নাম্বার");
                    nidTxtF.setForeground(Color.gray);
                }
            }
        });
        passTxtF = new JPasswordField();
        passTxtF.setBounds(337,151,227,37);
        passTxtF.setBackground(new Color(0xD9D9D9));
        passTxtF.setFont(font);
        PasswordHintText(passTxtF, font);
        add(passTxtF);
        passTxtF.addFocusListener(new FocusAdapter() { // show and hide password hintText
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passTxtF.getPassword()).equals("পাসওয়ার্ড")) {
                    passTxtF.setText("");
                    passTxtF.setEchoChar('*');
                    passTxtF.setForeground(Color.gray);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passTxtF.getPassword()).isEmpty()) {
                    passTxtF.setText("পাসওয়ার্ড");
                    passTxtF.setEchoChar((char) 0);
                    passTxtF.setFont(font.deriveFont(Font.PLAIN, 14));
                }
            }
        });
        JTextField emailTxtF = new JTextField("ই-মেইল");
        emailTxtF.setBounds(337,201,227,37);
        emailTxtF.setBackground(new Color(0xD9D9D9));
        emailTxtF.setForeground(Color.gray);
        emailTxtF.setFont(font);
        add(emailTxtF);
        emailTxtF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(emailTxtF.getText().equals("ই-মেইল")){
                    emailTxtF.setText(" ");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(emailTxtF.getText().equals(" ")){
                    emailTxtF.setText("ই-মেইল");
                    emailTxtF.setForeground(Color.gray);
                }
            }
        });
        JTextField mobileTxtF = new JTextField("মোবাইল নাম্বার");
        mobileTxtF.setBounds(337,251,227,37);
        mobileTxtF.setBackground(new Color(0xD9D9D9));
        mobileTxtF.setForeground(Color.gray);
        mobileTxtF.setFont(font);
        add(mobileTxtF);
        mobileTxtF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(
                        mobileTxtF.getText().equals("মোবাইল নাম্বার")){
                    mobileTxtF.setText(" ");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(mobileTxtF.getText().equals(" ")){
                    mobileTxtF.setText("মোবাইল নাম্বার");
                    mobileTxtF.setForeground(Color.gray);
                }
            }
        });

    }
    private void PasswordHintText(JPasswordField passTxtF, Font font) {
        passTxtF.setText("পাসওয়ার্ড");
        passTxtF.setForeground(Color.GRAY);
        passTxtF.setFont(font.deriveFont(Font.PLAIN, 14));
        passTxtF.setEchoChar((char) 0);
    }
    private void signupButton(Font font) {
        JButton signButton = new JButton("নিবন্ধন করুন");
        signButton.setBounds(385,300,132,44);
        signButton.setForeground(Color.BLACK);
        signButton.setBackground(new Color(0x5FFF95));
        signButton.setFont(font.deriveFont(Font.BOLD,20));
        add(signButton);
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Signup Button clicked");
            }
        });
    }
    public static void main(String[] args) {
        new VoterSignup();//add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VoterSignup frame = new VoterSignup();
//        frame.setVisible(true);
    }
}