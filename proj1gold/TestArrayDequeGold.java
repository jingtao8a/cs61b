import org.junit.Test;
import static org.junit.Assert.*;
public class TestArrayDequeGold {
    @Test
    public void TestArrayDeque() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                ads.addLast(i);
            } else {
                sad1.addFirst(i);
                ads.addFirst(i);
            }
        }

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                Integer actual = sad1.removeLast();
                Integer expected = ads.removeLast();
                assertEquals("removeLast()", actual, expected);
            } else {
                Integer actual = sad1.removeFirst();
                Integer expected = ads.removeFirst();
                assertEquals("removeFirst", actual, expected);
            }
        }
    }
}
