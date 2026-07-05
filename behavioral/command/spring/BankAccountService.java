package behavioral.command.spring;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BankAccountService {
    private final Map<String, Double> accounts = new ConcurrentHashMap<>();

    public void deposit(String accountId, double amount) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        accounts.merge(accountId, amount, Double::sum);
    }

    public void withdraw(String accountId, double amount) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        double balance = getBalance(accountId);
        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        accounts.put(accountId, balance - amount);
    }

    public double getBalance(String accountId) {
        if (accountId == null) {
            return 0.0;
        }
        return accounts.getOrDefault(accountId, 0.0);
    }
    
    public void clear() {
        accounts.clear();
    }
}
