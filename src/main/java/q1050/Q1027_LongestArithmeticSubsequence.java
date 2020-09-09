package q1050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1027. Longest Arithmetic Subsequence
 * https://leetcode.com/problems/longest-arithmetic-subsequence/
 *
 * Given an array A of integers, return the length of the longest arithmetic subsequence in A.
 *
 * Recall that a subsequence of A is a list A[i_1], A[i_2], ..., A[i_k] with 0 <= i_1 < i_2 < ... < i_k <= A.length - 1,
 * and that a sequence B is arithmetic if B[i+1] - B[i] are all the same value (for 0 <= i < B.length - 1).
 *
 * Example 1:
 *
 * Input: A = [3,6,9,12]
 * Output: 4
 * Explanation:
 * The whole array is an arithmetic sequence with steps of length = 3.
 *
 * Example 2:
 *
 * Input: A = [9,4,7,2,10]
 * Output: 3
 * Explanation:
 * The longest arithmetic subsequence is [4,7,10].
 *
 * Example 3:
 *
 * Input: A = [20,1,15,3,10,5,8]
 * Output: 4
 * Explanation:
 * The longest arithmetic subsequence is [20,15,10,5].
 *
 * Constraints:
 *
 * 2 <= A.length <= 1000
 * 0 <= A[i] <= 500
 *
 * 题解: 求A 中的最长子数组(保持相对顺序) 的长度, 子数组中的元素是等差队列.
 */
@RunWith(LeetCodeRunner.class)
public class Q1027_LongestArithmeticSubsequence {

    /**
     * 暴力解法, 可以通过OJ, 很慢就是了
     */
    @Answer
    public int longestArithSeqLength(int[] A) {
        final int n = A.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int prev = j, count = 2;
                for (int k = j + 1; k < n; k++) {
                    if (A[k] - A[prev] == A[j] - A[i]) {
                        count++;
                        prev = k;
                    }
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }

    /**
     * dp 的解法
     * 参考文档 https://yuuuuuy.top/2019/07/02/LeetCode-1027-Longest-Arithmetic-Sequence/
     */
    @Answer
    public int longestArithSeqLength2(int[] A) {
        int n = A.length;
        // dp[i][diff] 表示以元素 A[i-500] 结尾且差为diff 的序列长度
        int[][] dp = new int[n][1001];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], 1);
        }
        int res = 2;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int diff = A[i] - A[j] + 500;
                // 以 A[j] 结尾且差值为diff 的序列, 可以以 A[i] 来结尾, 长度+1
                dp[i][diff] = Math.max(dp[i][diff], dp[j][diff] + 1);
                res = Math.max(res, dp[i][diff]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 6, 9, 12}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{9, 4, 7, 2, 10}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{20, 1, 15, 3, 10, 5, 8}).expect(4);

}
