import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("Hospital Management System");
        setSize(1125, 845);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            private final Image bgImage = new ImageIcon("dashboard.png").getImage(); // Replace with your image file

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // Allow absolute positioning
        panel.setOpaque(false);

        JButton patientManagementButton = new JButton("Patient Management");
        patientManagementButton.setBounds(100, 100, 250, 50);
        panel.add(patientManagementButton);

        JButton doctorManagementButton = new JButton("Doctor Management");
        doctorManagementButton.setBounds(100, 180, 250, 50);
        panel.add(doctorManagementButton);

        JButton appointmentButton = new JButton("Appointment Scheduling");
        appointmentButton.setBounds(100, 260, 250, 50);
        panel.add(appointmentButton);

        JButton billingButton = new JButton("Billing System");
        billingButton.setBounds(100, 340, 250, 50);
        panel.add(billingButton);

        JButton pharmacyButton = new JButton("Pharmacy & Inventory");
        pharmacyButton.setBounds(100, 420, 250, 50);
        panel.add(pharmacyButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(100, 500, 250, 50);
        panel.add(logoutButton);

        setContentPane(panel);

        // You can uncomment these lines when other classes are ready
        
        patientManagementButton.addActionListener(e -> new PatientManagementFrame());
        doctorManagementButton.addActionListener(e -> new DoctorManagementFrame());
        appointmentButton.addActionListener(e -> new AppointmentSchedulingFrame());
        billingButton.addActionListener(e -> new BillingSystemFrame());
        pharmacyButton.addActionListener(e -> new PharmacyInventoryFrame());
        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardFrame::new);
    }
}
