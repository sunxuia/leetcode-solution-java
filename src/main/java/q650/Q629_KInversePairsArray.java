package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/k-inverse-pairs-array/
 *
 * Given two integers n and k, find how many different arrays consist of numbers from 1 to n such that there are
 * exactly k inverse pairs.
 *
 * We define an inverse pair as following: For ith and jth element in the array, if i < j and a[i] > a[j] then it's
 * an inverse pair; Otherwise, it's not.
 *
 * Since the answer may be very large, the answer should be modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 3, k = 0
 * Output: 1
 * Explanation:
 * Only the array [1,2,3] which consists of numbers from 1 to 3 has exactly 0 inverse pair.
 *
 *
 *
 * Example 2:
 *
 * Input: n = 3, k = 1
 * Output: 2
 * Explanation:
 * The array [1,3,2] and [2,1,3] have exactly 1 inverse pair.
 *
 *
 *
 * Note:
 *
 * The integer n is in the range [1, 1000] and k is in the range [0, 1000].
 */
@RunWith(LeetCodeRunner.class)
public class Q629_KInversePairsArray {

    // 这题没什么思路. 根据 Solution 中的做法, 有如下的方案:
    // 1. 带缓存的递归. 关于具体的数字规律可以参见 Solution.
    // (这个在LeetCode 上会超时)
//    @Answer
    public int kInversePairs(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (k == 0) {
            return 1;
        }
        if (memo[n][k] != null) {
            return memo[n][k];
        }
        int inv = 0;
        for (int i = 0; i <= Math.min(k, n - 1); i++) {
            inv = (inv + kInversePairs(n - 1, k - i)) % 10_0000_0007;
        }
        memo[n][k] = inv;
        return inv;
    }

    // memo[n][k] 表示在[1, n] 中有k 个反序数
    private Integer[][] memo = new Integer[1001][1001];

    // 2. dp. 根据上面dfs 的解法, 得出dp 的解法. (这个也是比较慢的)
    @Answer
    public int kInversePairs2(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                if (j == 0) {
                    dp[i][j] = 1;
                } else {
                    for (int p = 0; p <= Math.min(j, i - 1); p++) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - p]) % 10_0000_0007;
                    }
                }
            }
        }
        return dp[n][k];
    }

    // LeetCode 中最快的解法.
    @Answer
    public int kInversePairs3(int n, int k) {
        int[] store = new int[k + 1], next = new int[k + 1], temp;
        store[0] = 1;
        for (int i = 1; i < n; ++i) {
            dewIt(store, next, k + 1, i + 1);
            temp = next;
            next = store;
            store = temp;
        }
        return store[k];
    }

    private void dewIt(int[] source, int[] dest, int n, int k) {
        long acc = 0;
        for (int i = 0; i < n; i++) {
            acc += source[i];
            acc %= 10_0000_0007;
            int j = i - k;
            if (j > -1) {
                acc -= source[j];
                if (acc < 0) {
                    acc += 10_0000_0007;
                }
            }
            dest[i] = (int) acc;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 0).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, 1).expect(2);

}
