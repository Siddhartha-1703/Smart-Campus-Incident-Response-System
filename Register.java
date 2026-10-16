import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Register extends JFrame {

    JLabel l1, l2, l3, l4;
    JTextField t1, t2;
    JPasswordField t3;
    JButton b1, b2;

    public Register() {

        setTitle("Student Registration");
        setSize(400, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        l1 = new JLabel("Student Registration");
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        l1.setBounds(100, 30, 250, 30);
        add(l1);

        l2 = new JLabel("Name:");
        l2.setBounds(50, 90, 100, 25);
        add(l2);

        t1 = new JTextField();
        t1.setBounds(150, 90, 180, 25);
        add(t1);

        l3 = new JLabel("Username:");
        l3.setBounds(50, 130, 100, 25);
        add(l3);

        t2 = new JTextField();
        t2.setBounds(150, 130, 180, 25);
        add(t2);

        l4 = new JLabel("Password:");
        l4.setBounds(50, 170, 100, 25);
        add(l4);

        t3 = new JPasswordField();
        t3.setBounds(150, 170, 180, 25);
        add(t3);

        b1 = new JButton("Register");
        b1.setBounds(80, 230, 110, 35);
        add(b1);

        b2 = new JButton("Clear");
        b2.setBounds(210, 230, 110, 35);
        add(b2);

        // Register button
        b1.addActionListener(e -> registerStudent());

        // Clear button
        b2.addActionListener(e -> {
            t1.setText("");
            t2.setText("");
            t3.setText("");
        });

        setVisible(true);
    }

    public void registerStudent() {

        String name = t1.getText();
        String username = t2.getText();
        String password = new String(t3.getPassword());

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        String sql = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!"
            );

            t1.setText("");
            t2.setText("");
            t3.setText("");

            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!\n" + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
