package revolut.transfer.customer;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class CustomerControllerTest {

    static EmbeddedServer embeddedServer;

    @BeforeClass
    public static void setup() {
        embeddedServer = ApplicationContext.run(
                EmbeddedServer.class
        );
    }

    @AfterClass
    public static void cleanup() {
        if (embeddedServer != null) {
            embeddedServer.stop();
        }
    }


    @Test
    public void create(){
        CustomerControllerTestClient client = embeddedServer.getApplicationContext().getBean(CustomerControllerTestClient.class);

        CustomerController.CreateCustomerCommand command = new CustomerController.CreateCustomerCommand();
        String adem = "Adem";

        command.setName(adem);

        HttpStatus status = client
                .create(command)
                .getStatus();
        Assert.assertEquals(status, HttpStatus.CREATED);


        List<CustomerView> list = client.list(Optional.empty(), Optional.empty());
        Assert.assertTrue(list.size() > 0);
        Assert.assertEquals(adem,list.get(0).getName());

    }
}
