package revolut.transfer.account;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.Client;
import revolut.transfer.customer.CustomerController;
import revolut.transfer.customer.CustomerView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Client("/accounts")
public interface AccountControllerTestClient
{


    @Get("/{id}")
    public AccountView single(UUID id) ;

    @Get("/")
    public List<AccountView> list(Optional<Integer> page, Optional<Integer> size);

    @Post("/{id}/transfer")
    public AccountView transfer(@Parameter("id") UUID id, @Body AccountController.TransferMoneyCommand transferCommand);

    @Post("/{id}/deposit")
    public AccountView deposit(@Parameter("id") UUID id, @Body AccountController.DepositMoneyCommand depositCommand);


    @Post("/{id}/withdraw")
    public AccountView withdraw(@Parameter("id") UUID id, @Body AccountController.WithdrawMoneyCommand withdrawCommand);
}