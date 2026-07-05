package behavioral.command.tests;

import behavioral.command.before.BankAccountBefore;
import behavioral.command.before.TransactionServiceBefore;
import behavioral.command.after.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandPatternTest {

    // --- Before Refactoring Tests ---

    @Test
    public void testBeforeRefactoring_HappyPath() {
        BankAccountBefore account = new BankAccountBefore("123", 100.0);
        TransactionServiceBefore service = new TransactionServiceBefore(account);

        service.execute("deposit", 50.0);
        assertEquals(150.0, account.getBalance());

        service.execute("withdraw", 30.0);
        assertEquals(120.0, account.getBalance());

        service.undo(); // Undo withdraw
        assertEquals(150.0, account.getBalance());

        service.undo(); // Undo deposit
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testBeforeRefactoring_UnsupportedType() {
        BankAccountBefore account = new BankAccountBefore("123", 100.0);
        TransactionServiceBefore service = new TransactionServiceBefore(account);

        assertThrows(IllegalArgumentException.class, () -> service.execute("transfer", 50.0));
        assertThrows(IllegalArgumentException.class, () -> service.execute(null, 50.0));
    }

    @Test
    public void testBeforeRefactoring_EmptyUndo() {
        BankAccountBefore account = new BankAccountBefore("123", 100.0);
        TransactionServiceBefore service = new TransactionServiceBefore(account);

        assertThrows(IllegalStateException.class, service::undo);
    }

    // --- Pattern Solution (After Refactoring) Tests ---

    @Test
    public void testPatternSolution_HappyPath() {
        BankAccount account = new BankAccount("456", 200.0);
        TransactionInvoker invoker = new TransactionInvoker();

        Command deposit = new DepositCommand(account, 100.0);
        invoker.executeCommand(deposit);
        assertEquals(300.0, account.getBalance());
        assertEquals(1, invoker.getUndoStackSize());
        assertEquals(0, invoker.getRedoStackSize());

        Command withdraw = new WithdrawCommand(account, 50.0);
        invoker.executeCommand(withdraw);
        assertEquals(250.0, account.getBalance());
        assertEquals(2, invoker.getUndoStackSize());

        // Undo
        invoker.undo(); // Undo withdraw
        assertEquals(300.0, account.getBalance());
        assertEquals(1, invoker.getUndoStackSize());
        assertEquals(1, invoker.getRedoStackSize());

        invoker.undo(); // Undo deposit
        assertEquals(200.0, account.getBalance());

        // Redo
        invoker.redo(); // Redo deposit
        assertEquals(300.0, account.getBalance());

        invoker.redo(); // Redo withdraw
        assertEquals(250.0, account.getBalance());
    }

    @Test
    public void testPatternSolution_EdgeCases() {
        BankAccount account = new BankAccount("456", 100.0);

        assertThrows(IllegalArgumentException.class, () -> new DepositCommand(null, 50.0));
        assertThrows(IllegalArgumentException.class, () -> new DepositCommand(account, -50.0));
        assertThrows(IllegalArgumentException.class, () -> new DepositCommand(account, 0.0));

        assertThrows(IllegalArgumentException.class, () -> new WithdrawCommand(null, 50.0));
        assertThrows(IllegalArgumentException.class, () -> new WithdrawCommand(account, -50.0));
    }

    @Test
    public void testPatternSolution_WithdrawInsufficientFunds() {
        BankAccount account = new BankAccount("456", 50.0);
        TransactionInvoker invoker = new TransactionInvoker();

        Command withdraw = new WithdrawCommand(account, 100.0);
        assertThrows(IllegalStateException.class, () -> invoker.executeCommand(withdraw));
        assertEquals(50.0, account.getBalance());
        assertEquals(0, invoker.getUndoStackSize()); // Should not add to history if execution fails
    }

    @Test
    public void testPatternSolution_EmptyUndoRedo() {
        TransactionInvoker invoker = new TransactionInvoker();

        assertThrows(IllegalStateException.class, invoker::undo);
        assertThrows(IllegalStateException.class, invoker::redo);
    }
}
