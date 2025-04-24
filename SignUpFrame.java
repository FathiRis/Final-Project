import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class SignUpFrame extends JFrame {
    public SignUpFrame() {
        setTitle("Sign Up");
        setSize(429, 555);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            private final Image bgImage = new ImageIcon("sign up.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(37, 136, 343, 28);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(usernameField);

        JTextField emailField = new JTextField();
        emailField.setBounds(37, 209, 343, 30);
        emailField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(37, 282, 343, 30);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(passwordField);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(37, 356, 343, 30);
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(confirmPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(135, 430, 150, 40);
        registerButton.setBackground(new Color(118, 0, 188));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            try (Connection conn = DatabaseConfig.getConnection()) {
                String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, password); // consider hashing in future

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create account.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        });

        add(panel);
        setVisible(true);
    }

    /*private void styleTransparentField(JTextField field) {
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setForeground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
    }*/
}
