package revolut.transfer.account.transaction;

import revolut.transfer.domain.Money;

import java.util.Date;

public abstract class Transaction {

    private Date createdAt = new Date();

    public Transaction(Money money) {
        this.money = money;
    }

    private Money money;

    public Money money() {
        return money;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public abstract String summary();
}
