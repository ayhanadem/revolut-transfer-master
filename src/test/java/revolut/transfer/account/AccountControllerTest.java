package revolut.transfer.account;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import revolut.transfer.customer.CustomerController;
import revolut.transfer.customer.CustomerControllerTestClient;
import revolut.transfer.customer.CustomerView;
import revolut.transfer.domain.Money;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountControllerTest
{

    static EmbeddedServer embeddedServer;

    @BeforeClass
    public static void setup()
    {
        embeddedServer = ApplicationContext.run(
                EmbeddedServer.class
        );
    }

    @AfterClass
    public static void cleanup()
    {
        if (embeddedServer != null)
        {
            embeddedServer.stop();
        }
    }


    @Test
    public void deposit()
    {


        AccountRepository accountRepository = embeddedServer.getApplicationContext().getBean(AccountRepository.class);

        Account account = Account.create(Money.of(100), UUID.randomUUID());
        accountRepository.save(account);


        AccountControllerTestClient client = embeddedServer.getApplicationContext().getBean(AccountControllerTestClient.class);


        AccountController.DepositMoneyCommand command = new AccountController.DepositMoneyCommand();
        command.setValue(200);


        AccountView accView = client
                .deposit(account.id(), command);


        Assert.assertEquals("deposit 200 by itself",accView.getTransactionSummaries().get(0));
        Assert.assertEquals(300, accView.getBalance().intValue());


    }


    @Test
    public void withdraw()
    {


        AccountRepository accountRepository = embeddedServer.getApplicationContext().getBean(AccountRepository.class);

        Account account = Account.create(Money.of(200), UUID.randomUUID());
        accountRepository.save(account);


        AccountControllerTestClient client = embeddedServer.getApplicationContext().getBean(AccountControllerTestClient.class);


        AccountController.WithdrawMoneyCommand command = new AccountController.WithdrawMoneyCommand();
        command.setValue(100);


        AccountView accView = client
                .withdraw(account.id(), command);
        Assert.assertEquals(100, accView.getBalance().intValue());


    }

    @Test(expected=RuntimeException.class)
    public void withdrawMoreThanBalance()
    {


        AccountRepository accountRepository = embeddedServer.getApplicationContext().getBean(AccountRepository.class);

        Account account = Account.create(Money.of(200), UUID.randomUUID());
        accountRepository.save(account);


        AccountControllerTestClient client = embeddedServer.getApplicationContext().getBean(AccountControllerTestClient.class);


        AccountController.WithdrawMoneyCommand command = new AccountController.WithdrawMoneyCommand();
        command.setValue(300);


        AccountView accView = client
                .withdraw(account.id(), command);


    }


    @Test
    public void transfer()
    {


        AccountRepository accountRepository = embeddedServer.getApplicationContext().getBean(AccountRepository.class);

        Account account = Account.create(Money.of(200), UUID.randomUUID());
        accountRepository.save(account);

        Account accountTo = Account.create(Money.of(200), UUID.randomUUID());
        accountRepository.save(accountTo);


        AccountControllerTestClient client = embeddedServer.getApplicationContext().getBean(AccountControllerTestClient.class);


        AccountController.TransferMoneyCommand command = new AccountController.TransferMoneyCommand();
        command.setValue(100);
        command.setTo(accountTo.id().toString());


        AccountView accView = client
                .transfer(account.id(), command);


        AccountView accAfter =client.single(account.id());
        AccountView accToAfter =client.single(accountTo.id());


        System.err.println(accAfter.transactionSummaries);

        System.err.println(accToAfter.transactionSummaries);


        Assert.assertEquals(100, accAfter.getBalance().intValue());
        Assert.assertEquals(300, accToAfter.getBalance().intValue());

    }


    @Test(expected = RuntimeException.class)
    public void transferMoreThanBalance()
    {


        AccountRepository accountRepository = embeddedServer.getApplicationContext().getBean(AccountRepository.class);

        Account account = Account.create(Money.of(200), UUID.randomUUID());
        accountRepository.save(account);

        Account accountTo = Account.create(Money.of(200), UUID.randomUUID());
        accountRepository.save(accountTo);


        AccountControllerTestClient client = embeddedServer.getApplicationContext().getBean(AccountControllerTestClient.class);


        AccountController.TransferMoneyCommand command = new AccountController.TransferMoneyCommand();
        command.setValue(300);
        command.setTo(accountTo.id().toString());


        client.transfer(account.id(), command);


    }
}
