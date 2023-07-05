
public class ArrayDeque<T>  implements Deque<T> {
    private Object[] array;
    private int size;
    private int nextLast;
    private int nextFirst;

    public ArrayDeque() {
        array = new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private boolean isArrayFull() { return size + 2 >= array.length; }

    private void grow() {
        Object[] newArray = new Object[array.length * 2];
        //copy
        for (int i = 1; i <= size; ++i) {
            nextFirst = (nextFirst + 1) % array.length;
            newArray[i] = array[nextFirst];
        }
        array = newArray;
        //reset nextFirst and nextLast
        nextFirst = 0;
        nextLast = nextFirst + size + 1;
    }

    private void shrink() {
        Object[] newArray = new Object[array.length / 2];
        //copy
        for(int i = 1; i <= size; ++i) {
            nextFirst = (nextFirst + 1) % array.length;
            newArray[i] = array[nextFirst];
        }
        array = newArray;
        //reset nextFirst and nextLast
        nextFirst = 0;
        nextLast = nextFirst + size + 1;
    }
    @Override
    public void addFirst(T item) {
        if (isArrayFull()) {
            grow();
        }
        array[nextFirst] = item;
        nextFirst = (nextFirst + array.length - 1) % array.length;
        size++;
    }
    @Override
    public void addLast(T item) {
        if (isArrayFull()) {
            grow();
        }
        array[nextLast] = item;
        nextLast = (nextLast + 1) % array.length;
        size++;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        int pos = nextFirst;
        for (int i = 1; i <= size; ++i) {
            pos = (pos + 1) % array.length;
            System.out.println(array[pos]);
        }
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) % array.length;
        size--;
        T res = (T)array[nextFirst];
        if (array.length >= 16 && array.length / size >= 4) {
            shrink();
        }
        return res;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = (nextLast + array.length -1) % array.length;
        size--;
        T res = (T)array[nextLast];
        if (array.length >= 16 && array.length / size >= 4) {
            shrink();
        }
        return res;
    }
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int pos = (nextFirst + 1 + index) % array.length;
        return (T)array[pos];
    }
}
