package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/unique-binary-search-trees/
 *
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 *
 * Example:
 *
 * Input: 3
 * Output: 5
 * Explanation:
 * Given n = 3, there are a total of 5 unique BST's:
 *
 * >   1         3     3      2      1
 * >    \       /     /      / \      \
 * >     3     2     1      1   3      2
 * >    /     /       \                 \
 * >   2     1         2                 3
 *
 * 题解: 找出二叉搜索树的数量. 相关问题 {@link Q095_UniqueBinarySearchTreesII}
 */
@RunWith(LeetCodeRunner.class)
public class Q096_UniqueBinarySearchTrees {

    /**
     * Q095 的dfs 解法: 这种解法会发生超时问题
     */
    public int numTrees(int n) {
        if (n == 0) {
            return 1;
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += numTrees(i - 1) * numTrees(n - i);
        }
        return res;
    }

    // dp 的解法. 思路与上面一样, 但是缓存了结果.
    @Answer
    public int dp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(3).expect(5);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(19).expect(1767263190);

}
