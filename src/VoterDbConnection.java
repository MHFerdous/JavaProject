import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class VoterDbConnection extends Component {
    private Connection connection; // private Statement statement;
    public VoterDbConnection(Font banglaFont) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/voter_info", "root", "");
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
    }

    public boolean VoterSignupDB(String name, String nid, String pass, String email, String mobile) {
        try {
            String insertQuery = "INSERT INTO voter_signup VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, nid);
            preparedStatement.setString(3, pass);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, mobile);

            int noOfRows = preparedStatement.executeUpdate();
            return noOfRows>0;    // Check if the ResultSet contains any data


        }catch (SQLException exception){
            return false;
        }
    }
    boolean voterlogin(JTextField NidField, JPasswordField passwordField) {
        String idValue = NidField.getText();
        char[] passwordValue = passwordField.getPassword();

        String loginCheckSQL = "SELECT * FROM voter_signup WHERE nid = ? AND pass = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(loginCheckSQL)) {
            preparedStatement.setString(1, idValue);
            preparedStatement.setString(2, String.valueOf(passwordValue));

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            return false;
        }
    }


}
