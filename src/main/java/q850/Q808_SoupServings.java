package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/soup-servings/
 *
 * There are two types of soup: type A and type B. Initially we have N ml of each type of soup. There are four kinds
 * of operations:
 *
 * 1. Serve 100 ml of soup A and 0 ml of soup B
 * 2. Serve 75 ml of soup A and 25 ml of soup B
 * 3. Serve 50 ml of soup A and 50 ml of soup B
 * 4. Serve 25 ml of soup A and 75 ml of soup B
 *
 * When we serve some soup, we give it to someone and we no longer have it.  Each turn, we will choose from the four
 * operations with equal probability 0.25. If the remaining volume of soup is not enough to complete the operation,
 * we will serve as much as we can.  We stop once we no longer have some quantity of both types of soup.
 *
 * Note that we do not have the operation where all 100 ml's of soup B are used first.
 *
 * Return the probability that soup A will be empty first, plus half the probability that A and B become empty at the
 * same time.
 *
 *
 *
 * Example:
 * Input: N = 50
 * Output: 0.625
 * Explanation:
 * If we choose the first two operations, A will become empty first. For the third operation, A and B will become
 * empty at the same time. For the fourth operation, B will become empty first. So the total probability of A
 * becoming empty first plus half the probability that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5
 * + 0) = 0.625.
 *
 * Notes:
 *
 * 0 <= N <= 10^9.
 * Answers within 10^-6 of the true value will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q808_SoupServings {

    // 这题dfs/ bfs 会超时或栈/堆溢出, 可以使用 dp,
    // Solution 中给出了一种避免dp 的内存问题的方法.
    @Answer
    public double soupServings(int N) {
        int n = N / 25 + (N % 25 > 0 ? 1 : 0);
        // 解题的关键点在这里, 当超过 500 * 25 以后, 概率就近似为1 了.
        if (n >= 500) {
            return 1.0;
        }

        // dp[i][j] 表示分发了多少汤的概率
        double[][] dp = new double[n + 4][n + 4];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] type : TYPES) {
                    dp[i + type[0]][j + type[1]] += 0.25 * dp[i][j];
                }
            }
        }

        double res = 0;
        for (int i = n; i < n + 4; i++) {
            for (int j = 0; j < n; j++) {
                res += dp[i][j];
            }
        }
        for (int i = n; i < n + 4; i++) {
            for (int j = n; j < n + 4; j++) {
                res += 0.5 * dp[i][j];
            }
        }
        return res;
    }

    private static final int[][] TYPES = new int[][]{{4, 0}, {3, 1}, {2, 2}, {1, 3}};

    @TestData
    public DataExpectation example = DataExpectation.create(50).expect(0.625);

    @TestData
    public DataExpectation border = DataExpectation.create(0).expect(0.5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(100).expect(0.71875);

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(800)
            .expectDouble(0.961618, 6)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(660295675)
            .expectDouble(1.0, 6)
            .build();

    @TestData
    public DataExpectation overTime = DataExpectation.create(1_0000_0000).expect(1.0);

}
