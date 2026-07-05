package behavioral.command.spring;

public interface BankCommand {
    void execute(String accountId, double amount);
    String getCommandName();
}
