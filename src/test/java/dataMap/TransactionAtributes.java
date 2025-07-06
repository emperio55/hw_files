package dataMap;
import java.math.BigDecimal;
import java.util.List;

public class TransactionAtributes {
    private String transactionId;
    private String date;
    private BigDecimal amount;
    private String currency;
    private participantsTransact sender;
    private participantsTransact recipient;
    private String description;
    private String status;
    private List<Operation> operations;

    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public participantsTransact getSender() {
        return sender;
    }

    public participantsTransact getRecipient() {
        return recipient;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
