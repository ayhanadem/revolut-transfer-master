package revolut.transfer.customer;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import revolut.transfer.domain.DomainEvents;
import revolut.transfer.domain.events.CustomerCreatedEvent;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static revolut.transfer.domain.Constants.DEFAULT_PAGE_INDEX;
import static revolut.transfer.domain.Constants.DEFAULT_PAGE_SIZE;

@Controller("/customers")
public class CustomerController {

    @Inject
    final CustomerRepository customerRepository;

    @Inject
    final CustomerViewAssembler customerViewAssembler;

    @Inject
    final DomainEvents domainEvents;


    protected CustomerController(CustomerRepository customerRepository, CustomerViewAssembler customerViewAssembler, DomainEvents domainEvents) {
        this.customerRepository = customerRepository;
        this.customerViewAssembler = customerViewAssembler;
        this.domainEvents = domainEvents;
    }

    @Get("/{id}")
    public CustomerView single(UUID id) {
        return customerViewAssembler.convert(customerRepository.findById(id));
    }

    @Get("/")
    public List<CustomerView> list(Optional<Integer> page, Optional<Integer> size) {
        return customerRepository.list(page.orElse(DEFAULT_PAGE_INDEX), size.orElse(DEFAULT_PAGE_SIZE))
                .stream()
                .map(customerViewAssembler::convert)
                .collect(Collectors.toList());
    }

    @Post("/")
    public HttpResponse create(CreateCustomerCommand createCommand) {
        Customer customer = new Customer(createCommand.getName());
        customerRepository.save(customer);

        domainEvents.addEvent(new CustomerCreatedEvent(customer.id()));

        return HttpResponse.created(URI.create("/customers/" + customer.id()));

    }

    //DATA
    public static class CreateCustomerCommand {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}