import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Welcome");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1125, 845);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setLayout(null);

            // Background image
            ImageIcon bgImage = new ImageIcon("main.png");
            JLabel background = new JLabel(bgImage);
            background.setBounds(0, 0, 500, 400);
            background.setLayout(null);

            // Login button
            JButton loginButton = new JButton("Login");
            loginButton.setBounds(875, 80, 120, 40);
            loginButton.setBackground(Color.WHITE);
            loginButton.setForeground(new Color(118, 0, 188));
            loginButton.setFont(new Font("canva sans", Font.BOLD, 25));
            loginButton.setFocusPainted(false);
            // ✅ Add a purple border
            loginButton.setBorder(BorderFactory.createLineBorder(new Color(75, 0, 110), 3));
            loginButton.setBorderPainted(true); // ✅ Enable border painting
            background.add(loginButton);

            // Add background
            mainFrame.setContentPane(background);

            // Create a dimming glass pane
            JPanel dimPane = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            dimPane.setOpaque(false);
            dimPane.setLayout(null);

            mainFrame.setGlassPane(dimPane);

            // Button action: show dim + login frame
            loginButton.addActionListener(e -> {
                dimPane.setVisible(true); // Show the dim overlay
                LoginFrame login = new LoginFrame(mainFrame); // Pass the main frame
            });

            mainFrame.setVisible(true);
        });
    }
}
