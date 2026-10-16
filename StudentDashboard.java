import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    int userId;
    String username;

    JButton b1, b2, b3, b4;

    public StudentDashboard(int userId, String username) {

        this.userId = userId;
        this.username = username;

        setTitle("Smart Campus - Student Dashboard");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Student Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(150, 30, 250, 30);
        add(title);

        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setBounds(180, 70, 200, 25);
        add(welcome);

        b1 = new JButton("Report New Incident");
        b1.setBounds(130, 120, 240, 40);
        add(b1);

        b2 = new JButton("Track Incident");
        b2.setBounds(130, 175, 240, 40);
        add(b2);

        b3 = new JButton("My Incidents");
        b3.setBounds(130, 230, 240, 40);
        add(b3);

        b4 = new JButton("Logout");
        b4.setBounds(180, 290, 140, 35);
        add(b4);

        b1.addActionListener(e -> {
            // Report Incident will be connected next
           new ReportIncident(userId);
        });

        b2.addActionListener(e -> {
            new TrackIncident();
        });

        b3.addActionListener(e -> {
            new MyIncidents(userId);
        });

        b4.addActionListener(e -> {
            new Login();
            dispose();
        });

        setVisible(true);
    }
}
