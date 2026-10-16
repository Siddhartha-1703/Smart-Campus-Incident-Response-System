import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyIncidents extends JFrame {

    int userId;

    JTable table;
    DefaultTableModel model;

    JButton refreshButton;
    JButton closeButton;

    public MyIncidents(int userId) {

        this.userId = userId;

        setTitle("My Incidents");
        setSize(800, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("My Reported Incidents");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(280, 20, 300, 30);
        add(title);

        model = new DefaultTableModel();

        model.addColumn("Incident ID");
        model.addColumn("Category");
        model.addColumn("Location");
        model.addColumn("Status");
        model.addColumn("Reported Date");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 70, 730, 240);
        add(scrollPane);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(250, 340, 120, 35);
        add(refreshButton);

        closeButton = new JButton("Close");
        closeButton.setBounds(400, 340, 120, 35);
        add(closeButton);

        loadMyIncidents();

        refreshButton.addActionListener(e -> loadMyIncidents());

        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void loadMyIncidents() {

        model.setRowCount(0);

        String sql =
                "SELECT incident_id, category, location, status, reported_date " +
                "FROM incidents " +
                "WHERE user_id = ? " +
                "ORDER BY reported_date DESC";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getString("incident_id"),
                        rs.getString("category"),
                        rs.getString("location"),
                        rs.getString("status"),
                        rs.getString("reported_date")
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

    public static void main(String[] args) {

        new MyIncidents(1);
    }
}
