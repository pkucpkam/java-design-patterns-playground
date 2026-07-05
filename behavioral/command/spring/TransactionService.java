package behavioral.command.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final Map<String, BankCommand> commands;

    @Autowired
    public TransactionService(List<BankCommand> commandList) {
        this.commands = commandList.stream()
                .collect(Collectors.toMap(
                        c -> c.getCommandName().toUpperCase(),
                        Function.identity()
                ));
    }

    public void executeTransaction(String commandName, String accountId, double amount) {
        if (commandName == null) {
            throw new IllegalArgumentException("Command name cannot be null");
        }
        BankCommand command = commands.get(commandName.toUpperCase());
        if (command == null) {
            throw new IllegalArgumentException("Unsupported command type: " + commandName);
        }
        command.execute(accountId, amount);
    }
}
