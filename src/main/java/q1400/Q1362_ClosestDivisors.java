package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1362. Closest Divisors
 * https://leetcode.com/problems/closest-divisors/
 *
 * Given an integer num, find the closest two integers in absolute difference whose product equals num + 1 or num + 2.
 *
 * Return the two integers in any order.
 *
 * Example 1:
 *
 * Input: num = 8
 * Output: [3,3]
 * Explanation: For num + 1 = 9, the closest divisors are 3 & 3, for num + 2 = 10, the closest divisors are 2 & 5, hence
 * 3 & 3 is chosen.
 *
 * Example 2:
 *
 * Input: num = 123
 * Output: [5,25]
 *
 * Example 3:
 *
 * Input: num = 999
 * Output: [40,25]
 *
 * Constraints:
 *
 * 1 <= num <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1362_ClosestDivisors {

    @Answer
    public int[] closestDivisors(int num) {
        int[] res = new int[2];
        for (int i = 1; i * i <= num + 2; i++) {
            if ((num + 1) % i == 0) {
                res[0] = i;
                res[1] = (num + 1) / i;
            } else if ((num + 2) % i == 0) {
                res[0] = i;
                res[1] = (num + 2) / i;
            }
        }
        return res;
    }

    /**
     * leetcode 上比较快的解法
     */
    @Answer
    public int[] closestDivisors2(int num) {
        for (int i = (int) Math.sqrt(num + 2); i > 0; i--) {
            if ((num + 1) % i == 0) {
                return new int[]{i, (num + 1) / i};
            }
            if ((num + 2) % i == 0) {
                return new int[]{i, (num + 2) / i};
            }
        }
        return new int[]{1, num};
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(8).expect(new int[]{3, 3}).unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation.create(123).expect(new int[]{5, 25}).unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation.create(999).expect(new int[]{40, 25}).unOrder();

}
