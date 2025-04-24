import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel forgotPasswordLabel;
    private JFrame mainFrame; // ✅ add this

    // ✅ Modified constructor to accept mainFrame
    public LoginFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame; // ✅ store reference

        setTitle("Login");
        setSize(429, 529);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel with background image
        JPanel mainPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("Login.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null); // Absolute layout

        // Username Field
        usernameField = new JTextField();
        usernameField.setBounds(35, 182, 345, 30);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mainPanel.add(usernameField);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(35, 290, 345, 30);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mainPanel.add(passwordField);

        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setBounds(135, 385, 150, 40);
        loginButton.setBackground(new Color(118, 0, 188));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("canva sans", Font.BOLD, 25));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        mainPanel.add(loginButton);

        // Forgot Password Label
        forgotPasswordLabel = new JLabel("<HTML><U>Forgot password?</U></HTML>");
        forgotPasswordLabel.setForeground(new Color(118, 0, 188));
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setBounds(260, 330, 150, 25);
        mainPanel.add(forgotPasswordLabel);

        // Listeners
        loginButton.addActionListener(new LoginAction());
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleForgotPassword();
            }
        });

        add(mainPanel);
        setVisible(true);

        // ✅ Remove dimming when login frame is closed
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
                // JOptionPane.showMessageDialog(null, "Login Successful!");
                new DashboardFrame(); // Make sure this class exists
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
        JOptionPane.showMessageDialog(this, "Forgot password clicked");
    }

    // You can delete this main method since you're using Main.java
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(LoginFrame::new);
    // }
}
