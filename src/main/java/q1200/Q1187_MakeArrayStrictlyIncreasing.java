package q1200;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1187. Make Array Strictly Increasing
 * https://leetcode.com/problems/make-array-strictly-increasing/
 *
 * Given two integer arrays arr1 and arr2, return the minimum number of operations (possibly zero) needed to make arr1
 * strictly increasing.
 *
 * In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do the assignment
 * arr1[i] = arr2[j].
 *
 * If there is no way to make arr1 strictly increasing, return -1.
 *
 * Example 1:
 *
 * Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
 * Output: 1
 * Explanation: Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7].
 *
 * Example 2:
 *
 * Input: arr1 = [1,5,3,6,7], arr2 = [4,3,1]
 * Output: 2
 * Explanation: Replace 5 with 3 and then replace 3 with 4. arr1 = [1, 3, 4, 6, 7].
 *
 * Example 3:
 *
 * Input: arr1 = [1,5,3,6,7], arr2 = [1,6,3,3]
 * Output: -1
 * Explanation: You can't make arr1 strictly increasing.
 *
 * Constraints:
 *
 * 1 <= arr1.length, arr2.length <= 2000
 * 0 <= arr1[i], arr2[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1187_MakeArrayStrictlyIncreasing {

    /**
     * 参考 https://www.acwing.com/file_system/file/content/whole/index/content/5467/
     * 中给出的思路, 使用如下的dp 来做.
     */
    @Answer
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        final int n = arr1.length;
        // dp[i][times] 表示经过 times 次赋值, 让 arr[0:i] 递增的 arr[i] 处的最小值.
        int[][] dp = new int[n][n + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        Arrays.sort(arr2);
        dp[0][0] = arr1[0];
        if (arr2[0] < arr1[0]) {
            dp[0][1] = arr2[0];
        }
        for (int i = 0; i < n - 1; i++) {
            for (int times = 0; times <= i + 1; times++) {
                if (dp[i][times] == Integer.MAX_VALUE) {
                    continue;
                }
                // 不用替换
                if (arr1[i + 1] > dp[i][times]) {
                    dp[i + 1][times] = Math.min(dp[i + 1][times], arr1[i + 1]);
                }
                // 替换+1 次
                int idx = Arrays.binarySearch(arr2, dp[i][times] + 1);
                idx = idx < 0 ? ~idx : idx;
                if (idx < arr2.length && arr2[idx] < dp[i + 1][times]) {
                    dp[i + 1][times + 1] = Math.min(dp[i + 1][times + 1], arr2[idx]);
                }
            }
        }

        // 找出最小替换次数的结果
        for (int i = 0; i <= n; i++) {
            if (dp[n - 1][i] < Integer.MAX_VALUE) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 5, 3, 6, 7}, new int[]{1, 3, 2, 4})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 5, 3, 6, 7}, new int[]{4, 3, 1})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 5, 3, 6, 7}, new int[]{1, 6, 3, 3})
            .expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new int[]{31, 18, 1, 12, 23, 14, 25, 4, 17, 18, 29, 28, 35, 34, 19, 8, 25, 6, 35},
            new int[]{13, 10, 25, 18, 3, 8, 37, 20, 23, 12, 9, 36, 17, 22, 29, 6, 1, 12, 37, 6, 3, 14, 37, 2}
    ).expect(19);

}
