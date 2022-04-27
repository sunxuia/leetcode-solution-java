package q1700;

import org.junit.runner.RunWith;
import q1900.Q1872_StoneGameVIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1690. Stone Game VII
 * https://leetcode.com/problems/stone-game-vii/
 *
 * Alice and Bob take turns playing a game, with Alice starting first.
 *
 * There are n stones arranged in a row. On each player's turn, they can remove either the leftmost stone or the
 * rightmost stone from the row and receive points equal to the sum of the remaining stones' values in the row. The
 * winner is the one with the higher score when there are no stones left to remove.
 *
 * Bob found that he will always lose this game (poor Bob, he always loses), so he decided to minimize the score's
 * difference. Alice's goal is to maximize the difference in the score.
 *
 * Given an array of integers stones where stones[i] represents the value of the ith stone from the left, return the
 * difference in Alice and Bob's score if they both play optimally.
 *
 * Example 1:
 *
 * Input: stones = [5,3,1,4,2]
 * Output: 6
 * Explanation:
 * - Alice removes 2 and gets 5 + 3 + 1 + 4 = 13 points. Alice = 13, Bob = 0, stones = [5,3,1,4].
 * - Bob removes 5 and gets 3 + 1 + 4 = 8 points. Alice = 13, Bob = 8, stones = [3,1,4].
 * - Alice removes 3 and gets 1 + 4 = 5 points. Alice = 18, Bob = 8, stones = [1,4].
 * - Bob removes 1 and gets 4 points. Alice = 18, Bob = 12, stones = [4].
 * - Alice removes 4 and gets 0 points. Alice = 18, Bob = 12, stones = [].
 * The score difference is 18 - 12 = 6.
 *
 * Example 2:
 *
 * Input: stones = [7,90,5,1,100,10,10,2]
 * Output: 122
 *
 * Constraints:
 *
 * n == stones.length
 * 2 <= n <= 1000
 * 1 <= stones[i] <= 1000
 *
 * 上一题 {@link Q1686_StoneGameVI}
 * 下一题 {@link Q1872_StoneGameVIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1690_StoneGameVII {

    @Answer
    public int stoneGameVII(int[] stones) {
        final int n = stones.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }
        int[] pair = dfs(new int[n][n][], sums, 0, n - 1);
        return Math.abs(pair[0] - pair[1]);
    }

    private int[] dfs(int[][][] cache, int[] sums, int left, int right) {
        if (left == right) {
            return new int[2];
        }
        if (cache[left][right] != null) {
            return cache[left][right];
        }

        int leftValue = sums[right + 1] - sums[left + 1];
        int[] otherLeft = dfs(cache, sums, left + 1, right);
        int diffLeft = leftValue + otherLeft[1] - otherLeft[0];

        int rightValue = sums[right] - sums[left];
        int[] otherRight = dfs(cache, sums, left, right - 1);
        int diffRight = rightValue + otherRight[1] - otherRight[0];

        int[] res;
        // 差值要尽可能大
        if (diffLeft > diffRight) {
            res = new int[]{leftValue + otherLeft[1], otherLeft[0]};
        } else {
            res = new int[]{rightValue + otherRight[1], otherRight[0]};
        }
        cache[left][right] = res;
        return res;
    }

    /**
     * dp 的解法, 是上面解法的改写
     */
    @Answer
    public int stoneGameVII2(int[] stones) {
        final int n = stones.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stones[i];
        }

        int[][] dp = new int[n][n];
        for (int step = 1; step < n; step++) {
            for (int i = 0, j = i + step; j < n; i++, j++) {
                int left = sums[j + 1] - sums[i + 1] - dp[i + 1][j];
                int right = sums[j] - sums[i] - dp[i][j - 1];
                dp[i][j] = Math.max(left, right);
            }
        }
        return dp[0][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 3, 1, 4, 2}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 90, 5, 1, 100, 10, 10, 2}).expect(122);

}
