package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-repeating-character-replacement/
 *
 * Given a string s that consists of only uppercase English letters, you can perform at most k operations on that
 * string.
 *
 * In one operation, you can choose any character of the string and change it to any other uppercase English character.
 *
 * Find the length of the longest sub-string containing all repeating letters you can get after performing the above
 * operations.
 *
 * Note:
 * Both the string's length and k will not exceed 10^4.
 *
 * Example 1:
 *
 * Input:
 * s = "ABAB", k = 2
 *
 * Output:
 * 4
 *
 * Explanation:
 * Replace the two 'A's with two 'B's or vice versa.
 *
 *
 *
 * Example 2:
 *
 * Input:
 * s = "AABABBA", k = 1
 *
 * Output:
 * 4
 *
 * Explanation:
 * Replace the one 'A' in the middle with 'B' and form "AABBBBA".
 * The substring "BBBB" has the longest repeating letters, which is 4.
 */
@RunWith(LeetCodeRunner.class)
public class Q424_LongestRepeatingCharacterReplacement {

    // 暴力的方式会超时, 如下的循环的方式比较慢.
    @Answer
    public int characterReplacement(String s, int k) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int remains = k;
            for (int j = i; j < s.length() && remains >= 0; j++) {
                if (s.charAt(i) != s.charAt(j)) {
                    remains--;
                }
                res = Math.max(res, j - i + 1 + Math.min(i, remains));
            }
        }
        return res;
    }

    // LeetCode 上比较快的做法.
    @Answer
    public int characterReplacement2(String s, int k) {
        int res = 0;
        int left = 0, maxCount = 0;
        int[] counts = new int[26];

        for (int right = 0; right < s.length(); right++) {
            int rightVal = s.charAt(right) - 'A';
            counts[rightVal]++;
            maxCount = Math.max(maxCount, counts[rightVal]);

            // 求出left, 可以将 [left, right] 区域内的所有字母全部转换为 maxCount 的对应字母
            while (right - left + 1 - maxCount > k) {
                counts[s.charAt(left) - 'A']--;
                left++;
            }
            res = Math.max(res, right - left + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ABAB", 2).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("AABABBA", 1).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("ABBB", 1).expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("BAAAB", 2).expect(5);

    @TestData
    public DataExpectation border = DataExpectation.createWith("", 0).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith("EOEMQLLQTRQDDCOERARHGAAARRBKCC"
            + "MFTDAQOLOKARBIJBISTGNKBQGKKTALSQNFSABASNOPBMMGDIOETPTDICRBOMBAAHINTFLH", 7).expect(11);

}
