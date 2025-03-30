import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("Hospital Management System - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton patientManagementButton = new JButton("Patient Management");
        JButton doctorManagementButton = new JButton("Doctor Management");
        JButton appointmentButton = new JButton("Appointment Scheduling");
        JButton billingButton = new JButton("Billing System");
        JButton pharmacyButton = new JButton("Pharmacy & Inventory");
        JButton logoutButton = new JButton("Logout");

        panel.add(patientManagementButton);
        panel.add(doctorManagementButton);
        panel.add(appointmentButton);
        panel.add(billingButton);
        panel.add(pharmacyButton);
        panel.add(logoutButton);

        add(panel);

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
}
