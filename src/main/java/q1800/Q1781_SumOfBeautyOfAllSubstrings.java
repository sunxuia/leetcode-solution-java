package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1781. Sum of Beauty of All Substrings
 * https://leetcode.com/problems/sum-of-beauty-of-all-substrings/
 *
 * The beauty of a string is the difference in frequencies between the most frequent and least frequent characters.
 *
 * For example, the beauty of "abaacc" is 3 - 1 = 2.
 *
 * Given a string s, return the sum of beauty of all of its substrings.
 *
 * Example 1:
 *
 * Input: s = "aabcb"
 * Output: 5
 * Explanation: The substrings with non-zero beauty are ["aab","aabc","aabcb","abcb","bcb"], each with beauty equal to
 * 1.
 *
 * Example 2:
 *
 * Input: s = "aabcbaa"
 * Output: 17
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * s consists of only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1781_SumOfBeautyOfAllSubstrings {

    /**
     * 这题没什么意思, 时间复杂度 O(N^2).
     */
    @Answer
    public int beautySum(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int[] count = new int[26];
            for (int j = i; j < n; j++) {
                count[cs[j] - 'a']++;
                // 统计最多和最小的字符频次
                int max = 0, min = Integer.MAX_VALUE;
                for (int k = 0; k < 26; k++) {
                    if (count[k] != 0) {
                        max = Math.max(count[k], max);
                        min = Math.min(count[k], min);
                    }
                }
                res += max - min;
            }
        }
        return res;
    }

    /**
     * leetcode 上比较快的解法, 优化排序为动态排序
     */
    @Answer
    public int beautySum2(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            // 表示子字符串中每个字符的数量.
            int[] counts = new int[26];
            // freq[count] 表示子字符串中出现count 次的字符的数量.
            int[] freq = new int[n + 1 - i];
            int min = 1, max = 1;
            for (int j = i; j < n; j++) {
                int count = ++counts[cs[j] - 'a'];
                freq[count - 1]--;
                freq[count]++;
                max = Math.max(max, count);
                if (freq[min] == 0) {
                    min++;
                } else {
                    min = Math.min(min, count);
                }
                res += max - min;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aabcb").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aabcbaa").expect(17);

}
