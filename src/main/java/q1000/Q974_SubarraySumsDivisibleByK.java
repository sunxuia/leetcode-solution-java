package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 974. Subarray Sums Divisible by K
 * https://leetcode.com/problems/subarray-sums-divisible-by-k/
 *
 * Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by K.
 *
 * Example 1:
 *
 * Input: A = [4,5,0,-2,-3,1], K = 5
 * Output: 7
 * Explanation: There are 7 subarrays with a sum divisible by K = 5:
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * -10000 <= A[i] <= 10000
 * 2 <= K <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q974_SubarraySumsDivisibleByK {

    /**
     * 简单的遍历(时间复杂度 O(N^2)) 会超时.
     * 没什么思路, 参考Solution 中的解答.
     * 想让sums[i] - sums[j] 能被K 整除, 只需要sum[i] 和 sum[j] 对K 的模相同.
     */
    @Answer
    public int subarraysDivByK(int[] A, int K) {
        int n = A.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            sums[i + 1] = sums[i] + A[i];
        }

        // 求总和中对K 的模的数量.
        int[] counts = new int[K];
        for (int s : sums) {
            // (用于去除负数)
            counts[(s % K + K) % K]++;
        }

        int res = 0;
        for (int v : counts) {
            res += v * (v - 1) / 2;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{4, 5, 0, -2, -3, 1}, 5)
            .expect(7);

    // 3万个数字
    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 586)
            .expect(767481);

}
