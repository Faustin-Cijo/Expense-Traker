

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all transactions. This class acts as the central logic hub.
 */
public class TransactionManager {
    // POLYMORPHISM: The list can hold both Expense and Income objects.
    private List<Transaction> transactions;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Return a copy for encapsulation
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double calculateBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {
            if (t instanceof Income) {
                balance += t.getAmount();
            } else if (t instanceof Expense) {
                balance -= t.getAmount();
            }
        }
        return balance;
    }
}
    

