package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1915. Number of Wonderful Substrings
 * https://leetcode.com/problems/number-of-wonderful-substrings/
 *
 * A wonderful string is a string where at most one letter appears an odd number of times.
 *
 * For example, "ccjjc" and "abab" are wonderful, but "ab" is not.
 *
 * Given a string word that consists of the first ten lowercase English letters ('a' through 'j'), return the number of
 * wonderful non-empty substrings in word. If the same substring appears multiple times in word, then count each
 * occurrence separately.
 *
 * A substring is a contiguous sequence of characters in a string.
 *
 * Example 1:
 *
 * Input: word = "aba"
 * Output: 4
 * Explanation: The four wonderful substrings are underlined below:
 * - "aba" -> "a"
 * - "aba" -> "b"
 * - "aba" -> "a"
 * - "aba" -> "aba"
 *
 * Example 2:
 *
 * Input: word = "aabb"
 * Output: 9
 * Explanation: The nine wonderful substrings are underlined below:
 * - "aabb" -> "a"
 * - "aabb" -> "aa"
 * - "aabb" -> "aab"
 * - "aabb" -> "aabb"
 * - "aabb" -> "a"
 * - "aabb" -> "abb"
 * - "aabb" -> "b"
 * - "aabb" -> "bb"
 * - "aabb" -> "b"
 *
 * Example 3:
 *
 * Input: word = "he"
 * Output: 2
 * Explanation: The two wonderful substrings are underlined below:
 * - "he" -> "h"
 * - "he" -> "e"
 *
 * Constraints:
 *
 * 1 <= word.length <= 10^5
 * word consists of lowercase English letters from 'a' to 'j'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1915_NumberOfWonderfulSubstrings {

    @Answer
    public long wonderfulSubstrings(String word) {
        int[] counts = new int[1 << 10];
        counts[0] = 1;
        long res = 0;
        int mask = 0;
        for (int i = 0; i < word.length(); i++) {
            mask ^= 1 << (word.charAt(i) - 'a');
            res += counts[mask];
            for (int j = 0; j < 10; j++) {
                int match = mask ^ (1 << j);
                if (match != mask) {
                    res += counts[match];
                }
            }
            counts[mask]++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aba").expect(4L);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aabb").expect(9L);

    @TestData
    public DataExpectation example3 = DataExpectation.create("he").expect(2L);

}
