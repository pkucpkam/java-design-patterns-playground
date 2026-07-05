package behavioral.command.tests;

import behavioral.command.spring.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandSpringTest {

    private BankAccountService accountService;
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        accountService = new BankAccountService();
        accountService.clear();

        List<BankCommand> commandList = new ArrayList<>();
        commandList.add(new DepositCommand(accountService));
        commandList.add(new WithdrawCommand(accountService));

        transactionService = new TransactionService(commandList);
    }

    @Test
    public void testSpring_HappyPath_Deposit() {
        transactionService.executeTransaction("DEPOSIT", "ACC-01", 100.0);
        assertEquals(100.0, accountService.getBalance("ACC-01"));
    }

    @Test
    public void testSpring_HappyPath_Withdraw() {
        transactionService.executeTransaction("DEPOSIT", "ACC-01", 200.0);
        transactionService.executeTransaction("WITHDRAW", "ACC-01", 50.0);
        assertEquals(150.0, accountService.getBalance("ACC-01"));
    }

    @Test
    public void testSpring_FailureCase_UnsupportedCommand() {
        assertThrows(IllegalArgumentException.class, () -> 
            transactionService.executeTransaction("TRANSFER", "ACC-01", 100.0)
        );
    }

    @Test
    public void testSpring_FailureCase_InsufficientFunds() {
        transactionService.executeTransaction("DEPOSIT", "ACC-01", 50.0);
        assertThrows(IllegalStateException.class, () -> 
            transactionService.executeTransaction("WITHDRAW", "ACC-01", 100.0)
        );
        assertEquals(50.0, accountService.getBalance("ACC-01"));
    }

    @Test
    public void testSpring_EdgeCase_NullCommandName() {
        assertThrows(IllegalArgumentException.class, () -> 
            transactionService.executeTransaction(null, "ACC-01", 100.0)
        );
    }
}
