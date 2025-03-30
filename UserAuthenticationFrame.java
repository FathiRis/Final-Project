import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserAuthenticationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public UserAuthenticationFrame() {
        setTitle("User Authentication");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel()); // Empty space
        panel.add(loginButton);

        add(panel);
        
        loginButton.addActionListener(new LoginAction());

        setVisible(true);
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT role FROM users WHERE username=? AND password=?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    JOptionPane.showMessageDialog(null, "Login Successful! Role: " + role);
                    
                    dispose(); // Close login frame
                    
                    if (role.equalsIgnoreCase("Admin")) {
                        new DashboardFrame();
                    } else if (role.equalsIgnoreCase("Doctor")) {
                        new DoctorManagementFrame();
                    } else if (role.equalsIgnoreCase("Receptionist")) {
                        new AppointmentSchedulingFrame();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database connection error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new UserAuthenticationFrame();
    }
}
