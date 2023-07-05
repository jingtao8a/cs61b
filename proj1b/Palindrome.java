public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> dq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); ++i) {
            dq.addLast(word.charAt(i));
        }
        return dq;
    }
    public boolean isPalindrome(String word) {
        Deque<Character> dq = wordToDeque(word);
        String reverseWord = "";
        while (!dq.isEmpty()) {
            reverseWord += dq.removeLast();
        }
        return reverseWord.equals(word);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq = wordToDeque(word);
        String reverseWord = "";
        while (!dq.isEmpty()) {
            reverseWord += dq.removeLast();
        }
        int length = word.length();
        if (length % 2 == 0) {
            for (int i = 0; i < length; ++i) {
                if (!cc.equalChars(word.charAt(i), reverseWord.charAt(i))) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < length; ++i) {
                if (i != length / 2 && !cc.equalChars(word.charAt(i), reverseWord.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }
}
