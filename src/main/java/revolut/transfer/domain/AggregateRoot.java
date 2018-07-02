package revolut.transfer.domain;

import java.util.Objects;
import java.util.function.Supplier;

public class AggregateRoot<T> {

    public AggregateRoot(Supplier<T> supplier) {
        id = supplier.get();
    }

    private final T id;

    public T id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRoot<?> that = (AggregateRoot<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
