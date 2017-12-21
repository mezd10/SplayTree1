import java.util.*;
//строго меньше, чем toElement, бросает IllegalArgumentException() при попытке вставть элемент в не пределах interval


public class HeadSet <T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private T toElement;
    private SplayTree<T> interval;

    HeadSet(T toElement, SplayTree<T> interval){
        this.toElement = toElement;
        this.interval = interval;
    }

    public boolean add(T value){
        if (toElement.compareTo(value) > 0) interval.add(value);
        else throw new IllegalArgumentException();

        return true;
    }

    public boolean remove(T value){
        if (toElement.compareTo(value) > 0) {
            interval.remove(value);
        }
        else throw new IllegalArgumentException();

        return true;
    }

    @Override
    public Iterator<T> iterator() {

        return new HeadSetIterator();
    }

    public class HeadSetIterator implements Iterator<T>{

        private List<T> listOfNodes;

        private HeadSetIterator(){
            listOfNodes = new ArrayList<>();
            fillListOfNodes();
        }

        private void fillListOfNodes(){
            Iterator iterator = interval.iterator();
            while (iterator.hasNext()){
                T next = (T) iterator.next();
                if (next.compareTo(toElement) < 0) listOfNodes.add(next);
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
            }
            else throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        int result =0;
        Iterator iterator = interval.iterator();
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (toElement.compareTo(next) > 0) result++;
        }

        return result;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {

        if (fromElement.compareTo(this.toElement) < 0 && toElement.compareTo(this.toElement) < 0 && fromElement.compareTo(this.first()) >= 0)
            return new SubSet<>(fromElement,toElement,interval);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public SortedSet<T> headSet(T toElement) {

        if (toElement.compareTo(this.toElement) < 0 && toElement.compareTo(this.last()) <= 0 && toElement.compareTo(this.first()) >= 0)
            return new HeadSet<>(toElement,interval);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (fromElement.compareTo(this.first()) >= 0 && fromElement.compareTo(this.toElement) < 0){
            return new SubSet<>(fromElement, this.toElement, interval);
        }
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public T first() {
        Iterator iterator = interval.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = result.compareTo(next) > 0 ? next : result;
            }
        }

        return result;
    }

    @Override
    public T last() {
        Iterator iterator = interval.iterator();
        T result = null;
        while(iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 && next.compareTo(toElement) < 0 ? next : result;
            }
        }

        return result;
    }
}