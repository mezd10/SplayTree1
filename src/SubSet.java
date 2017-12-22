import java.util.*;

//возвращает от fromElement до toElement включительно, будет вызывать IllegalArgumentException() при попытке вставить элемент за пределы его диапазона

public class SubSet  <T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T>{

    private T fromElement;
    private T toElement;
    private SplayTree<T> interval;

    SubSet(T fromElement,T toElement, SplayTree<T> interval){
        this.fromElement = fromElement;
        this.toElement = toElement;
        this.interval = interval;
    }

    public boolean remove(T value){
        if (toElement.compareTo(value) > 0 && fromElement.compareTo(value) <= 0) interval.remove(value);
        else throw new IllegalArgumentException();

        return true;
    }

    public boolean add(T value){
        if(toElement.compareTo(value) > 0 && fromElement.compareTo(value) <= 0) interval.add(value);
        else throw new IllegalArgumentException();

        return true;
    }


    @Override
    public Iterator<T> iterator() {
        return new SubSetIterator();
    }

    public class SubSetIterator  implements Iterator<T>{

        private List<T> listOfNodes;

        private SubSetIterator(){
            listOfNodes = new ArrayList<>();
            fillListOfNodes();
        }

        private void fillListOfNodes(){
            Iterator treeIterator = interval.iterator();
            while (treeIterator.hasNext()){
                T next = (T) treeIterator.next();
                if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0) listOfNodes.add(next);
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
        int result = 0;
        Iterator iterator = interval.iterator();
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0){
                result++;
            }
        }

        return result;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {

        if (fromElement.compareTo(this.fromElement) >= 0 && toElement.compareTo(this.toElement) < 0)
            return new SubSet<>(fromElement,toElement,interval);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public SortedSet<T> headSet(T toElement) {

        if (toElement.compareTo(this.toElement) < 0 && toElement.compareTo(this.fromElement) >= 0){
            return new SubSet<>(this.fromElement, toElement, interval);
        } else throw new IndexOutOfBoundsException();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {

        if (fromElement.compareTo(this.fromElement) >= 0 && fromElement.compareTo(this.toElement) < 0){
            return new SubSet<>(fromElement, this.toElement, interval);
        } else throw new IndexOutOfBoundsException();
    }

    @Override
    public T first() {
        Iterator iterator = interval.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = next.compareTo(result) < 0 ? next : result;
            }
        }
        return result;
    }

    @Override
    public T last() {
        Iterator iterator = interval.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 ? next : result;
            }
        }
        return result;
    }
}
