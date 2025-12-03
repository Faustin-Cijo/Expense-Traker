

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations using JDBC for SQLite.
 * This class replaces the FileManager.
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:expensetracker.db";

    public DatabaseManager() {
        // Create the table as soon as the manager is initialized if it doesn't exist
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "type TEXT NOT NULL," +
                         "date TEXT NOT NULL," +
                         "description TEXT NOT NULL," +
                         "amount REAL NOT NULL," +
                         "categorySource TEXT NOT NULL)";
            stmt.execute(sql);

        } catch (SQLException e) {
            System.err.println("Error creating database table: " + e.getMessage());
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                String categorySource = rs.getString("categorySource");

                if ("EXPENSE".equalsIgnoreCase(type)) {
                    transactions.add(new Expense(date, description, amount, categorySource));
                } else if ("INCOME".equalsIgnoreCase(type)) {
                    transactions.add(new Income(date, description, amount, categorySource));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading transactions from database: " + e.getMessage());
        }
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions(type, date, description, amount, categorySource) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (transaction instanceof Expense) {
                pstmt.setString(1, "EXPENSE");
                pstmt.setString(5, ((Expense) transaction).getCategory());
            } else if (transaction instanceof Income) {
                pstmt.setString(1, "INCOME");
                pstmt.setString(5, ((Income) transaction).getSource());
            }

            pstmt.setString(2, transaction.getDate().toString());
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDouble(4, transaction.getAmount());
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding transaction to database: " + e.getMessage());
        }
    }
}

