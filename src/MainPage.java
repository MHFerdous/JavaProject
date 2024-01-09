import javax.swing.*;

public class MainPage extends JFrame {

    private JPanel MainPage;
    private JRadioButton নির্বাচনRadioButton;

    public MainPage() {
        setLayout(null); // Set layout to null for absolute positioning

        // Set frame size and background color
        setSize(612, 400);
        //getContentPane().setBackground(new Color(0x007355));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPage frame = new MainPage();
                frame.setTitle("Commission Frame");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
