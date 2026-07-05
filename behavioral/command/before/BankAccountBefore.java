package behavioral.command.before;

public class BankAccountBefore {
    private final String accountNumber;
    private double balance;

    public BankAccountBefore(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
        System.out.println("Account " + accountNumber + " deposited $" + amount + ". New balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
        System.out.println("Account " + accountNumber + " withdrew $" + amount + ". New balance: $" + balance);
    }
}
