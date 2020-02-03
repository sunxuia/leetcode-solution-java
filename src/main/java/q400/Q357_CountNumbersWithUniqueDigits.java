package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/count-numbers-with-unique-digits/
 *
 * Given a non-negative integer n, count all numbers with unique digits, x, where 0 ≤ x < 10^n.
 *
 * Example:
 *
 * Input: 2
 * Output: 91
 * Explanation: The answer should be the total numbers in the range of 0 ≤ x < 100,
 * excluding 11,22,33,44,55,66,77,88,99
 */
@RunWith(LeetCodeRunner.class)
public class Q357_CountNumbersWithUniqueDigits {

    @Answer
    public int countNumbersWithUniqueDigits(int n) {
        int res = 1, total = 9, product = 9;
        for (int i = 0; i < n; i++, product--) {
            res += total;
            total *= product;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(2).expect(91);

    @TestData
    public DataExpectation nIs0 = DataExpectation.create(0).expect(1);

    @TestData
    public DataExpectation nIs1 = DataExpectation.create(1).expect(10);

    @TestData
    public DataExpectation nIs3 = DataExpectation.create(3).expect(739);

}
