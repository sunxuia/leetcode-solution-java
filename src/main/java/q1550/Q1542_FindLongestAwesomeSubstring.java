package q1550;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1542. Find Longest Awesome Substring
 * https://leetcode.com/problems/find-longest-awesome-substring/
 *
 * Given a string s. An awesome substring is a non-empty substring of s such that we can make any number of swaps in
 * order to make it palindrome.
 *
 * Return the length of the maximum length awesome substring of s.
 *
 * Example 1:
 *
 * Input: s = "3242415"
 * Output: 5
 * Explanation: "24241" is the longest awesome substring, we can form the palindrome "24142" with some swaps.
 *
 * Example 2:
 *
 * Input: s = "12345678"
 * Output: 1
 *
 * Example 3:
 *
 * Input: s = "213123"
 * Output: 6
 * Explanation: "213123" is the longest awesome substring, we can form the palindrome "231132" with some swaps.
 *
 * Example 4:
 *
 * Input: s = "00"
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s consists only of digits.
 */
@RunWith(LeetCodeRunner.class)
public class Q1542_FindLongestAwesomeSubstring {

    @Answer
    public int longestAwesome(String s) {
        final int n = s.length();
        // starts[i] 表示mask 为i 的组合对应的起始位置
        int[] starts = new int[1024];
        Arrays.fill(starts, n);
        int[] next = new int[1024];
        int res = 1;
        for (int i = 0; i < n; i++) {
            Arrays.fill(next, n);
            int num = 1 << s.charAt(i) - '0';
            // 遍历当前mask, 加入num 计算结果.
            for (int j = 0; j < 1024; j++) {
                int mask = j ^ num;
                if ((mask & mask - 1) == 0) {
                    res = Math.max(res, i - starts[j] + 1);
                }
                next[mask] = Math.min(next[mask], starts[j]);
            }
            next[num] = Math.min(next[num], i);

            int[] t = starts;
            starts = next;
            next = t;
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法, 是对上面解法的优化.
     */
    @Answer
    public int longestAwesome2(String s) {
        final int n = s.length();
        int[] seen = new int[1024];
        Arrays.fill(seen, n);
        seen[0] = -1;
        int res = 0, curr = 0;
        for (int i = 0; i < n; ++i) {
            curr ^= 1 << s.charAt(i) - '0';
            for (int j = 0; j < 10; j++) {
                // aabaa 形回文, 遍历这个b 的可能的值,
                // curr ^ (1 << j) ^ curr 的结果等于 1 << j
                res = Math.max(res, i - seen[curr ^ (1 << j)]);
            }
            // abba 形回文, 找出和 curr 相同的 mask, 这样异或结果是0
            res = Math.max(res, i - seen[curr]);
            seen[curr] = Math.min(seen[curr], i);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("3242415").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create("12345678").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("213123").expect(6);

    @TestData
    public DataExpectation example4 = DataExpectation.create("00").expect(2);

}
