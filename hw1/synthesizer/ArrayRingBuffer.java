package synthesizer;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    private Object[] buffer;
    private int first;
    private int last;

    public ArrayRingBuffer(int n) {
        capacity = n;
        fillcount = 0;
        first = last = 0;
        buffer = new Object[n + 1];
    }
    public void enqueue(T x) {
        if (isFull()) {
            return;
        }
        last = (last + 1) % (capacity + 1);
        buffer[last] = x;
        fillcount++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        first = (first + 1) % (capacity + 1);
        fillcount--;
        return (T)buffer[first];
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        int pos = (first + 1) % (capacity + 1);
        return (T)buffer[pos];
    }

}
