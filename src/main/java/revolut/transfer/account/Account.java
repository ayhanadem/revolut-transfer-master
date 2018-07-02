package revolut.transfer.account;

import revolut.transfer.account.transaction.Deposit;
import revolut.transfer.account.transaction.Transaction;
import revolut.transfer.account.transaction.Withdraw;
import revolut.transfer.domain.AggregateRoot;
import revolut.transfer.domain.Money;
import revolut.transfer.domain.UUIDSupplier;

import java.util.*;

public class Account extends AggregateRoot<UUID> {

    private Money balance;
    private UUID customerId;
    private List<Transaction> transactions = new ArrayList<>();


    public static Account create(Money existing, UUID customerId) {
        return new Account(existing, customerId);
    }

    public static Account create(UUID customerId) {
        return new Account(Money.of(0), customerId);
    }

    public Money balance() {
        return balance;
    }

    public UUID customerId() {
        return customerId;
    }

    static class Transactions extends LinkedList<Transaction> {

        private final long recentTimestamp;

        public long recentTimestamp() {
            return recentTimestamp;
        }

        public Transactions(Collection<? extends Transaction> c) {
            super(Collections.unmodifiableCollection(c));
            if (c.isEmpty()) {
                this.recentTimestamp = Long.MIN_VALUE;
            } else {
                this.recentTimestamp = this.getLast().getCreatedAt().getTime();
            }

        }


    }

    public Transactions transactions() {
        return new Transactions(transactions);
    }

    private Account(Money initial, UUID customerId) {
        super(UUIDSupplier.INSTANCE);
        this.balance = initial;
        this.customerId = customerId;
    }


    public void deposit(Deposit deposit) {
        this.balance = this.balance.add(deposit.money());
        this.transactions.add(deposit);
    }

    public void withdraw(Withdraw withdraw) {
        this.balance = this.balance.substract(withdraw.money());
        this.transactions.add(withdraw);
    }

}
