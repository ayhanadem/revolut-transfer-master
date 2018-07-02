package revolut.transfer.account.transaction;

import revolut.transfer.domain.Money;

import java.util.Optional;
import java.util.UUID;

public class Deposit extends Transaction {


    private Optional<UUID> by =Optional.empty();

    public Deposit(Money money, Optional<UUID> by)
    {
        super(money);
        this.by = by;
    }

    public Deposit(Money money) {
        super(money);
    }

    @Override
    public String summary() {
        return String.format("deposit %s by %s", money().asString(), by.isPresent() ? by.get(): "itself" );
    }
}
