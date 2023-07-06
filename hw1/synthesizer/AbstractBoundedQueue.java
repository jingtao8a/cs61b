package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillcount;
    protected int capacity;
    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillcount;
    }
}
