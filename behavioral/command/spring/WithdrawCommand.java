package behavioral.command.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("withdrawSpringCommand")
public class WithdrawCommand implements BankCommand {
    private final BankAccountService accountService;

    @Autowired
    public WithdrawCommand(BankAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute(String accountId, double amount) {
        accountService.withdraw(accountId, amount);
    }

    @Override
    public String getCommandName() {
        return "WITHDRAW";
    }
}
