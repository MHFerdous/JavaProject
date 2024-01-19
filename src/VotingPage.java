import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class VotingPage extends JFrame {
    private final Font banglaFont = loadBanglaFont();
    private JTable table;
    private  Connection connection;
    private boolean VoteDone = false;
    private JButton VoteDeleteButton;
    private JLabel messageShow;


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
        messageLable();
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
        panel.setBounds(165, 42, 270, 32);
        panel.setBackground(new Color(0x9CB2FF));
        add(panel);

        JLabel label1 = new JLabel("আপনার পছন্দের প্রার্থীকে নির্বাচন করুন");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        label1.setForeground(Color.black);

        panel.add(label1);
    }

    private void TableField() {
        table = new JTable();
        table.setSelectionBackground(Color.green);
        table.setFont(banglaFont.deriveFont(Font.BOLD,16));
        table.setBackground(new Color(0x057B61));
        table.setForeground(Color.white);
        table.setRowHeight(20);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(112,85,400,215);
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
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
            JOptionPane.showMessageDialog(this, message, "্রাইভার ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>ডাটাবেসে সংযোগ স্থাপন করা যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ডাটাবেসে ত্রুটি", JOptionPane.ERROR_MESSAGE);
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

    private void TableSelectAndVote() {
        if (VoteDone) {
            messageShow.setText("আপনি একবার ভোট দিয়েছেন");
            messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 25));
            messageShow.setBounds(183, 4, 300, 32);
            messageShow.setForeground(Color.red);
            return; // Exit the method if the user give vote already
        }

        int selectedRow = table.getSelectedRow();
        TableModel model = table.getModel();
        String candidateName = model.getValueAt(selectedRow, 0).toString();

        String voteQuery = "UPDATE nchome SET VoteCount = IFNULL(VoteCount, 0) + 1 WHERE Prarthi_Name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(voteQuery);
            statement.setString(1, candidateName);
            int updatedRows = statement.executeUpdate();

            if (updatedRows > 0) {
                messageShow.setText("আপনার ভোট প্রদান সম্পূর্ণ হয়েছে");
                messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 25));
                messageShow.setForeground(new Color(0x045703));
                messageShow.setBounds(168, 4, 300, 32);
                VoteDone = true;
            } else {
                messageShow.setText("দুঃখিত! প্রার্থী বাছাই করে পুনরায় ভোট বাটনে চাপ দিন");
                messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 25));
            }

        } catch (SQLException exception) {
            System.out.println("Error updating vote count: " + exception.getMessage());
        }
    }

    private void TableSelectAndVoteDelete( ) {

        int selectedRow = table.getSelectedRow();
        TableModel model = table.getModel();
        String candidateName = model.getValueAt(selectedRow, 0).toString();

        String voteQuery = "UPDATE nchome SET VoteCount = IFNULL(VoteCount, 0) - 1 WHERE Prarthi_Name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(voteQuery);
            statement.setString(1, candidateName);
            int updatedRows = statement.executeUpdate();

            if (updatedRows > 0) {
                messageShow.setText("আপনার ভোট বাতিল হয়েছে, আবার ভোট দিন");
                messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 25));
                messageShow.setForeground(Color.red);
                messageShow.setBounds(120, 4, 370, 32);
                VoteDone = false;
            } else {
                messageShow.setText("প্রার্থী কখুঁজে পাওয়া যায়নি");
                messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 25));
                messageShow.setForeground(Color.red);
            }

        } catch (SQLException exception) {
            messageShow.setText("আপনার ভোট বাতিল হয়েছে, আবার ভোট দিন");
            messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        }
    }


    private void voteButton() {
        JButton voteButton = new JButton("ভোট দিন");
        voteButton.setBounds(142, 308, 149, 42);
        voteButton.setForeground(Color.WHITE);
        voteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        voteButton.setBackground(new Color(0x045703));

        voteButton.addActionListener(e -> {
            if(table.getSelectedRow() != -1) {
                TableSelectAndVote();
                VoteDeleteButton.setVisible(true);
            }
            else {
                messageShow.setText("আগে প্রার্থী বাছাই করুন");
                messageShow.setFont(banglaFont.deriveFont(Font.BOLD, 25));
                messageShow.setForeground(Color.red);
                messageShow.setBounds(210, 4, 370, 32);
            }
        });

        add(voteButton);
    }

    private void VoteDeleteButton() {
        VoteDeleteButton = new JButton("বাতিল করুন");
        VoteDeleteButton.setBounds(320, 308, 149, 42);
        VoteDeleteButton.setForeground(Color.WHITE);
        VoteDeleteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        VoteDeleteButton.setBackground(new Color(0xFF1B1B));
        VoteDeleteButton.setVisible(false);

        VoteDeleteButton.addActionListener(e-> {
            TableSelectAndVoteDelete();
            VoteDeleteButton.setVisible(false);
        });

        add(VoteDeleteButton);
    }

    private void messageLable(){
        messageShow = new JLabel();
        add(messageShow);
    }

    public static void main(String[] args) {
       new VotingPage(); //add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        VotingPage frame = new VotingPage();
//        frame.setVisible(true);
    }
}