import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;
    private JLabel forgotPasswordLabel, noAccountLabel;
    private JFrame mainFrame;

    // ✅ Default constructor
    public LoginFrame() {
        this(null); // Calls the existing constructor with null
    }

    // ✅ Original constructor
    public LoginFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Login");
        setSize(429, 555);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("Login.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);

        usernameField = new JTextField();
        usernameField.setBounds(36, 150, 343, 28);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mainPanel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(36, 235, 343, 28);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mainPanel.add(passwordField);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(135, 330, 150, 40);
        loginButton.setBackground(new Color(118, 0, 188));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("canva sans", Font.BOLD, 25));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        mainPanel.add(loginButton);

        forgotPasswordLabel = new JLabel("<HTML><U>Forgot password?</U></HTML>");
        forgotPasswordLabel.setForeground(new Color(118, 0, 188));
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setBounds(270, 270, 150, 25);
        mainPanel.add(forgotPasswordLabel);

        noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setForeground(new Color(118, 0, 188));
        noAccountLabel.setBounds(143, 395, 200, 25);
        mainPanel.add(noAccountLabel);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 435, 120, 35);
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setForeground(new Color(118, 0, 188));
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainPanel.add(signUpButton);

        loginButton.addActionListener(new LoginAction());
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleForgotPassword();
            }
        });
        signUpButton.addActionListener(e -> new SignUpFrame());

        add(mainPanel);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (mainFrame != null) {
                    mainFrame.getGlassPane().setVisible(false);
                }
            }
        });
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticate(username, password)) {
                new DashboardFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean authenticate(String username, String password) {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    private void handleForgotPassword() {
        String email = JOptionPane.showInputDialog(this, "Enter your registered email:");
        if (email != null && !email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password reset link sent to: " + email);
        } else {
            JOptionPane.showMessageDialog(this, "Email is required!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
