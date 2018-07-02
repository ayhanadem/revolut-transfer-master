package revolut.transfer.domain;

import java.util.UUID;
import java.util.function.Supplier;

public class UUIDSupplier implements Supplier<UUID> {

    public static final Supplier<UUID> INSTANCE = new UUIDSupplier();

    @Override
    public UUID get() {
        return UUID.randomUUID();
    }
}
