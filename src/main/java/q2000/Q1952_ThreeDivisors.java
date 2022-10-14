package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1952. Three Divisors
 * https://leetcode.com/problems/three-divisors/
 *
 * Given an integer n, return true if n has exactly three positive divisors. Otherwise, return false.
 *
 * An integer m is a divisor of n if there exists an integer k such that n = k * m.
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: false
 * Explantion: 2 has only two divisors: 1 and 2.
 *
 * Example 2:
 *
 * Input: n = 4
 * Output: true
 * Explantion: 4 has three divisors: 1, 2, and 4.
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1952_ThreeDivisors {

    @Answer
    public boolean isThree(int n) {
        int i;
        for (i = 2; i * i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return i * i == n;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(4).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(9).expect(true);

}
