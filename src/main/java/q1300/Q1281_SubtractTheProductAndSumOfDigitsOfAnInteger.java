package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1281. Subtract the Product and Sum of Digits of an Integer
 * https://leetcode.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
 *
 * Given an integer number n, return the difference between the product of its digits and the sum of its digits.
 *
 * Example 1:
 *
 * Input: n = 234
 * Output: 15
 * Explanation:
 * Product of digits = 2 * 3 * 4 = 24
 * Sum of digits = 2 + 3 + 4 = 9
 * Result = 24 - 9 = 15
 *
 * Example 2:
 *
 * Input: n = 4421
 * Output: 21
 * Explanation:
 * Product of digits = 4 * 4 * 2 * 1 = 32
 * Sum of digits = 4 + 4 + 2 + 1 = 11
 * Result = 32 - 11 = 21
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1281_SubtractTheProductAndSumOfDigitsOfAnInteger {

    @Answer
    public int subtractProductAndSum(int n) {
        int product = 1, sum = 0;
        for (int i = n; i > 0; i /= 10) {
            product *= i % 10;
            sum += i % 10;
        }
        return product - sum;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(234).expect(15);

    @TestData
    public DataExpectation example2 = DataExpectation.create(4421).expect(21);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(114).expect(-2);

}
