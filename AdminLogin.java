import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLogin extends JFrame {

    JLabel l1, l2, l3;
    JTextField t1;
    JPasswordField t2;
    JButton b1, b2;

    public AdminLogin() {

        setTitle("Smart Campus - Admin Login");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        l1 = new JLabel("Admin Login");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setBounds(130, 30, 200, 30);
        add(l1);

        l2 = new JLabel("Username:");
        l2.setBounds(50, 90, 100, 25);
        add(l2);

        t1 = new JTextField();
        t1.setBounds(150, 90, 180, 25);
        add(t1);

        l3 = new JLabel("Password:");
        l3.setBounds(50, 130, 100, 25);
        add(l3);

        t2 = new JPasswordField();
        t2.setBounds(150, 130, 180, 25);
        add(t2);

        b1 = new JButton("Login");
        b1.setBounds(80, 190, 100, 35);
        add(b1);

        b2 = new JButton("Clear");
        b2.setBounds(210, 190, 100, 35);
        add(b2);

        b1.addActionListener(e -> loginAdmin());

        b2.addActionListener(e -> {
            t1.setText("");
            t2.setText("");
        });

        setVisible(true);
    }

    private void loginAdmin() {

        String username = t1.getText();
        String password = new String(t2.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password!"
            );

            return;
        }

        String sql =
                "SELECT * FROM admins WHERE username = ? AND password = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admin Login Successful!"
                );

                new AdminDashboard();

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Admin Username or Password!"
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Admin Login Failed!\n" + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
