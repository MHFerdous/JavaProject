import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class Result extends JFrame {
    private final Font banglaFont = loadBanglaFont(); // Load Bangla banglaFont
    private Connection connection;

    public Result() throws SQLException {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setVisible(true);
        setTitle("নির্বাচনী ফলাফল");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xFFFFFF));
        BijoyiText();
        WinnerText();
        HomeButton();
        Logo();
        OtherResultHeadline();
        displayOtherPrarthiResult();

    }
    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }
    private void WinnerText() {
        JLabel winnerText = new JLabel();
        winnerText.setFont(banglaFont.deriveFont(Font.BOLD, 30));
        winnerText.setForeground(Color.black);
        winnerText.setBounds(40, 10, 500, 40);
        add(winnerText);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/result", "root", "");
        } catch (ClassNotFoundException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>মাইএসকুয়েল জেডবিসি ড্রাইভার পাওয়া যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>ডাটাবেসে সংযোগ স্থাপন করা যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Winner FROM allresult");



            if (resultSet.next()) {
                String winnerName = resultSet.getString("Winner");
                winnerText.setText(winnerName);
            } else if (resultSet.next()) {
                winnerText.setText("কোনো বিজয়ী পাওয়া যায়নি");
            }
        } catch (SQLException e) {
            winnerText.setText("বিজয়ী তথ্য পড়ে না");
        }

    }
    private void BijoyiText() {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(270, 50, 86, 40);
        jPanel.setBackground(Color.red);
        add(jPanel);
        JLabel BijoyiText = new JLabel("বিজয়ী");
        BijoyiText.setForeground(Color.WHITE);
        BijoyiText.setFont(banglaFont.deriveFont(Font.BOLD, 35));
        jPanel.add(BijoyiText);
    }
    private void OtherResultHeadline(){

        JPanel jPanel = new JPanel();
        jPanel.setBounds(240, 105, 150, 30);
        jPanel.setBackground(new Color(0x9CB2FF));
        add(jPanel);
        JLabel label1 = new JLabel("অন্যান্য প্রার্থীর ফলাফল");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 18));
        label1.setForeground(Color.black);
        jPanel.add(label1);
    }
    private void displayOtherPrarthiResult() {
        JTextArea otherPrarthiResultTextArea = new JTextArea("ডাটাবেজ এ ফলাফল প্রকাশিত হয়নি");
        otherPrarthiResultTextArea.setBounds(170, 145, 270, 100);
        otherPrarthiResultTextArea.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        otherPrarthiResultTextArea.setForeground(Color.BLACK);
        otherPrarthiResultTextArea.setEditable(false);
        add(otherPrarthiResultTextArea);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/result", "root", "");
        }catch (ClassNotFoundException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>মাইএসকুয়েল জেডবিসি ড্রাইভার পাওয়া যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>ডাটাবেসে সংযোগ স্থাপন করা যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT OtherWin1 FROM allresult");

            StringBuilder resultText = new StringBuilder();

            while (resultSet.next()) {
                String otherPrarthiName = resultSet.getString("OtherWin1");
                resultText.append(otherPrarthiName).append("\n");
            }
            if (!resultText.isEmpty()) {
                otherPrarthiResultTextArea.setText(resultText.toString());
            }
            else {
                otherPrarthiResultTextArea.setText("ফলাফল প্রকাশিত হয়নি");
            }

        } catch (SQLException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>" +
                    "কোডে ভুল আছে।" + "</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    private void HomeButton() {
        JButton HomeButton = new JButton(new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/home-icon.jpg"));
        HomeButton.setBounds(570, 5, 20, 20);
        HomeButton.addActionListener(e -> {
            new NcHomepage();
            dispose();
        });
        add(HomeButton);
    }

    private void Logo() {
        JLabel Logo = new JLabel(new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png"));
        Logo.setBounds(43, 50, 100, 100);
        add(Logo);
    }
    public static void main(String[] args) throws SQLException {
        new Result(); //add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        Result frame = new Result();
//        frame.setVisible(true);
    }
}
