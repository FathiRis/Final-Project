import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientManagementFrame extends JFrame {
    private JTextField patientIdField, nameField, ageField, addressField;
    private JButton addButton, viewButton;

    public PatientManagementFrame() {
        setTitle("Patient Management");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        panel.add(patientIdField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        addButton = new JButton("Add Patient");
        viewButton = new JButton("View Patients");
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new AddPatientAction());
        viewButton.addActionListener(e -> new ViewPatientsFrame());

        setVisible(true);
    }

    private class AddPatientAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String address = addressField.getText();

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO patients (name, age, address) VALUES (?, ?, ?)");) {
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, address);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Patient added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding patient", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
