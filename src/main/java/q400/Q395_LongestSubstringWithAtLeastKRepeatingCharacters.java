package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/
 *
 * Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every
 * character in T appears no less than k times.
 *
 * Example 1:
 *
 * Input:
 * s = "aaabb", k = 3
 *
 * Output:
 * 3
 *
 * The longest substring is "aaa", as 'a' is repeated 3 times.
 *
 * Example 2:
 *
 * Input:
 * s = "ababbc", k = 2
 *
 * Output:
 * 5
 *
 * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 */
@RunWith(LeetCodeRunner.class)
public class Q395_LongestSubstringWithAtLeastKRepeatingCharacters {

    // 暴力破解法会超时
    @Answer
    public int longestSubstring(String s, int k) {
        final int length = s.length();
        int[] vals = new int[length];
        for (int i = 0; i < length; i++) {
            vals[i] = s.charAt(i) - 'a';
        }
        int[][] counts = new int[length + 1][];
        counts[0] = new int[26];
        for (int i = 0; i < length; i++) {
            counts[i + 1] = counts[i].clone();
            counts[i + 1][vals[i]]++;
        }

        return calculate(vals, k, counts, 0, length);
    }

    private int calculate(int[] vals, int k, int[][] counts, int start, int end) {
        if (end - start < k) {
            return 0;
        }
        int res = 0, prev = start;
        for (int i = start; i < end; i++) {
            if (counts[end][vals[i]] - counts[start][vals[i]] < k) {
                res = Math.max(res, calculate(vals, k, counts, prev, i));
                prev = i + 1;
            }
        }
        if (prev > start) {
            res = Math.max(res, calculate(vals, k, counts, prev, end));
        } else {
            res = end - start;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("aaabb", 3).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ababbc", 2).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("bbaaacbd", 3).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("ababacb", 3).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.readString("Q395_LongTestData"), 301)
            .expect(301);

}
