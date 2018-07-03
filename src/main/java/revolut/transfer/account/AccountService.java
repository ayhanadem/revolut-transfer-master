package revolut.transfer.account;

import io.micronaut.context.annotation.Context;
import revolut.transfer.account.transaction.Deposit;
import revolut.transfer.account.transaction.Withdraw;
import revolut.transfer.domain.DomainEvents;
import revolut.transfer.domain.events.CustomerCreatedEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Observable;
import java.util.Observer;

@Singleton
@Context
public class AccountService implements Observer {
    @Inject
    final DomainEvents domainEvents;
    private final AccountRepository accountRepository;

    public AccountService(DomainEvents domainEvents, AccountRepository accountRepository) {
        this.domainEvents = domainEvents;
        this.accountRepository = accountRepository;
        domainEvents.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.err.println(o);
        System.err.println(arg);
        if (arg instanceof CustomerCreatedEvent) {
            CustomerCreatedEvent event = (CustomerCreatedEvent) arg;
            accountRepository.save(Account.create(event.getId()));
        }
    }
}
