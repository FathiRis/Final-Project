import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PharmacyInventoryFrame extends JFrame {
    private JTextField medicineNameField, quantityField, priceField, expiryDateField;
    private JButton addButton, viewButton;

    public PharmacyInventoryFrame() {
        setTitle("Pharmacy & Inventory Management");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Medicine Name:"));
        medicineNameField = new JTextField();
        panel.add(medicineNameField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Price per Unit:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        expiryDateField = new JTextField();
        panel.add(expiryDateField);

        addButton = new JButton("Add Medicine");
        viewButton = new JButton("View Inventory");
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new AddMedicineAction());
        viewButton.addActionListener(e -> new ViewInventoryFrame());

        setVisible(true);
    }

    private class AddMedicineAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = medicineNameField.getText();
            String quantity = quantityField.getText();
            String price = priceField.getText();
            String expiryDate = expiryDateField.getText();

            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO pharmacy (name, quantity, price, expiry_date) VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, name);
                stmt.setInt(2, Integer.parseInt(quantity));
                stmt.setDouble(3, Double.parseDouble(price));
                stmt.setString(4, expiryDate);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Medicine added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding medicine", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
