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
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>মাইএসকুয়েল জেডবিসি ড্রাইভার পাওয়া যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException e) {
            String message = "<html><p style='font-family: " + banglaFont.getFontName() + "; font-size: 17pt;'>ডাটাবেসে সংযোগ স্থাপন করা যায়নি</p></html>";
            JOptionPane.showMessageDialog(this, message, "ত্রুটি", JOptionPane.ERROR_MESSAGE);
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

        String idReadSQL = "SELECT nid FROM voter_signup WHERE nid = ?";
        String passReadSQL = "SELECT pass FROM voter_signup WHERE pass = ?";

        try (PreparedStatement preparedStatementId = connection.prepareStatement(idReadSQL);
             PreparedStatement preparedStatementPass = connection.prepareStatement(passReadSQL)) {

            preparedStatementId.setString(1, idValue);
            preparedStatementPass.setString(1, String.valueOf(passwordValue));

            ResultSet resultSetId = preparedStatementId.executeQuery();
            ResultSet resultSetPass = preparedStatementPass.executeQuery();

            return resultSetId.next() && resultSetPass.next();    // Check if the ResultSet contains any data
        } catch (SQLException exception) {
            return false;
        }
    }

}
