package revolut.transfer.customer;

import revolut.transfer.domain.Repository;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.UUID;

@Singleton
public class CustomerRepository extends Repository<Customer, UUID> {


    @Override
    protected Comparator<Customer> listComparator() {
        return Comparator.comparing(Customer::createdAt);
    }
}
