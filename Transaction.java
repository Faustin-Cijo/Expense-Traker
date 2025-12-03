

import java.time.LocalDate;

/**
 * Abstract class representing a single financial transaction.
 * This demonstrates ABSTRACTION and provides a base for INHERITANCE.
 */
public abstract class Transaction {
    // ENCAPSULATION: Fields are private.
    private LocalDate date;
    private String description;
    private double amount;

    public Transaction(LocalDate date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    // Getters for accessing the private fields.
    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Abstract method to be implemented by subclasses.
     * This will define how each transaction is represented in the CSV file.
     */
    public abstract String toCSVString();

    /**
     * Abstract method to provide details for display.
     * This demonstrates POLYMORPHISM, as the implementation will differ
     * between Expense and Income.
     */
    public abstract String getDetails();
}
    

