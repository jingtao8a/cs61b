package synthesizer;

public class GuitarString {
    private BoundedQueue<Double> queue;
    private static final int SR = 44100;
    private static final double DECAY = .996;

    public GuitarString(double frequency) {
        int capacity = (int)Math.round(SR / frequency);
        queue = new ArrayRingBuffer<Double>(capacity);
    }
    public void pluck() {
        while (!queue.isEmpty()) {
            queue.dequeue();
        }
        while (!queue.isFull()) {
            queue.enqueue(Math.random() - 0.5);
        }
    }

    public void tic() {
        double dqValue = queue.dequeue();
        queue.enqueue(DECAY * (dqValue + queue.peek()) * 0.5);
    }

    public double sample() {
        return queue.peek();
    }
}
