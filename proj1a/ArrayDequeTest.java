import org.junit.Test;
import static org.junit.Assert.*;

class Atest<T> {
    Object[] array;
    public Atest() {
        array =  new Object[1];
    }

    public void fun(T i) {
        array[0] = i;
    }

    public T get(int index) {
        return (T)array[index];
    }
}

public class ArrayDequeTest {
    @Test
    public void testaddsizeempty() {
        ArrayDeque<String> dq = new ArrayDeque<>();
        assertEquals(true, dq.isEmpty());
        dq.addFirst("first");
        assertEquals(1, dq.size());

        dq.addLast("last");
        assertEquals(2, dq.size());

        dq.printDeque();

        String first = dq.removeFirst();
        assertEquals("first", first);

        String last = dq.removeLast();
        assertEquals("last", last);

        assertEquals(0, dq.size());
    }

    @Test
    public void testAtest() {
        Atest<Integer> o = new Atest<>();
        o.array[0] = 1;
        System.out.println(o.array[0]);
    }

    @Test
    public void testgrowshrink() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            dq.addLast(i);
        }

        for (int i = -16; i < 0; i++) {
            dq.addFirst(i);
        }
        dq.printDeque();
        for (int i = 0; i < 16; ++i) {
            assertEquals(-1 - i , (long)dq.get(i));
        }
        for (int i = 16; i < 32; ++i ) {
            assertEquals(i - 16, (long)dq.get(i));
        }
        for (int i = 0; i < 30; i++) {
            dq.removeFirst();
        }
        assertEquals(2, dq.size());
        dq.printDeque();
    }
}
