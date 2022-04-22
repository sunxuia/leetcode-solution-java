package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1837. Sum of Digits in Base K
 * https://leetcode.com/problems/sum-of-digits-in-base-k/
 *
 * Given an integer n (in base 10) and a base k, return the sum of the digits of n after converting n from base 10 to
 * base k.
 *
 * After converting, each digit should be interpreted as a base 10 number, and the sum should be returned in base 10.
 *
 * Example 1:
 *
 * Input: n = 34, k = 6
 * Output: 9
 * Explanation: 34 (base 10) expressed in base 6 is 54. 5 + 4 = 9.
 *
 * Example 2:
 *
 * Input: n = 10, k = 10
 * Output: 1
 * Explanation: n is already in base 10. 1 + 0 = 1.
 *
 * Constraints:
 *
 * 1 <= n <= 100
 * 2 <= k <= 10
 */
@RunWith(LeetCodeRunner.class)
public class Q1837_SumOfDigitsInBaseK {

    @Answer
    public int sumBase(int n, int k) {
        int res = 0;
        while (n > 0) {
            res += n % k;
            n /= k;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(34, 6).expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(10, 10).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(42, 2).expect(3);

}
