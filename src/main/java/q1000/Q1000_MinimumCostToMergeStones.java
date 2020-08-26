package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1000. Minimum Cost to Merge Stones
 * https://leetcode.com/problems/minimum-cost-to-merge-stones/
 *
 * There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.
 *
 * A move consists of merging exactly K consecutive piles into one pile, and the cost of this move is equal to the total
 * number of stones in these K piles.
 *
 * Find the minimum cost to merge all piles of stones into one pile.  If it is impossible, return -1.
 *
 * Example 1:
 *
 * Input: stones = [3,2,4,1], K = 2
 * Output: 20
 * Explanation:
 * We start with [3, 2, 4, 1].
 * We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
 * We merge [4, 1] for a cost of 5, and we are left with [5, 5].
 * We merge [5, 5] for a cost of 10, and we are left with [10].
 * The total cost was 20, and this is the minimum possible.
 *
 * Example 2:
 *
 * Input: stones = [3,2,4,1], K = 3
 * Output: -1
 * Explanation: After any merge operation, there are 2 piles left, and we can't merge anymore.  So the task is
 * impossible.
 *
 * Example 3:
 *
 * Input: stones = [3,5,1,2,6], K = 3
 * Output: 25
 * Explanation:
 * We start with [3, 5, 1, 2, 6].
 * We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
 * We merge [3, 8, 6] for a cost of 17, and we are left with [17].
 * The total cost was 25, and this is the minimum possible.
 *
 * Note:
 *
 * 1 <= stones.length <= 30
 * 2 <= K <= 30
 * 1 <= stones[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1000_MinimumCostToMergeStones {

    /**
     * dfs 的方式, 这种解法会超时
     */
//    @Answer
    public int mergeStones_dfs(int[] stones, int K) {
        final int n = stones.length;
        if (n < K || (n - K) % (K - 1) != 0) {
            return n == 1 ? 0 : -1;
        }
        this.K = K;
        this.sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }
        return dfs(0, n - 1, 1);
    }

    // [from, to] 区间内合并为steps 个元素的最小搬动结果
    private int dfs(int from, int to, int steps) {
        final int range = to - from + 1;
        int res = MAX_VALUE;
        if (steps == range) {
            // 因为 steps <= k, 所以如果相等则需要额外搬动的石头数为0
            res = 0;
        } else if (steps == 1) {
            // 如要将[from, to] 内的所有石头合到一起,
            // 则需要加上把子区间的石头合并起来的数量.
            if (range >= K) {
                res = sums[to + 1] - sums[from] + dfs(from, to, K);
                res = Math.min(MAX_VALUE, res);
            }
            // 如果range < k 则不可能搬动
        } else {
            // 这个是中间状态, 将 [from, to] 的石头合并为 steps 个石头
            // 因为是中间状态, 所以不需要搬动当前石头,
            // 这里每次确定1 个区间的大小, 递归调用获取最小的结果.
            for (int i = from; i < to; i++) {
                int curr = dfs(from, i, 1);
                int rest = dfs(i + 1, to, steps - 1);
                res = Math.min(res, curr + rest);
            }
        }
        return res;
    }

    private int K;

    private int[] sums;

    private static final int MAX_VALUE = Integer.MAX_VALUE / 2;

    /**
     * 对上面带缓存的优化
     */
    @Answer
    public int mergeStones(int[] stones, int K) {
        final int n = stones.length;
        if (n < K || (n - K) % (K - 1) != 0) {
            return n == 1 ? 0 : -1;
        }
        this.K = K;
        this.sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }
        int[][][] cache = new int[n][n][K + 1];
        return dfs(cache, 0, n - 1, 1);
    }

    private int dfs(int[][][] cache, int from, int to, int steps) {
        final int range = to - from + 1;
        if (steps == range) {
            return 0;
        }
        if (cache[from][to][steps] > 0) {
            return cache[from][to][steps];
        }

        int res = MAX_VALUE;
        if (steps == 1) {
            if (range >= K) {
                res = sums[to + 1] - sums[from] + dfs(cache, from, to, K);
                res = Math.min(MAX_VALUE, res);
            }
        } else {
            for (int i = from; i < to; i++) {
                int curr = dfs(cache, from, i, 1);
                int rest = dfs(cache, i + 1, to, steps - 1);
                res = Math.min(res, curr + rest);
            }
        }
        cache[from][to][steps] = res;
        return res;
    }

    /**
     * 根据上面的思路的改写为dp 的解法
     */
    @Answer
    public int mergeStones_dp(int[] stones, int K) {
        final int n = stones.length;
        if (n < K || (n - K) % (K - 1) != 0) {
            return n == 1 ? 0 : -1;
        }
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }
        // dp[from][to][k] 表示将[from, to] 的石头合并为k 组需要搬动的石头数量.
        int[][][] dp = new int[n][n][K + 1];
        for (int to = 0; to < n; to++) {
            for (int from = to; from >= 0; from--) {
                int range = to - from + 1;
                for (int steps = K; steps > 1; steps--) {
                    if (range == steps) {
                        continue;
                    }
                    int res = MAX_VALUE;
                    for (int i = from; i < to; i++) {
                        int curr = dp[from][i][1];
                        int rest = dp[i + 1][to][steps - 1];
                        res = Math.min(res, curr + rest);
                    }
                    dp[from][to][steps] = res;
                }
                if (range > 1) {
                    if (range >= K) {
                        dp[from][to][1] = Math.min(MAX_VALUE,
                                sums[to + 1] - sums[from] + dp[from][to][K]);
                    } else {
                        dp[from][to][1] = MAX_VALUE;
                    }
                }
            }
        }
        return dp[0][n - 1][1];
    }

    /**
     * LeetCode 中比较快的解法.
     */
    @Answer
    public int mergeStones_leetCode(int[] stones, int K) {
        final int n = stones.length;
        if (n < K || (n - K) % (K - 1) != 0) {
            return n == 1 ? 0 : -1;
        }

        this.K = K;
        this.sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }

        int[][] dp = new int[n][n];
        return dfs(dp, 0, n - 1);
    }

    private int dfs(int[][] dp, int from, int to) {
        if (dp[from][to] > 0) {
            return dp[from][to];
        }
        if (to - from + 1 < K) {
            return 0;
        }

        int res = MAX_VALUE;
        // 主要变更点在这, 避免了上面不同steps 的无效计算
        // i += K - 1, 确保左右2 边肯定可以合并
        for (int i = from; i < to; i += K - 1) {
            int left = dfs(dp, from, i);
            int right = dfs(dp, i + 1, to);
            res = Math.min(res, left + right);
        }
        // 这个区间可以合并为1 个时
        if ((to - from) % (K - 1) == 0) {
            res += sums[to + 1] - sums[from];
            res = Math.min(MAX_VALUE, res);
        }
        dp[from][to] = res;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 2, 4, 1}, 2).expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 2, 4, 1}, 3).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{3, 5, 1, 2, 6}, 3).expect(25);

    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[]{1}, 2).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{4, 6, 4, 7, 5}, 2).expect(62);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(new int[]{59, 7, 10, 6, 3, 7, 59, 20, 40, 86, 18, 43, 78, 83, 95, 27, 25, 80, 96, 29, 21}, 3)
            .expect(2362);

}
