package revolut.transfer.account.transaction;

import revolut.transfer.domain.Money;

public class Withdraw extends Transaction {


    public Withdraw(Money money) {
        super(money);
    }

    @Override
    public String summary() {
        return String.format("withdraw %s", money().asString());
    }
}
