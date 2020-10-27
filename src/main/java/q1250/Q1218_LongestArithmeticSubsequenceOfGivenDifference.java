package q1250;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1218. Longest Arithmetic Subsequence of Given Difference
 * https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/
 *
 * Given an integer array arr and an integer difference, return the length of the longest subsequence in arr which is an
 * arithmetic sequence such that the difference between adjacent elements in the subsequence equals difference.
 *
 * Example 1:
 *
 * Input: arr = [1,2,3,4], difference = 1
 * Output: 4
 * Explanation: The longest arithmetic subsequence is [1,2,3,4].
 *
 * Example 2:
 *
 * Input: arr = [1,3,5,7], difference = 1
 * Output: 1
 * Explanation: The longest arithmetic subsequence is any single element.
 *
 * Example 3:
 *
 * Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
 * Output: 4
 * Explanation: The longest arithmetic subsequence is [7,5,3,1].
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * -10^4 <= arr[i], difference <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1218_LongestArithmeticSubsequenceOfGivenDifference {

    @Answer
    public int longestSubsequence(int[] arr, int difference) {
        final int offset = 1_0000, n = arr.length;
        int res = 0;
        int[] dp = new int[n];
        int[] pos = new int[offset * 2 + 1];
        Arrays.fill(pos, -1);
        for (int i = 0; i < n; i++) {
            int before = arr[i] - difference;
            if (-offset <= before && before <= offset
                    && pos[offset + before] != -1) {
                dp[i] = dp[pos[offset + before]] + 1;
                res = Math.max(dp[i], res);
            }
            pos[offset + arr[i]] = i;
        }
        return res + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3, 4}, 1).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 3, 5, 7}, 1).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 5, 7, 8, 5, 3, 4, 2, 1}, -2).expect(4);

}
