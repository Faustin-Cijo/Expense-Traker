

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExpenseTrackerGUI extends JFrame {

   
    private TransactionManager manager;
    private DatabaseManager dbManager; 
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private JLabel balanceLabel;

   
    public ExpenseTrackerGUI() {
        manager = new TransactionManager();
        dbManager = new DatabaseManager();

       
        manager.setTransactions(dbManager.loadTransactions());

      
        setTitle("Expense Tracker (JDBC Database Version)");
        setSize(800, 600);
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        setupTable();
        setupControlPanel();
        
        
        updateTableAndBalance();
    }


    private void setupTable() {
        String[] columnNames = {"Type", "Date", "Description", "Amount", "Category/Source"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
    }

  
    private void setupControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton addIncomeButton = new JButton("Add Income");
        JButton addExpenseButton = new JButton("Add Expense");
        balanceLabel = new JLabel("Balance: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        controlPanel.add(addIncomeButton);
        controlPanel.add(addExpenseButton);
        controlPanel.add(balanceLabel);
        add(controlPanel, BorderLayout.SOUTH);

       
        addIncomeButton.addActionListener(e -> showTransactionDialog("Income"));
        addExpenseButton.addActionListener(e -> showTransactionDialog("Expense"));
    }


    private void showTransactionDialog(String type) {
        TransactionDialog dialog = new TransactionDialog(this, type);
        dialog.setVisible(true); 

       
        Transaction newTransaction = dialog.getNewTransaction();
        if (newTransaction != null) {
          
            manager.addTransaction(newTransaction);
           
            dbManager.addTransaction(newTransaction);
          
            updateTableAndBalance();
        }
    }

    
    private void updateTableAndBalance() {
       
        tableModel.setRowCount(0);

       
        for (Transaction t : manager.getTransactions()) {
            String type, categorySource;
            if (t instanceof Income) {
                type = "Income";
                categorySource = ((Income) t).getSource();
            } else {
                type = "Expense";
                categorySource = ((Expense) t).getCategory();
            }

            Object[] row = {
                type,
                t.getDate(),
                t.getDescription(),
                String.format("%.2f", t.getAmount()),
                categorySource
            };
            tableModel.addRow(row);
        }

      
        double balance = manager.calculateBalance();
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
        balanceLabel.setForeground(balance >= 0 ? Color.BLUE : Color.RED);
    }

  
     
    public static void main(String[] args) {
     
        SwingUtilities.invokeLater(() -> {
            new ExpenseTrackerGUI().setVisible(true);
        });
    }
}  
    

