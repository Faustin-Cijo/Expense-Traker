

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an income, a specific type of Transaction.
 * This also demonstrates INHERITANCE.
 */
public class Income extends Transaction {
    private String source;

    public Income(LocalDate date, String description, double amount, String source) {
        super(date, description, amount); // Call the parent constructor
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String getDetails() {
        return String.format("Income    | Date: %-12s | Source: %-15s | Amount: $%-8.2f | Description: %s",
                getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                source,
                getAmount(),
                getDescription());
    }

    @Override
    public String toCSVString() {
        // Format: TYPE,DATE,DESCRIPTION,AMOUNT,CATEGORY/SOURCE
        return String.format("INCOME,%s,%s,%.2f,%s",
                getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                getDescription(),
                getAmount(),
                getSource());
    }
}

