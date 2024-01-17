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

    public void VoterSignupDB(String name, String nid, String pass, String email, String mobile, Font banglaFont, JLabel ValidationErrorText ) {
        try {
            String insertQuery = "INSERT INTO voter_signup VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, nid);
            preparedStatement.setString(3, pass);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, mobile);

            int noOfRows = preparedStatement.executeUpdate();
            if(noOfRows>0){
                ValidationErrorText.setText("ডাটাবেজ সংরক্ষণ হয়েছে");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));

            } else {
                ValidationErrorText.setText("ভুল! ডাটাবেজ সংরক্ষণ হয়নি");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
            }

        }catch (SQLException exception){
            ValidationErrorText.setText("কোড এর এস-কিউ-এল ভুল আছে");
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
        }
    }

}
