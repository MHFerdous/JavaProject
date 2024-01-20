import javax.swing.*;
import javax.swing.table.*;
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
    private void WinnerText() {
        JLabel winnerText = new JLabel();
        winnerText.setFont(banglaFont.deriveFont(Font.BOLD, 24));
        winnerText.setForeground(Color.black);
        winnerText.setBounds(0, 38, 612, 40);
        winnerText.setHorizontalAlignment(SwingConstants.CENTER);
        add(winnerText);

        JLabel winnerDetails = new JLabel();
        winnerDetails.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        winnerDetails.setForeground(new Color(0x057B61));
        winnerDetails.setBounds(0, 63, 612, 40);
        winnerDetails.setHorizontalAlignment(SwingConstants.CENTER);
        add(winnerDetails);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
        }catch (ClassNotFoundException e) {
            JLabel label = new JLabel("মাইএসকুয়েল জেডবিসি ড্রাইভার পাওয়া যায়নি");
            label.setFont(banglaFont);
            JOptionPane.showMessageDialog(null, label, "ড্রাইভার ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (Exception e) {
            JLabel label = new JLabel("ডাটাবেসে সংযোগ স্থাপন করা যায়নি");
            label.setFont(banglaFont);
            JOptionPane.showMessageDialog(null, label, "ডাটাবেস ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Prarthi_Name,Protik,VoteCount FROM nchome ORDER BY VoteCount DESC LIMIT 1");

            if (resultSet.next()) {
                String name = resultSet.getString("Prarthi_Name");
                String protik = resultSet.getString("Protik");
                String vote = resultSet.getString("VoteCount");
                winnerText.setText(name);
                String DetailsText = " মার্কা: " + protik +" | প্রাপ্ত ভোট: "+vote;
                winnerDetails.setText(DetailsText);
            } else {
                winnerText.setText("কোনো বিজয়ী পাওয়া যায়নি");
            }
        } catch (SQLException e) {
            winnerText.setText("কোনো বিজয়ী পাওয়া যায়নি");
        }


    }
    private void BijoyiText() {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(270, 2, 86, 40);
        jPanel.setBackground(Color.red);
        add(jPanel);
        JLabel BijoyiText = new JLabel("বিজয়ী");
        BijoyiText.setForeground(Color.WHITE);
        BijoyiText.setFont(banglaFont.deriveFont(Font.BOLD, 35));
        jPanel.add(BijoyiText);
    }
    private void OtherResultHeadline(){

        JPanel jPanel = new JPanel();
        jPanel.setBounds(240, 98, 150, 30);
        jPanel.setBackground(new Color(0x9CB2FF));
        add(jPanel);
        JLabel label1 = new JLabel("অন্যান্য প্রার্থীর ফলাফল");
        label1.setFont(banglaFont.deriveFont(Font.BOLD, 18));
        label1.setForeground(Color.black);
        jPanel.add(label1);
    }
    private void displayOtherPrarthiResult() {

        JTable table = new JTable();
        table.setSelectionBackground(Color.green);
        table.setFont(banglaFont.deriveFont(Font.BOLD, 18));
        table.setBackground(new Color(0x057B61));
        table.setForeground(Color.white);
        table.setRowHeight(22);

        Object[] column = {"প্রার্থীর নাম", "প্রতীক", "প্রাপ্ত ভোট"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(column);
        table.setModel(model);

        TableColumn column1 = table.getColumnModel().getColumn(0);
        TableColumn column2 = table.getColumnModel().getColumn(1);
        TableColumn column3 = table.getColumnModel().getColumn(2);
        column1.setPreferredWidth(150);
        column2.setPreferredWidth(40);
        column3.setPreferredWidth(15);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(112, 138, 400, 215);
        add(scroll);

        JTableHeader header = table.getTableHeader();
        header.setFont(banglaFont.deriveFont(Font.BOLD, 20));

        JTextArea otherPrarthiResultTextArea = new JTextArea("ডাটাবেজ এ ফলাফল প্রকাশিত হয়নি");
        otherPrarthiResultTextArea.setBounds(170, 200, 270, 100);
        otherPrarthiResultTextArea.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        otherPrarthiResultTextArea.setForeground(Color.BLACK);
        otherPrarthiResultTextArea.setEditable(false);
        otherPrarthiResultTextArea.setVisible(false);
        add(otherPrarthiResultTextArea);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOthers = statement.executeQuery("SELECT Prarthi_Name, Protik, VoteCount FROM nchome ORDER BY VoteCount DESC");

            int rowCount = 0;

            while (resultSetOthers.next()) {
                rowCount++;

                if (rowCount > 1) {
                    String name = resultSetOthers.getString("Prarthi_Name");
                    String protik = resultSetOthers.getString("Protik");
                    String vote = resultSetOthers.getString("VoteCount");
                    String[] otherPrarthi = {name, protik, vote};
                    model.addRow(otherPrarthi);
                }
            }

            if (rowCount > 1) {
                otherPrarthiResultTextArea.setVisible(false);
                table.setVisible(true);
            } else {
                otherPrarthiResultTextArea.setVisible(true);
                table.setVisible(false);
            }
        } catch (SQLException e) {
            otherPrarthiResultTextArea.setText("কোডে ভুল আছে।");
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
        Logo.setBounds(0, 0, 100, 100);
        add(Logo);
    }
    public static void main(String[] args) throws SQLException {
        new Result(); //add comment this and above setVisible(true); line - if below line is active

        //To run this page remove comment
//        Result frame = new Result();
//        frame.setVisible(true);
    }
}
