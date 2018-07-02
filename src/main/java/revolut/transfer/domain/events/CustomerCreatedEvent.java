package revolut.transfer.domain.events;

import java.util.UUID;

public class CustomerCreatedEvent {

    private UUID id;

    public CustomerCreatedEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}