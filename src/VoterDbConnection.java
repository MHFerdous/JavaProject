import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class VoterDbConnection {
    private Connection connection; // private Statement statement;

    public VoterDbConnection() {
      try{
          Class.forName("com.mysql.cj.jdbc.Driver");
          connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/voter_info","root","");
          if(connection!=null){
              System.out.println("Successfully Connected to DB");
          }

      }catch (ClassNotFoundException | SQLException e) {
          JOptionPane.showMessageDialog(null, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
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
                ValidationErrorText.setText("ভুল! সঠিক আইডি ও পাসওয়ার্ড দিন");
                ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
            }

        }catch (SQLException exception){
            ValidationErrorText.setText("কোড এর এস-কিউ-এল ভুল আছে");
            ValidationErrorText.setFont(banglaFont.deriveFont(Font.BOLD, 17));
        }
    }

}
