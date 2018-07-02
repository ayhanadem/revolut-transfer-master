package revolut.transfer.customer;

import revolut.transfer.domain.AggregateRoot;
import revolut.transfer.domain.UUIDSupplier;

import java.util.Date;
import java.util.UUID;

public class Customer extends AggregateRoot<UUID> {

    private String name;
    private Date createdAt = new Date();


    public Customer() {
        super(UUIDSupplier.INSTANCE);
    }

    public Customer(String name) {
        this();
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Date createdAt() {
        return createdAt;
    }
}
