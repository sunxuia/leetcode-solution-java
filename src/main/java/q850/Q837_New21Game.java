package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/new-21-game/
 *
 * Alice plays the following game, loosely based on the card game "21".
 *
 * Alice starts with 0 points, and draws numbers while she has less than K points.  During each draw, she gains an
 * integer number of points randomly from the range [1, W], where W is an integer.  Each draw is independent and the
 * outcomes have equal probabilities.
 *
 * Alice stops drawing numbers when she gets K or more points.  What is the probability that she has N or less points?
 *
 * Example 1:
 *
 * Input: N = 10, K = 1, W = 10
 * Output: 1.00000
 * Explanation:  Alice gets a single card, then stops.
 *
 * Example 2:
 *
 * Input: N = 6, K = 1, W = 10
 * Output: 0.60000
 * Explanation:  Alice gets a single card, then stops.
 * In 6 out of W = 10 possibilities, she is at or below N = 6 points.
 *
 * Example 3:
 *
 * Input: N = 21, K = 17, W = 10
 * Output: 0.73278
 *
 * Note:
 *
 * 0 <= K <= N <= 10000
 * 1 <= W <= 10000
 * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
 * The judging time limit has been reduced for this question.
 */
@RunWith(LeetCodeRunner.class)
public class Q837_New21Game {

    // 看不懂题目什么意思?
    // https://www.cnblogs.com/grandyang/p/10386525.html
    @Answer
    public double new21Game(int N, int K, int W) {
        if (K == 0 || N >= K + W) {
            return 1.0;
        }
        double[] dp = new double[N + 1];
        dp[0] = 1.0;
        double sumW = 1.0, res = 0.0;
        for (int i = 1; i <= N; i++) {
            dp[i] = sumW / W;
            if (i < K) {
                sumW += dp[i];
            } else {
                res += dp[i];
            }
            if (i - W >= 0) {
                sumW -= dp[i - W];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(10)
            .addArgument(1)
            .addArgument(10)
            .expectDouble(1.0, 5)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(6, 1, 10).expect(0.6);

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(21)
            .addArgument(17)
            .addArgument(10)
            .expectDouble(0.73278, 5)
            .build();

}
