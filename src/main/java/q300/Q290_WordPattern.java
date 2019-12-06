package q300;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/word-pattern/
 *
 * Given a pattern and a string str, find if str follows the same pattern.
 *
 * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in
 * str.
 *
 * Example 1:
 *
 * Input: pattern = "abba", str = "dog cat cat dog"
 * Output: true
 *
 * Example 2:
 *
 * Input:pattern = "abba", str = "dog cat cat fish"
 * Output: false
 *
 * Example 3:
 *
 * Input: pattern = "aaaa", str = "dog cat cat dog"
 * Output: false
 *
 * Example 4:
 *
 * Input: pattern = "abba", str = "dog dog dog dog"
 * Output: false
 *
 * Notes:
 * You may assume pattern contains only lowercase letters, and str contains lowercase letters that may be separated
 * by a single space.
 */
@RunWith(LeetCodeRunner.class)
public class Q290_WordPattern {

    @Answer
    public boolean wordPattern(String pattern, String str) {
        final int len = pattern.length();
        String[] words = str.split(" ");
        if (words.length != len) {
            return false;
        }
        Map<Character, String> map1 = new HashMap<>();
        Map<String, Character> map2 = new HashMap<>();
        for (int i = 0; i < len; i++) {
            String expectedWord = map1.get(pattern.charAt(i));
            Character expectedChar = map2.get(words[i]);
            Character c = pattern.charAt(i);
            if (expectedWord == null || expectedChar == null) {
                if (expectedWord == null && expectedChar == null) {
                    map1.put(pattern.charAt(i), words[i]);
                    map2.put(words[i], c);
                } else {
                    return false;
                }
            } else if (!expectedWord.equals(words[i]) || !expectedChar.equals(c)) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abba", "dog cat cat dog").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abba", "dog cat cat fish").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("aaaa", "dog cat cat dog").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("abba", "dog dog dog dog").expect(false);

}
