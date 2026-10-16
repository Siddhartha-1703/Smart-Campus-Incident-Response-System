import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrackIncident extends JFrame {

    JTextField incidentField;
    JTextArea resultArea;
    JButton trackButton, clearButton;

    public TrackIncident() {

        setTitle("Track Incident");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Track Incident");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(170, 25, 200, 30);
        add(title);

        JLabel l1 = new JLabel("Incident ID:");
        l1.setBounds(50, 85, 100, 25);
        add(l1);

        incidentField = new JTextField();
        incidentField.setBounds(150, 85, 220, 25);
        add(incidentField);

        trackButton = new JButton("Track");
        trackButton.setBounds(150, 125, 100, 35);
        add(trackButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(270, 125, 100, 35);
        add(clearButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(50, 180, 400, 130);
        add(scrollPane);

        trackButton.addActionListener(e -> trackIncident());

        clearButton.addActionListener(e -> {
            incidentField.setText("");
            resultArea.setText("");
        });

        setVisible(true);
    }

    private void trackIncident() {

        String incidentId = incidentField.getText().trim();

        if (incidentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Incident ID!"
            );

            return;
        }

        String sql = "SELECT * FROM incidents WHERE incident_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, incidentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                resultArea.setText(
                        "Incident ID: " + rs.getString("incident_id") +
                        "\nCategory: " + rs.getString("category") +
                        "\nLocation: " + rs.getString("location") +
                        "\nDescription: " + rs.getString("description") +
                        "\nReported Date: " + rs.getString("reported_date") +
                        "\nCurrent Status: " + rs.getString("status")
                );

            } else {

                resultArea.setText("Incident not found!");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Tracking Failed!\n" + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new TrackIncident();
    }
}
