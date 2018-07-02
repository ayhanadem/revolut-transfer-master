package revolut.transfer.domain;

import java.util.*;

public abstract class Repository<E extends AggregateRoot<ID>, ID> {

    HashMap<ID, E> db = new HashMap<>();

    public E findById(ID id) {
        return db.get(id);
    }


    public List<E> list(int page, int size) {
        ArrayList<E> values = new ArrayList<>(db.values());
        Collections.sort(values, listComparator());
        int fromIndex = (page - 1) * size;
        int toIndex = fromIndex + size;
        toIndex = toIndex >= db.size() ? db.size() : toIndex;
        try {
            return values.subList(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) {
            return Collections.emptyList();
        }

    }

    protected abstract Comparator<E> listComparator();

    public void save(E e) {
        db.put(e.id(), e);
    }

}
