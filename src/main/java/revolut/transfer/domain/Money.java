package revolut.transfer.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money implements Value {

    int value;

    private Money(int value) {
        this.value = value;
    }

    public static Money of(int value) {
        if (value < 0) {
            throw new IllegalStateException("illegal money value " + value);
        }
        return new Money(value);
    }

    public Money add(Money money) {
        return of(this.value + money.value);
    }

    public Money substract(Money money) {
        return of(this.value - money.value);
    }

    public BigDecimal asBigDecimal() {
        return BigDecimal.valueOf(value);
    }

    public String asString() {
        return "" + value;
    }

    @Override
    public boolean isSameValue(Value v) {
        return this.equals(v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}
