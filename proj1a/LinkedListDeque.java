public class LinkedListDeque<T> {
    private class Node {
        Node prev;
        Node next;
        T item;
        Node(Node prev, Node next, T item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    private int size;
    private Node sentinel;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        sentinel.next = new Node(sentinel, sentinel.next, item);
        sentinel.next.next.prev = sentinel.next;
        sentinel.next.prev = sentinel;
        size++;
    }

    public void addLast(T item) {
        sentinel.prev = new Node(sentinel.prev, sentinel, item);
        sentinel.prev.prev.next = sentinel.prev;
        sentinel.prev.next = sentinel;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T res = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        --size;
        return res;
    }

    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T res = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        --size;
        return res;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node p = sentinel.next;
        for (int i = 0; i < index; ++i) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursive(0, index, sentinel.next);
    }

    private T getRecursive(int pos, int index, Node x) {
        if (pos == index) {
            return x.item;
        }
        return getRecursive(pos + 1, index, x.next);
    }

}














