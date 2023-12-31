import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("cabac"));
        assertTrue(palindrome.isPalindrome("aaaaaa"));
        assertTrue(palindrome.isPalindrome("123321"));
        assertFalse(palindrome.isPalindrome("a123"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("afasdjl"));
    }

    @Test
    public void testIsPalindrome2() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("daube",cc));
        assertTrue(palindrome.isPalindrome("done",cc));
        assertTrue(palindrome.isPalindrome("dope",cc));

        assertFalse(palindrome.isPalindrome("dab",cc));
        assertFalse(palindrome.isPalindrome("da",cc));
    }
}
