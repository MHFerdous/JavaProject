import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class VotingPage extends JFrame {
    private final Font banglaFont = loadBanglaFont(); // Load Bangla banglaFont


    public VotingPage(){
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setVisible(true);
        setTitle("ভোট প্রদান");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xFFFFFF));

        setImages();
        HeadingText();
        TableField();
        SignOutButton();
        voteButton();
        VoteDeleteButton();
    }

    private void setImages() {
        // Add image
        ImageIcon image = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(11, 3, 100, 100);
        add(imageLabel);

        // Add frameIcon
        ImageIcon frameIcon = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png");
        setIconImage(frameIcon.getImage());
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 14); // Fallback to a default font
        }
    }
    private void HeadingText(){

        JPanel panel = new JPanel();
        panel.setBounds(165, 26, 270, 32);
        panel.setBackground(new Color(0x9CB2FF));
        add(panel);

        JLabel label1 = new JLabel("আপনার পছন্দের প্রার্থীকে নির্বাচন করুন");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        label1.setForeground(Color.black);

        panel.add(label1);
    }

    private void TableField() {
        JTable table = new JTable();
        table.setSelectionBackground(Color.green);
        table.setFont(banglaFont.deriveFont(Font.BOLD,14));
        table.setBackground(new Color(0xC4E4DF));
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(112,70,380,215);
        add(scroll);

        JTableHeader header = table.getTableHeader();
        header.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        Object [] column = {"প্রার্থীর নাম","প্রতীক"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(column);
        table.setModel(model);

        // Fetch data
        String fetchQuery = "SELECT Prarthi_Name,Protik FROM nchome";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
            PreparedStatement statement = connection.prepareStatement(fetchQuery);

            ResultSet resultSet = statement.executeQuery();

            String name,protik;

            while(resultSet.next()) {
                name = resultSet.getString("Prarthi_Name");
                protik = resultSet.getString("Protik");
                String[] row = {name,protik};
                model.addRow(row);
            }

        } catch (ClassNotFoundException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>মাইএসকুয়েল জেডবিসি ড্রাইভার পাওয়া যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি no", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>ডাটাবেসে সংযোগ স্থাপন করা যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি ok", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

    }


    private void SignOutButton() {
        JButton SignOutButton = new JButton("প্রস্থান");
        SignOutButton.setBounds(520, 7, 71, 37);
        SignOutButton.setForeground(Color.WHITE);
        SignOutButton.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        SignOutButton.setBackground(new Color(0xFF0000));

        SignOutButton.addActionListener(e -> {
            MainPage loginFrame = new MainPage();
            loginFrame.setVisible(true);
            dispose();
        });

        add(SignOutButton);
    }

    private void voteButton() {
        JButton voteButton = new JButton("ভোট দিন");
        voteButton.setBounds(142, 300, 149, 42);
        voteButton.setForeground(Color.WHITE);
        voteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        voteButton.setBackground(new Color(0x045703));

        voteButton.addActionListener(e -> {

            //catch database that table row or colum and count++

        });


        add(voteButton);
    }
    private void VoteDeleteButton() {
        JButton VoteDeleteButton = new JButton("বাতিল");
        VoteDeleteButton.setBounds(320, 300, 149, 42);
        VoteDeleteButton.setForeground(Color.WHITE);
        VoteDeleteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        VoteDeleteButton.setBackground(new Color(0xFF1B1B));

        VoteDeleteButton.addActionListener(e -> {

            //catch database and delete the table

        });

        add(VoteDeleteButton);
    }

    public static void main(String[] args) {
       new VotingPage(); //add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VotingPage frame = new VotingPage();
//        frame.setVisible(true);
    }
}