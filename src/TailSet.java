import java.util.*;

public class TailSet <T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private T fromElement;
    private SplayTree<T> interval;

    TailSet(T fromElement, SplayTree<T> interval){
        this.fromElement = fromElement;
        this.interval = interval;
    }

    @Override
    public Iterator<T> iterator() {

        return new TailSetIterator();
    }

    public class TailSetIterator implements Iterator<T>{

        private List<T> listOfNodes;

        private TailSetIterator(){
            listOfNodes = new ArrayList<>();
            fillListOfNodes();
        }

        private void fillListOfNodes(){
            Iterator iterator = interval.iterator();
            while (iterator.hasNext()){
                T next = (T) iterator.next();
                if (next.compareTo(fromElement) >= 0) listOfNodes.add(next);
            }
        }

        @Override
        public boolean hasNext() {
            return !listOfNodes.isEmpty();
        }

        @Override
        public T next() {
            if (hasNext()){
                T result = listOfNodes.get(0);
                listOfNodes.remove(0);
                return result;
            }else throw new NoSuchElementException();
        }
    }

    public boolean remove(T value){
        if (fromElement.compareTo(value) > 0) throw new IllegalArgumentException();
        interval.remove(value);
        return true;
    }

    public boolean add(T value){
        if (fromElement.compareTo(value) > 0) throw new IllegalArgumentException();
        interval.add(value);
        return true;
    }

    @Override
    public int size() {
        int result = 0;
        Iterator iterator = interval.iterator();
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0) result ++;
        }

        return result;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {

        if (fromElement.compareTo(this.fromElement) >= 0 && toElement.compareTo(this.fromElement) > 0 && toElement.compareTo(this.last()) <=0)
            return new SubSet<>(fromElement,toElement,interval);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public SortedSet<T> headSet(T toElement) {

        if (toElement.compareTo(this.fromElement) > 0 && toElement.compareTo(this.last()) <= 0){
            return new SubSet<>(this.fromElement, toElement, interval);
        }else throw new IndexOutOfBoundsException();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {

        if (fromElement.compareTo(this.fromElement) >= 0 && fromElement.compareTo(this.last()) <= 0)
            return new TailSet<>(fromElement,interval);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public T first() {
        Iterator iterator = interval.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0){
                if (result == null) result = next;
                else result = result.compareTo(next) > 0 ? next : result;
            }
        }
        return result;
    }

    @Override
    public T last() {
        Iterator itrator = interval.iterator();
        T result = null;
        while (itrator.hasNext()){
            T next = (T) itrator.next();
            if (next.compareTo(fromElement) >= 0){
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 ? next : result;
            }
        }
        return result;
    }
}
