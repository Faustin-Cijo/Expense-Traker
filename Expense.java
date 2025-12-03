

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an expense, a specific type of Transaction.
 * This demonstrates INHERITANCE.
 */
public class Expense extends Transaction {
    private String category;

    public Expense(LocalDate date, String description, double amount, String category) {
        super(date, description, amount); // Call the parent constructor
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String getDetails() {
        return String.format("Expense   | Date: %-12s | Category: %-15s | Amount: $%-8.2f | Description: %s",
                getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                category,
                getAmount(),
                getDescription());
    }

    @Override
    public String toCSVString() {
        // Format: TYPE,DATE,DESCRIPTION,AMOUNT,CATEGORY/SOURCE
        return String.format("EXPENSE,%s,%s,%.2f,%s",
                getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                getDescription(),
                getAmount(),
                getCategory());
    }
}
    

