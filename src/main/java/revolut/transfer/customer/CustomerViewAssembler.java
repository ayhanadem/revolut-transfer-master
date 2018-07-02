package revolut.transfer.customer;

import javax.inject.Singleton;

@Singleton
public class CustomerViewAssembler {

    CustomerView convert(Customer customer) {
        CustomerView data = new CustomerView();
        data.setId(customer.id().toString());
        data.setName(customer.name());
        return data;
    }
}
