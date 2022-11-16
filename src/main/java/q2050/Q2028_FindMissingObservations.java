package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2028. Find Missing Observations
 * https://leetcode.com/problems/find-missing-observations/
 *
 * You have observations of n + m 6-sided dice rolls with each face numbered from 1 to 6. n of the observations went
 * missing, and you only have the observations of m rolls. Fortunately, you have also calculated the average value of
 * the n + m rolls.
 *
 * You are given an integer array rolls of length m where rolls[i] is the value of the ith observation. You are also
 * given the two integers mean and n.
 *
 * Return an array of length n containing the missing observations such that the average value of the n + m rolls is
 * exactly mean. If there are multiple valid answers, return any of them. If no such array exists, return an empty
 * array.
 *
 * The average value of a set of k numbers is the sum of the numbers divided by k.
 *
 * Note that mean is an integer, so the sum of the n + m rolls should be divisible by n + m.
 *
 * Example 1:
 *
 * Input: rolls = [3,2,4,3], mean = 4, n = 2
 * Output: [6,6]
 * Explanation: The mean of all n + m rolls is (3 + 2 + 4 + 3 + 6 + 6) / 6 = 4.
 *
 * Example 2:
 *
 * Input: rolls = [1,5,6], mean = 3, n = 4
 * Output: [2,3,2,2]
 * Explanation: The mean of all n + m rolls is (1 + 5 + 6 + 2 + 3 + 2 + 2) / 7 = 3.
 *
 * Example 3:
 *
 * Input: rolls = [1,2,3,4], mean = 6, n = 4
 * Output: []
 * Explanation: It is impossible for the mean to be 6 no matter what the 4 missing rolls are.
 *
 * Constraints:
 *
 * m == rolls.length
 * 1 <= n, m <= 10^5
 * 1 <= rolls[i], mean <= 6
 */
@RunWith(LeetCodeRunner.class)
public class Q2028_FindMissingObservations {

    @Answer
    public int[] missingRolls(int[] rolls, int mean, int n) {
        final int m = rolls.length;
        int sum = mean * (m + n);
        for (int roll : rolls) {
            sum -= roll;
        }
        if (sum > 6 * n || sum < n) {
            return new int[0];
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = sum / (n - i);
            sum -= res[i];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{3, 2, 4, 3}, 4, 2)
            .expect(new int[]{6, 6}).unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 5, 6}, 3, 4)
            .expect(new int[]{2, 3, 2, 2}).unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, 6, 4)
            .expect(new int[0]);

}
