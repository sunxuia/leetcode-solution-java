package q650;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/maximum-length-of-pair-chain/
 *
 * You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.
 *
 * Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in
 * this fashion.
 *
 * Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs.
 * You can select pairs in any order.
 *
 * Example 1:
 *
 * Input: [[1,2], [2,3], [3,4]]
 * Output: 2
 * Explanation: The longest chain is [1,2] -> [3,4]
 *
 * Note:
 *
 * 1. The number of given pairs will be in the range [1, 1000].
 */
@RunWith(LeetCodeRunner.class)
public class Q646_MaximumLengthOfPairChain {

    // 不带缓存的dfs 会超时
    @Answer
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));
        int[] cache = new int[pairs.length + 1];
        Arrays.fill(cache, -1);
        cache[pairs.length] = 0;
        return dfs(pairs, 0, cache);
    }

    private int dfs(int[][] pairs, int idx, int[] cache) {
        if (cache[idx] > -1) {
            return cache[idx];
        }
        int res = dfs(pairs, idx + 1, cache);
        int next = Arrays.binarySearch(pairs, idx + 1, pairs.length,
                pairs[idx], (pair, key) -> pair[0] - key[1] - 1);
        next = next > 0 ? next : -next - 1;
        if (next < pairs.length) {
            while (pairs[next - 1][0] == pairs[idx][1] + 1) {
                next--;
            }
        }
        res = Math.max(res, 1 + dfs(pairs, next, cache));
        cache[idx] = res;
        return res;
    }

    // DP 的解法
    @Answer
    public int findLongestChain2(int[][] pairs) {
        final int n = pairs.length;
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            int next = Arrays.binarySearch(pairs, i + 1, n,
                    pairs[i], (pair, key) -> pair[0] - key[1] - 1);
            next = next > 0 ? next : -next - 1;
            if (next < n) {
                while (pairs[next - 1][0] == pairs[i][1] + 1) {
                    next--;
                }
            }
            dp[i] = Math.max(dp[i + 1], dp[next] + 1);
        }
        return dp[0];
    }

    // LeetCode 上比较快的解法
    @Answer
    public int findLongestChain3(int[][] pairs) {
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[1]));
        int curr = Integer.MIN_VALUE;
        int res = 0;
        for (int[] arr : pairs) {
            if (curr < arr[0]) {
                curr = arr[1];
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {3, 4}})
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{-6, 9}, {1, 6}, {8, 10}, {-1, 4}, {-6, -2}, {-9, 8}, {-5, 3}, {0, 3}})
            .expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{{-2, 4}, {1, 5}, {2, 3}, {-5, -2}, {-8, 9}, {-1, 0}, {-1, 2}})
            .expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read2DArray("Q646_TestData"))
            .expect(50);

}
