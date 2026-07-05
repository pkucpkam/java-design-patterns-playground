package behavioral.command.before;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceBefore {
    private final BankAccountBefore account;
    private final List<String> history = new ArrayList<>();
    private final List<Double> amounts = new ArrayList<>();

    public TransactionServiceBefore(BankAccountBefore account) {
        this.account = account;
    }

    public void execute(String type, double amount) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        
        String upperType = type.toUpperCase();
        if (upperType.equals("DEPOSIT")) {
            account.deposit(amount);
            history.add("DEPOSIT");
            amounts.add(amount);
        } else if (upperType.equals("WITHDRAW")) {
            account.withdraw(amount);
            history.add("WITHDRAW");
            amounts.add(amount);
        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + type);
        }
    }

    public void undo() {
        if (history.isEmpty()) {
            throw new IllegalStateException("No transactions to undo");
        }
        
        int lastIndex = history.size() - 1;
        String type = history.remove(lastIndex);
        double amount = amounts.remove(lastIndex);

        if (type.equals("DEPOSIT")) {
            account.withdraw(amount);
        } else if (type.equals("WITHDRAW")) {
            account.deposit(amount);
        }
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}
