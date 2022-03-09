package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1780. Check if Number is a Sum of Powers of Three
 * https://leetcode.com/problems/check-if-number-is-a-sum-of-powers-of-three/
 *
 * Given an integer n, return true if it is possible to represent n as the sum of distinct powers of three. Otherwise,
 * return false.
 *
 * An integer y is a power of three if there exists an integer x such that y == 3x.
 *
 * Example 1:
 *
 * Input: n = 12
 * Output: true
 * Explanation: 12 = 3^1 + 3^2
 *
 * Example 2:
 *
 * Input: n = 91
 * Output: true
 * Explanation: 91 = 3^0 + 3^2 + 3^4
 *
 * Example 3:
 *
 * Input: n = 21
 * Output: false
 *
 * Constraints:
 *
 * 1 <= n <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1780_CheckIfNumberIsASumOfPowersOfThree {

    @Answer
    public boolean checkPowersOfThree(int n) {
        for (int num : NUMS) {
            if (n >= num) {
                n -= num;
            }
        }
        return n == 0;
    }

    private static final int[] NUMS = new int[]{1162261467, 387420489, 129140163, 43046721, 14348907,
            4782969, 1594323, 531441, 177147, 59049, 19683, 6561, 2187, 729, 243, 81, 27, 9, 3, 1};

    @TestData
    public DataExpectation example1 = DataExpectation.create(12).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(91).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(21).expect(false);

}
