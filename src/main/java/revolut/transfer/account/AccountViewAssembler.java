package revolut.transfer.account;

import revolut.transfer.account.transaction.Transaction;

import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class AccountViewAssembler {


    public AccountView convert(Account account) {
        AccountView data = new AccountView();
        data.setId(account.id().toString());
        data.setBalance(account.balance().asBigDecimal());
        data.setTransactionSummaries(account.transactions().stream().map(Transaction::summary).collect(Collectors.toList()));
        data.setCustomer(account.customerId().toString());
        return data;
    }
}
