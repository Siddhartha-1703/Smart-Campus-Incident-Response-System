import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportIncident extends JFrame {

    int userId;

    JComboBox<String> categoryBox;
    JTextField locationField;
    JTextArea descriptionArea;
    JButton submitButton, clearButton;

    public ReportIncident(int userId) {

        this.userId = userId;

        setTitle("Report New Incident");
        setSize(500, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Report New Incident");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(140, 25, 250, 30);
        add(title);

        JLabel l1 = new JLabel("Category:");
        l1.setBounds(50, 90, 100, 25);
        add(l1);

        categoryBox = new JComboBox<>(new String[]{
                "Electrical",
                "Water",
                "Infrastructure",
                "Cleanliness",
                "Security",
                "Other"
        });

        categoryBox.setBounds(170, 90, 220, 25);
        add(categoryBox);

        JLabel l2 = new JLabel("Location:");
        l2.setBounds(50, 135, 100, 25);
        add(l2);

        locationField = new JTextField();
        locationField.setBounds(170, 135, 220, 25);
        add(locationField);

        JLabel l3 = new JLabel("Description:");
        l3.setBounds(50, 180, 100, 25);
        add(l3);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(170, 180, 220, 100);
        add(scrollPane);

        submitButton = new JButton("Submit");
        submitButton.setBounds(120, 320, 110, 35);
        add(submitButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(260, 320, 110, 35);
        add(clearButton);

        submitButton.addActionListener(e -> submitIncident());

        clearButton.addActionListener(e -> {
            locationField.setText("");
            descriptionArea.setText("");
            categoryBox.setSelectedIndex(0);
        });

        setVisible(true);
    }

    private void submitIncident() {

        String category = categoryBox.getSelectedItem().toString();
        String location = locationField.getText();
        String description = descriptionArea.getText();

        if (location.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        String incidentId = generateIncidentId();

        String sql = "INSERT INTO incidents " +
                "(incident_id, user_id, category, location, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, incidentId);
            ps.setInt(2, userId);
            ps.setString(3, category);
            ps.setString(4, location);
            ps.setString(5, description);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Incident reported successfully!\n\n" +
                    "Your Incident ID: " + incidentId
            );

            ps.close();
            con.close();

            locationField.setText("");
            descriptionArea.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Incident Reporting Failed!\n" +
                    e.getMessage()
            );
        }
    }

    private String generateIncidentId() {

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    String timestamp =
            LocalDateTime.now().format(formatter);

    return "VMEG" + timestamp;
    }

    public static void main(String[] args) {

        new ReportIncident(1);
    }
}
