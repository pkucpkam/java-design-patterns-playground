package behavioral.command.after;

public class DepositCommand implements Command {
    private final BankAccount account;
    private final double amount;
    private boolean isExecuted = false;

    public DepositCommand(BankAccount account, double amount) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        account.deposit(amount);
        isExecuted = true;
    }

    @Override
    public void undo() {
        if (isExecuted) {
            account.withdraw(amount);
            isExecuted = false;
        }
    }
}
