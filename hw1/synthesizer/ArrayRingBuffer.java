package synthesizer;

import java.util.Iterator;

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
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            return;
        }
        last = (last + 1) % (capacity + 1);
        buffer[last] = x;
        fillcount++;
    }
    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        first = (first + 1) % (capacity + 1);
        fillcount--;
        return (T)buffer[first];
    }
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        int pos = (first + 1) % (capacity + 1);
        return (T)buffer[pos];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements  Iterator<T> {
        private int pos;
        private int curnum;

        public ArrayRingBufferIterator() {
            pos = (ArrayRingBuffer.this.first + 1) % ArrayRingBuffer.this.buffer.length;
            curnum = 0;
        }
        public boolean hasNext() {
            return curnum < ArrayRingBuffer.this.fillcount;
        }

        public T next() {
            T res = (T)ArrayRingBuffer.this.buffer[pos];
            pos = (pos + 1) % ArrayRingBuffer.this.buffer.length;
            curnum++;
            return res;
        }
    }
}
