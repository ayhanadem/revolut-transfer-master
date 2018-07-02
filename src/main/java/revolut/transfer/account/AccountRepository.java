package revolut.transfer.account;

import revolut.transfer.domain.Repository;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.UUID;

@Singleton
public class AccountRepository extends Repository<Account, UUID> {

    @Override
    protected Comparator<Account> listComparator() {
        return Comparator.comparing(account -> account.transactions().recentTimestamp());
    }
}
