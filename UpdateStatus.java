import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateStatus extends JFrame {

    String incidentId;
    AdminDashboard dashboard;

    JLabel idLabel;
    JComboBox<String> statusBox;
    JButton updateButton;
    JButton cancelButton;

    public UpdateStatus(String incidentId, AdminDashboard dashboard) {

        this.incidentId = incidentId;
        this.dashboard = dashboard;

        setTitle("Update Incident Status");
        setSize(400, 280);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Update Incident Status");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(90, 25, 250, 30);
        add(title);

        JLabel idText = new JLabel("Incident ID:");
        idText.setBounds(50, 85, 100, 25);
        add(idText);

        idLabel = new JLabel(incidentId);
        idLabel.setBounds(160, 85, 180, 25);
        add(idLabel);

        JLabel statusLabel = new JLabel("New Status:");
        statusLabel.setBounds(50, 125, 100, 25);
        add(statusLabel);

        statusBox = new JComboBox<>(new String[]{
                "Pending",
                "Assigned",
                "In Progress",
                "Resolved"
        });

        statusBox.setBounds(160, 125, 180, 25);
        add(statusBox);

        updateButton = new JButton("Update");
        updateButton.setBounds(80, 180, 100, 35);
        add(updateButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(210, 180, 100, 35);
        add(cancelButton);

        updateButton.addActionListener(e -> updateIncidentStatus());

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void updateIncidentStatus() {

        String newStatus =
                statusBox.getSelectedItem().toString();

        String updateIncident =
                "UPDATE incidents SET status = ? WHERE incident_id = ?";

        String insertHistory =
                "INSERT INTO status_history " +
                "(incident_id, status) VALUES (?, ?)";

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            // Update current status
            PreparedStatement ps1 =
                    con.prepareStatement(updateIncident);

            ps1.setString(1, newStatus);
            ps1.setString(2, incidentId);

            ps1.executeUpdate();

            // Store status history
            PreparedStatement ps2 =
                    con.prepareStatement(insertHistory);

            ps2.setString(1, incidentId);
            ps2.setString(2, newStatus);

            ps2.executeUpdate();

            con.commit();

            ps1.close();
            ps2.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Status updated successfully!"
            );

            dashboard.refreshTable();

            dispose();

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception rollbackError) {
                rollbackError.printStackTrace();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Status Update Failed!\n" +
                    e.getMessage()
            );

        } finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception closeError) {
                closeError.printStackTrace();
            }
        }
    }
}
