import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DoctorManagementFrame extends JFrame {
    private JTextField doctorIdField, nameField, specialtyField;
    private JButton addButton, viewButton;

    public DoctorManagementFrame() {
        setTitle("Doctor Management");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Doctor ID:"));
        doctorIdField = new JTextField();
        panel.add(doctorIdField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Specialty:"));
        specialtyField = new JTextField();
        panel.add(specialtyField);

        addButton = new JButton("Add Doctor");
        viewButton = new JButton("View Doctors");
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new AddDoctorAction());
        viewButton.addActionListener(e -> new ViewDoctorsFrame());

        setVisible(true);
    }

    private class AddDoctorAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String specialty = specialtyField.getText();

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO doctors (name, specialty) VALUES (?, ?)");) {
                stmt.setString(1, name);
                stmt.setString(2, specialty);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Doctor added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding doctor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
