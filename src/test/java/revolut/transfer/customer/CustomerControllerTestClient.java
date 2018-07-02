package revolut.transfer.customer;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.Client;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@Client("/customers")
public interface CustomerControllerTestClient {

    @Get("/{id}")
    public CustomerView single(UUID id);

    @Get("/")
    public List<CustomerView> list(Optional<Integer> page, Optional<Integer> size);

    @Post("/")
    public HttpResponse create(CustomerController.CreateCustomerCommand createCommand);
}