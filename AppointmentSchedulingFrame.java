import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AppointmentSchedulingFrame extends JFrame {
    private JTextField appointmentIdField, patientIdField, doctorIdField, dateField;
    private JButton addButton, viewButton;

    public AppointmentSchedulingFrame() {
        setTitle("Appointment Scheduling");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Appointment ID:"));
        appointmentIdField = new JTextField();
        panel.add(appointmentIdField);

        panel.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        panel.add(patientIdField);

        panel.add(new JLabel("Doctor ID:"));
        doctorIdField = new JTextField();
        panel.add(doctorIdField);

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        panel.add(dateField);

        addButton = new JButton("Schedule Appointment");
        viewButton = new JButton("View Appointments");
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new AddAppointmentAction());
        // viewButton.addActionListener(e -> new ViewAppointmentsFrame());

        setVisible(true);
    }

    private class AddAppointmentAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String patientId = patientIdField.getText();
            String doctorId = doctorIdField.getText();
            String date = dateField.getText();

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO appointments (patient_id, doctor_id, date) VALUES (?, ?, ?)");) {
                stmt.setString(1, patientId);
                stmt.setString(2, doctorId);
                stmt.setString(3, date);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Appointment scheduled successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error scheduling appointment", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
