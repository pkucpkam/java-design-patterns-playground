package behavioral.command.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("depositSpringCommand")
public class DepositCommand implements BankCommand {
    private final BankAccountService accountService;

    @Autowired
    public DepositCommand(BankAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute(String accountId, double amount) {
        accountService.deposit(accountId, amount);
    }

    @Override
    public String getCommandName() {
        return "DEPOSIT";
    }
}
