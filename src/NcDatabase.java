import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class NcDatabase extends JFrame {

    private final Font banglaFont = loadBanglaFont();
    private JTable table;
    private JTextField field;
    private Connection connection;

    public NcDatabase() {
        setLayout(null);
        setSize(612, 400);
        setResizable(false);
        setTitle("নির্বাচন কমিশন বিভাগ");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xC4E4DF));

        HeadingText();
        BackButton();
        TableField();
        DeleteButton();
        UpdateButton();
        addTextField();
        setImages();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked();
            }
        });
        getContentPane().requestFocusInWindow();
    }

    private void setImages() {
        ImageIcon MainImage = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png");
        JLabel Image = new JLabel(MainImage);
        Image.setBounds(80, 100, 100, 100);
        add(Image);

        ImageIcon frameIcon = new ImageIcon("/Users/hrkja/OneDrive/Desktop/evmProject/img/nirbacon_commison_logo.png");
        setIconImage(frameIcon.getImage());
    }

    private void formMouseClicked() {
        getContentPane().requestFocusInWindow();
    }

    private Font loadBanglaFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/Users/hrkja/OneDrive/Desktop/evmProject/BanglaFont/Nikosh.ttf")).deriveFont(Font.PLAIN, 17);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, 18);
        }
    }

    private void HeadingText() {
        JLabel HeadingText = new JLabel();
        HeadingText.setText("নির্বাচন কমিশন বিভাগ");
        HeadingText.setForeground(Color.black);
        HeadingText.setBounds(0, 15, 612, 40);
        HeadingText.setHorizontalAlignment(SwingConstants.CENTER);
        HeadingText.setFont(banglaFont.deriveFont(Font.BOLD, 32));
        add(HeadingText);
    }

    private void BackButton() {
        JButton backButton = new JButton("পেছনে");
        backButton.setBounds(520, 8, 70, 35);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(banglaFont.deriveFont(Font.BOLD, 15));
        backButton.setBackground(Color.red);

        backButton.addActionListener(e -> {
            new NcHomepage();
            dispose();
        });

        add(backButton);
    }

    private void addTextField() {
        field = new JTextField();
        HintText(field);
        field.setBounds(20, 242, 227, 37);
        field.setBackground(new Color(0xD9D9D9));
        field.setForeground(Color.GRAY);
        add(field);
    }
    private void HintText(JTextField textField) {
        textField.setText("প্রার্থীর প্রতীক");
        textField.setForeground(Color.GRAY);
        textField.setFont(banglaFont);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("প্রার্থীর প্রতীক")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("প্রার্থীর প্রতীক");
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void TableField() {
        table = new JTable();
        table.setSelectionBackground(Color.green);
        table.setFont(banglaFont.deriveFont(Font.BOLD, 16));
        table.setForeground(Color.white);
        table.setBackground(new Color(0x057B61));
        table.setRowHeight(22);
        Object[] columns = {"প্রার্থীর নাম", "প্রতীক"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        TableColumn column1 = table.getColumnModel().getColumn(0);
        TableColumn column2 = table.getColumnModel().getColumn(1);
        column1.setPreferredWidth(150);
        column2.setPreferredWidth(40);



        table.setModel(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(260, 85, 320, 215);
        add(scroll);


        JTableHeader header = table.getTableHeader();
        header.setFont(banglaFont.deriveFont(Font.BOLD, 20));

        String fetchQuery = "SELECT Prarthi_Name, Protik FROM nchome";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
            PreparedStatement statement = connection.prepareStatement(fetchQuery);

            ResultSet resultSet = statement.executeQuery();

            String name, protik;

            while (resultSet.next()) {
                name = resultSet.getString("Prarthi_Name");
                protik = resultSet.getString("Protik");
                model.addRow(new String[]{name, protik});
            }

            table.clearSelection();

        } catch (ClassNotFoundException e) {
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


        ListSelectionModel selectTableRow = table.getSelectionModel();
        selectTableRow.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String protik = table.getModel().getValueAt(selectedRow, 1).toString();
                    field.setText(protik);
                    field.setForeground(Color.black);
                }
            }
        });
    }

    private void DeleteButton() {
        JButton deleteButton = new JButton("বাতিল করুন");
        deleteButton.setBounds(362, 310, 130, 40);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        deleteButton.setBackground(new Color(0xFF2727));
        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                TableSelectAndDelete();
                table.clearSelection();
            } else {
                JLabel emptyMsg = new JLabel("আগে প্রার্থী বাছাই করুন");
                emptyMsg.setBounds(50, 10, 400, 30);
                emptyMsg.setFont(banglaFont);
                add(emptyMsg);
                JOptionPane.showMessageDialog(null, emptyMsg, "বার্তা", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(deleteButton);
    }

    private void TableSelectAndDelete() {
        int selectedRow = table.getSelectedRow();
        String cell = table.getModel().getValueAt(selectedRow, 0).toString();
        String deleteQuery = "DELETE FROM nchome WHERE Prarthi_Name = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, cell);

            statement.execute();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);

            JLabel emptyMsg = new JLabel("ডিলিট সফল হয়েছে");
            emptyMsg.setBounds(50, 10, 400, 30);
            emptyMsg.setFont(banglaFont);
            add(emptyMsg);
            JOptionPane.showMessageDialog(null, emptyMsg, "বার্তা", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JLabel emptyMsg = new JLabel("ডাটাবেস ত্রুটি");
            emptyMsg.setBounds(50, 10, 400, 30);
            emptyMsg.setFont(banglaFont);
            add(emptyMsg);
            JOptionPane.showMessageDialog(null, emptyMsg, "বার্তা", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void UpdateButton() {
        JButton UpdateButton = new JButton("পরিবর্তন");
        UpdateButton.setBounds(62, 310, 130, 40);
        UpdateButton.setForeground(Color.WHITE);
        UpdateButton.setFont(banglaFont.deriveFont(Font.BOLD, 20));
        UpdateButton.setBackground(new Color(0x037C5A));
        UpdateButton.addActionListener(e -> {
            String updatedText = field.getText();

            if (!updatedText.isEmpty()) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    String name = table.getModel().getValueAt(selectedRow, 0).toString();

                    String updateQuery = "UPDATE nchome SET Protik = ? WHERE Prarthi_Name = ?";
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nirbacon_commission", "root", "");
                        PreparedStatement statement = connection.prepareStatement(updateQuery);
                        statement.setString(1, updatedText);
                        statement.setString(2, name);

                        int rowsAffected = statement.executeUpdate();

                        if (rowsAffected > 0) {
                            JLabel successMsg = new JLabel("আপডেট সফল হয়েছে");
                            successMsg.setBounds(50, 10, 400, 30);
                            successMsg.setFont(banglaFont);
                            add(successMsg);
                            JOptionPane.showMessageDialog(null, successMsg, "বার্তা", JOptionPane.INFORMATION_MESSAGE);

                            // this line for Updatw the table after update
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.setValueAt(updatedText, selectedRow, 1);
                        } else {
                            JLabel errorMsg = new JLabel("আপডেট সফল হয়নি");
                            errorMsg.setBounds(50, 10, 400, 30);
                            errorMsg.setFont(banglaFont);
                            add(errorMsg);
                            JOptionPane.showMessageDialog(null, errorMsg, "বার্তা", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ex) {
                        JLabel errorMsg = new JLabel("ডাটাবেস ত্রুটি");
                        errorMsg.setBounds(50, 10, 400, 30);
                        errorMsg.setFont(banglaFont);
                        add(errorMsg);
                        JOptionPane.showMessageDialog(null, errorMsg, "বার্তা", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JLabel emptyMsg = new JLabel("আগে প্রার্থী বাছাই করুন");
                    emptyMsg.setBounds(50, 10, 400, 30);
                    emptyMsg.setFont(banglaFont);
                    add(emptyMsg);
                    JOptionPane.showMessageDialog(null, emptyMsg, "বার্তা", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(UpdateButton);
    }

    public static void main(String[] args) {
        new NcDatabase();
        //ncDatabase.setVisible(true);
    }
}
