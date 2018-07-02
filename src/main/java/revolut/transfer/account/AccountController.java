package revolut.transfer.account;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import revolut.transfer.account.transaction.Deposit;
import revolut.transfer.account.transaction.Withdraw;
import revolut.transfer.domain.Money;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static revolut.transfer.domain.Constants.DEFAULT_PAGE_INDEX;
import static revolut.transfer.domain.Constants.DEFAULT_PAGE_SIZE;

@Controller("/accounts")
public class AccountController {

    @Inject
    final AccountRepository accountRepository;

    @Inject
    final AccountViewAssembler accountViewAssembler;


    protected AccountController(AccountRepository accountRepository, AccountViewAssembler accountViewAssembler) {
        this.accountRepository = accountRepository;
        this.accountViewAssembler = accountViewAssembler;
    }

    @Get("/{id}")
    public AccountView single(UUID id) {
        return accountViewAssembler.convert(accountRepository.findById(id));
    }

    @Get("/")
    public List<AccountView> list(Optional<Integer> page, Optional<Integer> size) {
        return accountRepository.list(page.orElse(DEFAULT_PAGE_INDEX), size.orElse(DEFAULT_PAGE_SIZE))
                .stream()
                .map(accountViewAssembler::convert)
                .collect(Collectors.toList());
    }

    @Post("/{id}/transfer")
    public AccountView transfer(@Parameter("id") UUID id, @Body TransferMoneyCommand transferCommand) {
        UUID to = UUID.fromString(transferCommand.getTo());

        //this processes are must be transactional.
        Account account= accountRepository.findById(id);
        Account accountTo = accountRepository.findById(to);

        account.withdraw(new Withdraw(Money.of(transferCommand.getValue())));
        accountTo.deposit(new Deposit(Money.of(transferCommand.getValue()),Optional.of(id)));
        accountRepository.save(account);
        accountRepository.save(accountTo);
        return accountViewAssembler.convert(account);
    }

    @Post("/{id}/deposit")
    public AccountView deposit(@Parameter("id") UUID id, @Body DepositMoneyCommand depositCommand) {
        Deposit deposit = new Deposit(Money.of(depositCommand.getValue()));
        Account account = accountRepository.findById(id);
        account.deposit(deposit);
        accountRepository.save(account);
        return accountViewAssembler.convert(account);
    }

    @Post("/{id}/withdraw")
    public AccountView withdraw(@Parameter("id") UUID id, @Body WithdrawMoneyCommand withdrawCommand) {
        Withdraw withdraw = new Withdraw(Money.of(withdrawCommand.getValue()));
        Account account = accountRepository.findById(id);
        account.withdraw(withdraw);
        accountRepository.save(account);
        return accountViewAssembler.convert(account);
    }





    //DATA
    public static class TransferMoneyCommand {

        private String to;
        private int value;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class DepositMoneyCommand {
        int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class WithdrawMoneyCommand {
        int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}