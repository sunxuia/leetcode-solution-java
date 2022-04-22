package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1839. Longest Substring Of All Vowels in Order
 * https://leetcode.com/problems/longest-substring-of-all-vowels-in-order/
 *
 * A string is considered beautiful if it satisfies the following conditions:
 *
 * Each of the 5 English vowels ('a', 'e', 'i', 'o', 'u') must appear at least once in it.
 * The letters must be sorted in alphabetical order (i.e. all 'a's before 'e's, all 'e's before 'i's, etc.).
 *
 * For example, strings "aeiou" and "aaaaaaeiiiioou" are considered beautiful, but "uaeio", "aeoiu", and "aaaeeeooo" are
 * not beautiful.
 *
 * Given a string word consisting of English vowels, return the length of the longest beautiful substring of word. If no
 * such substring exists, return 0.
 *
 * A substring is a contiguous sequence of characters in a string.
 *
 * Example 1:
 *
 * Input: word = "aeiaaioaaaaeiiiiouuuooaauuaeiu"
 * Output: 13
 * Explanation: The longest beautiful substring in word is "aaaaeiiiiouuu" of length 13.
 *
 * Example 2:
 *
 * Input: word = "aeeeiiiioooauuuaeiou"
 * Output: 5
 * Explanation: The longest beautiful substring in word is "aeiou" of length 5.
 *
 * Example 3:
 *
 * Input: word = "a"
 * Output: 0
 * Explanation: There is no beautiful substring, so return 0.
 *
 * Constraints:
 *
 * 1 <= word.length <= 5 * 10^5
 * word consists of characters 'a', 'e', 'i', 'o', and 'u'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1839_LongestSubstringOfAllVowelsInOrder {

    @Answer
    public int longestBeautifulSubstring(String word) {
        int prev = -1, start = 0, res = 0;
        for (int i = 0; i < word.length(); i++) {
            int index = MAP[word.charAt(i)];
            if (prev == index || prev + 1 == index) {
                if (prev == -1) {
                    start = i;
                }
                if (index == 4) {
                    res = Math.max(res, i - start + 1);
                }
                prev = index;
            } else if (index == 0) {
                prev = 0;
                start = i;
            } else {
                prev = -1;
            }
        }
        return res;
    }

    private static final int[] MAP = new int[128];

    static {
        MAP['a'] = 0;
        MAP['e'] = 1;
        MAP['i'] = 2;
        MAP['o'] = 3;
        MAP['u'] = 4;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aeiaaioaaaaeiiiiouuuooaauuaeiu").expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aeeeiiiioooauuuaeiou").expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create("a").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("auaeiaeiou").expect(5);

}
