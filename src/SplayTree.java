import java.util.*;

public class SplayTree <T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T>{

    class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node<T> parent = null;

        Node(T value, Node<T> parent) {
            this.value = value;
            this.parent = parent;
        }

        T getValue() {
            return value;
        }

        Node<T> getLeft() {
            return left;
        }

        Node<T> getRight() {
            return right;
        }

    }

    private Node<T> root = null;

    Node<T> getRoot() {
        return root;
    }

    private int size;


    private void splay(Node<T> node) {

        if (node.parent == null) return;

        Node<T> parent = node.parent;
        Node<T> gParent = parent.parent;

        if (gParent == null) {
            rotate(parent, node);
        }

        else if (gParent.left == parent && parent.left == node || gParent.right == parent && parent.right == node){
            zigZig(gParent, parent, node);
        }

        else if (gParent.left == parent && parent.right == node || gParent.right == parent && parent.left == node) {
            zigZag(gParent, parent, node);
        }

        splay(node);
    }

    private void rotate(Node<T> parent, Node<T> node) {

        if ( parent.left == node) {
            Node<T> nodeRight = node.right;
            node.right = parent;
            parent.parent = node;
            parent.left = nodeRight;
            if (nodeRight != null) nodeRight.parent = parent;
        } else {
            Node<T> nodeLeft = node.left;
            node.left = parent;
            parent.parent = node;
            parent.right = nodeLeft;
            if (nodeLeft != null) nodeLeft.parent = parent;
        }

        node.parent = null;
        root = node;
    }

    private void zigZig(Node<T> gParent, Node<T> parent, Node<T> node) {

        if (gParent.left == parent && parent.left == node){

            Node<T> parentRight = parent.right;

            parent.right = gParent;
            parent.parent = gParent.parent;
            setParent(gParent,parent);

            gParent.parent = parent;
            gParent.left = parentRight;
            if (parentRight != null) parentRight.parent = gParent;

            Node<T> nodeRight = node.right;

            node.right = parent;
            node.parent = parent.parent;
            setParent(parent,node);
            parent.parent = node;
            parent.left = nodeRight;
            if (nodeRight != null) nodeRight.parent = parent;
        }

        if (gParent.right == parent && parent.right == node){

            Node<T> parentLeft = parent.left;

            parent.left = gParent;
            parent.parent = gParent.parent;
            setParent(gParent,parent);

            gParent.parent = parent;
            gParent.right = parentLeft;
            if (parentLeft != null) parentLeft.parent = gParent;

            Node<T> nodeLeft = node.left;

            node.left = parent;
            node.parent = parent.parent;
            setParent(parent,node);

            parent.parent = node;
            parent.right = nodeLeft;
            if (nodeLeft != null) nodeLeft.parent = parent;
        }

        if (node.parent == null) root = node;
    }

    private void zigZag (Node<T> gParent, Node<T> parent, Node<T> node) {

        if (gParent.right == parent && parent.left == node){

            Node<T> nodeRight = node.right;

            gParent.right = node;
            node.parent = gParent;
            node.right = parent;
            parent.parent = node;
            parent.left = nodeRight;
            if (nodeRight != null) nodeRight.parent = parent;

            Node<T> nodeLeftSon = node.left;

            node.left = gParent;
            node.parent = gParent.parent;
            setParent(gParent,node);

            gParent.parent = node;
            gParent.right = nodeLeftSon;
            if (nodeLeftSon != null) nodeLeftSon.parent = gParent;
        }

        if (gParent.left == parent && parent.right == node){

            Node<T> nodeLeft = node.left;

            gParent.left = node;
            node.parent = gParent;
            node.left = parent;
            parent.parent = node;
            parent.right = nodeLeft;
            if (nodeLeft != null) nodeLeft.parent = parent;

            Node<T> nodeRightSon = node.right;

            node.right = gParent;
            node.parent = gParent.parent;
            setParent(gParent,node);

            gParent.parent = node;
            gParent.left = nodeRightSon;
            if (nodeRightSon != null) nodeRightSon.parent = gParent;
        }

        if (node.parent == null) root = node;
    }

    private void setParent(Node<T> first, Node<T> second) {
        if (second.parent != null) {
            if (first.parent.left == first) first.parent.left = second;
            else first.parent.right = second;
        }

    }

    @Override
    public boolean add(T value) {
        Node<T> closest = findNode(value);
        int comparison = closest == null ? -1 : value.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(value, null);
        if (closest == null) {

            root = newNode;
            size ++;
            return true;
        }
        if (comparison < 0) {

            assert closest.left == null;
            closest.left = newNode;
            closest.left.parent = closest;
            splay(closest.left);
        }
        else {

            assert closest.right == null;
            closest.right = newNode;
            closest.right.parent = closest;
            splay(closest.right);
        }
        size++;
        return true;
    }

    private Node<T> findNode(T value) {
        if (root == null) return null;
        return findNode(root, value);
    }

    private Node<T> findNode(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return findNode(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return findNode(start.right, value);
        }
    }

    public void remove(T value) {

        Node<T> closest = findNode(value);
        int comparison = closest == null ? -1 : value.compareTo(closest.value);
        if (comparison != 0) {
            return;
        }

        findVertex(value);

        Node<T> leftTreeRoot = root.left;
        Node<T> rightTreeRoot = root.right;

        if (leftTreeRoot != null) {
            leftTreeRoot = findMax(leftTreeRoot);
            leftTreeRoot.right = rightTreeRoot;
            if (rightTreeRoot != null) {
                rightTreeRoot.parent = leftTreeRoot;
            }

            root = leftTreeRoot;
            size --;
        }
        else{
            rightTreeRoot.parent = null;
            root = rightTreeRoot;
            size --;
        }
    }

    private Node<T> findMax(Node<T> root){
        Node<T> currentNode = root;

        while (currentNode.right != null){
            currentNode = currentNode.right;
        }

        splay(currentNode);
        return currentNode;
    }


    public void findVertex(T value){
        Node<T> currentNode = root;

        while(currentNode.value.compareTo(value) != 0){
            if (currentNode.value.compareTo(value) < 0) {
                if (currentNode.right == null) return;
                currentNode = currentNode.right;
            }
            else {
                if (currentNode.left == null) return;
                currentNode = currentNode.left;
            }
        }

        splay(currentNode);
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }


    public boolean contains(T value){
        Node<T> currentNode = root;
        if (currentNode == null) return false;

        while(currentNode.value.compareTo(value) != 0){
            if (currentNode.value.compareTo(value) < 0) {
                if (currentNode.right == null) return false;
                currentNode = currentNode.right;
            }
            else {
                if (currentNode.left == null) return false;
                currentNode = currentNode.left;
            }
        }

        return true;
    }

    public T getRootValue() {

        return root.value;
    }

    @Override
    public Iterator<T> iterator () {

        return new TreeIterator();
    }

    public class TreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private List<Node> listOfNodes;

        private TreeIterator() {
            listOfNodes = new ArrayList<>();
            fillListOfNodes(root);
        }

        private void fillListOfNodes(Node current){
            listOfNodes.add(current);
            if (current.left != null) fillListOfNodes(current.left);
            if (current.right != null) fillListOfNodes(current.right);
        }

        private Node findNext() {
            if (listOfNodes.size() == 0) return null;

            Node<T> result = listOfNodes.get(0);
            if (current == null){
                for (Node<T> node: listOfNodes){
                    if (node.value.compareTo(result.value) < 0){ result = node; }
                }
            }
            else {
                for (Node<T> node : listOfNodes) {
                    if (node.value.compareTo(result.value) < 0 && node.value.compareTo(current.value) > 0) {  result = node;  }
                }
            }

            return result;
        }

        @Override
        public boolean hasNext() {

            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null)
                throw new NoSuchElementException();
            listOfNodes.remove(current);
            return (T) current.value;
        }

        @Override
        public void remove() {

            SplayTree.this.remove(current);
        }
    }

    @Override
    public int size () {

        return size;
    }

    @Override
    public Comparator<? super T> comparator () {

        return null;

    }

    @Override
    public SortedSet<T> subSet (T fromElement, T toElement){

        return new SubSet<>(fromElement,toElement,this);

    }

    @Override
    public SortedSet<T> headSet (T toElement){

        return new HeadSet<>(toElement,this);
    }

    @Override
    public SortedSet<T> tailSet (T fromElement){

        return new TailSet<>(fromElement,this);
    }

    @Override
    public T first () {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last () {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
