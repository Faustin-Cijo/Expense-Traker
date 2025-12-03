import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;

/**
 * A JDialog for adding a new transaction.
 */
public class TransactionDialog extends JDialog {
    private JTextField dateField, descField, amountField, categorySourceField;
    private JButton okButton, cancelButton;
    private Transaction newTransaction = null;

    public TransactionDialog(Frame owner, String type) {
        super(owner, "Add New " + type, true); // true for modal
        
        setLayout(new GridLayout(5, 2, 10, 10));

        // Create labels and text fields
        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(LocalDate.now().toString());
        add(dateField);

        add(new JLabel("Description:"));
        descField = new JTextField();
        add(descField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        String categorySourceLabel = type.equals("Expense") ? "Category:" : "Source:";
        add(new JLabel(categorySourceLabel));
        categorySourceField = new JTextField();
        add(categorySourceField);

        // Create buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        add(okButton);
        add(cancelButton);

        // --- Action Listeners ---
        okButton.addActionListener(e -> {
            if (createTransaction(type)) {
                dispose(); // Close the dialog
            }
        });

        cancelButton.addActionListener(e -> {
            newTransaction = null;
            dispose();
        });

        pack(); // Adjust window size to fit components
        setLocationRelativeTo(owner); // Center relative to the main window
    }

    private boolean createTransaction(String type) {
        try {
            LocalDate date = LocalDate.parse(dateField.getText());
            String description = descField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String categorySource = categorySourceField.getText();

            if (description.isEmpty() || categorySource.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (type.equals("Expense")) {
                newTransaction = new Expense(date, description, amount, categorySource);
            } else {
                newTransaction = new Income(date, description, amount, categorySource);
            }
            return true;

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Public method to be called after the dialog is closed.
     * @return The newly created transaction, or null if cancelled.
     */
    public Transaction getNewTransaction() {
        return newTransaction;
    }
}

    

