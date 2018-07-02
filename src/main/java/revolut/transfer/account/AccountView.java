package revolut.transfer.account;

import java.math.BigDecimal;
import java.util.List;

public class AccountView {

    String id;
    BigDecimal balance;
    List<String> transactionSummaries;
    String customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<String> getTransactionSummaries() {
        return transactionSummaries;
    }

    public void setTransactionSummaries(List<String> transactionSummaries) {
        this.transactionSummaries = transactionSummaries;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
