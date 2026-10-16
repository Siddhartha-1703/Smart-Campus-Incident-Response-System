import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDashboard extends JFrame {

    JTable table;
    DefaultTableModel model;

    JButton viewButton;
    JButton updateButton;
    JButton refreshButton;
    JButton logoutButton;

    public AdminDashboard() {

        setTitle("Smart Campus - Admin Dashboard");
        setSize(850, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Incidents Requiring Action");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(280, 20, 300, 30);
        add(title);

        model = new DefaultTableModel();

        model.addColumn("Incident ID");
        model.addColumn("Category");
        model.addColumn("Location");
        model.addColumn("Status");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 70, 750, 250);
        add(scrollPane);

        viewButton = new JButton("View Details");
        viewButton.setBounds(100, 350, 140, 35);
        add(viewButton);

        updateButton = new JButton("Update Status");
        updateButton.setBounds(270, 350, 140, 35);
        add(updateButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(440, 350, 100, 35);
        add(refreshButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(570, 350, 100, 35);
        add(logoutButton);

        loadIncidents();

        viewButton.addActionListener(e -> viewDetails());

        updateButton.addActionListener(e -> updateStatus());

        refreshButton.addActionListener(e -> loadIncidents());

        logoutButton.addActionListener(e -> {
            new AdminLogin();
            dispose();
        });

        setVisible(true);
    }

    private void loadIncidents() {

        model.setRowCount(0);

        String sql =
                "SELECT incident_id, category, location, status " +
                "FROM incidents " +
                "WHERE status <> 'Resolved' " +
                "ORDER BY reported_date DESC";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getString("incident_id"),
                        rs.getString("category"),
                        rs.getString("location"),
                        rs.getString("status")
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load incidents!\n" +
                    e.getMessage()
            );
        }
    }

    private void viewDetails() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an incident!"
            );

            return;
        }

        String incidentId =
                model.getValueAt(row, 0).toString();

        String sql =
                "SELECT * FROM incidents WHERE incident_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, incidentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String details =
                        "Incident ID: " + rs.getString("incident_id") +
                        "\nCategory: " + rs.getString("category") +
                        "\nLocation: " + rs.getString("location") +
                        "\nDescription: " + rs.getString("description") +
                        "\nReported Date: " + rs.getString("reported_date") +
                        "\nStatus: " + rs.getString("status");

                JOptionPane.showMessageDialog(
                        this,
                        details,
                        "Incident Details",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to view details!\n" +
                    e.getMessage()
            );
        }
    }

    private void updateStatus() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an incident!"
            );

            return;
        }

        String incidentId =
                model.getValueAt(row, 0).toString();

        new UpdateStatus(incidentId, this);
    }

    public void refreshTable() {
        loadIncidents();
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}