package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1191. K-Concatenation Maximum Sum
 * https://leetcode.com/problems/k-concatenation-maximum-sum/
 *
 * Given an integer array arr and an integer k, modify the array by repeating it k times.
 *
 * For example, if arr = [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].
 *
 * Return the maximum sub-array sum in the modified array. Note that the length of the sub-array can be 0 and its sum in
 * that case is 0.
 *
 * As the answer can be very large, return the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: arr = [1,2], k = 3
 * Output: 9
 *
 * Example 2:
 *
 * Input: arr = [1,-2,1], k = 5
 * Output: 2
 *
 * Example 3:
 *
 * Input: arr = [-1,-2], k = 7
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= k <= 10^5
 * -10^4 <= arr[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1191_KConcatenationMaximumSum {

    @Answer
    public int kConcatenationMaxSum(int[] arr, int k) {
        if (k == 1) {
            return maxBetween(arr, 1);
        }
        int total = 0;
        for (int val : arr) {
            total += val;
        }
        if (total <= 0) {
            return maxBetween(arr, 2);
        }
        return maxTrim(arr, total, k);
    }

    private int maxBetween(int[] arr, int limit) {
        final int n = arr.length;
        int res = 0, sum = 0;
        int left = 0, right = 0;
        while (left < n && right < n * limit) {
            if (sum + arr[right % n] < 0) {
                sum -= arr[left++ % n];
            } else {
                sum += arr[right++ % n];
                res = Math.max(res, sum);
            }
        }
        return res;
    }

    private int maxTrim(int[] arr, long total, int k) {
        final int n = arr.length;
        final int mod = 10_0000_0007;
        int leftSum = 0, rightSum = 0, left = 0;
        for (int i = 0, sum = 0; i < n; i++) {
            sum += arr[i];
            if (sum < leftSum) {
                leftSum = sum;
                left = i;
            }
        }
        for (int i = n - 1, sum = 0; left < i; i--) {
            sum += arr[i];
            rightSum = Math.min(rightSum, sum);
        }
        return (int) ((total * k + mod - leftSum - rightSum) % mod);
    }

    /**
     * LeetCode 上比较快的解法, 与上面思路一样, 进行了优化.
     */
    @Answer
    public int kConcatenationMaxSum2(int[] arr, int k) {
        final int mod = 10_0000_0007;
        // total: arr 的总和
        // prefix: arr[0:] 子数组的最大总和
        // suffix: arr 中最大的子数组的之和 (最终结果就是arr[:n] 子数组的最大总和)
        // max: arr 中最大的子数组的之和
        int total = 0, prefix = 0, suffix = 0, max = 0;
        for (int num : arr) {
            total += num;
            prefix = Math.max(prefix, total);
            suffix = Math.max(suffix + num, 0);
            max = Math.max(max, suffix);
        }
        if (k == 1) {
            return max;
        }
        if (total <= 0) {
            return Math.max(max, prefix + suffix);
        }
        long trim = (long) total * (k - 2) + prefix + suffix;
        return (int) (Math.max(trim, max) % mod);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2}, 3).expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, -2, 1}, 5).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{-1, -2}, 7).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 2}, 1).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{-5, -2, 0, 0, 3, 9, -2, -5, 4}, 5).expect(20);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 36263).expect(664859242);

}
