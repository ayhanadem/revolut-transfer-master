package revolut.transfer.domain;

import javax.inject.Singleton;
import java.util.Observable;

@Singleton
public class DomainEvents extends Observable {


    public void addEvent(Object event) {
        setChanged();
        notifyObservers(event);
    }


}
