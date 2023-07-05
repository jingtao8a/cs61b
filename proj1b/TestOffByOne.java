import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testequalChars() {
        CharacterComparator cc = new OffByOne();
        assertTrue(cc.equalChars('a', 'b'));
        assertTrue(cc.equalChars('r', 'q'));
        assertFalse(cc.equalChars('a', 'e'));
        assertFalse(cc.equalChars('z', 'a'));
        assertFalse(cc.equalChars('a', 'a'));
    }
}
