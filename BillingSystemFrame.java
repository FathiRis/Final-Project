import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BillingSystemFrame extends JFrame {
    private JTextField billIdField, patientIdField, amountField, dateField;
    private JButton addButton, viewButton;

    public BillingSystemFrame() {
        setTitle("Billing System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Bill ID:"));
        billIdField = new JTextField();
        panel.add(billIdField);

        panel.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        panel.add(patientIdField);

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        panel.add(dateField);

        addButton = new JButton("Generate Bill");
        viewButton = new JButton("View Bills");
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new AddBillAction());
        viewButton.addActionListener(e -> new ViewBillsFrame());

        setVisible(true);
    }

    private class AddBillAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String patientId = patientIdField.getText();
            String amount = amountField.getText();
            String date = dateField.getText();

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO bills (patient_id, amount, date) VALUES (?, ?, ?)");) {
                stmt.setString(1, patientId);
                stmt.setString(2, amount);
                stmt.setString(3, date);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Bill generated successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error generating bill", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
