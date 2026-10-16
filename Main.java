import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        String[] options = {
                "Student",
                "Admin"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Select User Type",
                "Smart Campus Incident & Response System",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {

            new Login();

        } else if (choice == 1) {

            new AdminLogin();

        } else {

            System.exit(0);
        }
    }
}
