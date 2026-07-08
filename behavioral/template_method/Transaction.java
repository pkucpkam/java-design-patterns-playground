package behavioral.template_method;

import java.time.LocalDate;

/**
 * Model đại diện cho một giao dịch tài chính để tạo báo cáo.
 */
public class Transaction {
    private final String id;
    private final double amount;
    private final LocalDate date;
    private final String description;

    public Transaction(String id, double amount, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
