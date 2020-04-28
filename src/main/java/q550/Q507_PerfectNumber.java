package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/perfect-number/
 *
 * We define the Perfect Number is a positive integer that is equal to the sum of all its positive divisors except
 * itself.
 * Now, given an integer n, write a function that returns true when it is a perfect number and false when it is not.
 *
 * Example:
 *
 * Input: 28
 * Output: True
 * Explanation: 28 = 1 + 2 + 4 + 7 + 14
 *
 * Note: The input number n will not exceed 100,000,000. (1e8)
 */
@RunWith(LeetCodeRunner.class)
public class Q507_PerfectNumber {

    // 参考 https://www.cnblogs.com/grandyang/p/6636879.html
    // 直接算会超时, 算到sqrt(num) 可以通过.
    @Answer
    public boolean checkPerfectNumber(int num) {
        if (num < 2) {
            return false;
        }
        int sum = 1;
        int i = 2;
        while (i * i < num) {
            if (num % i == 0) {
                sum += i + num / i;
            }
            i++;
        }
        if (i * i == num) {
            sum += i;
        }
        return sum == num;
    }

    // 最快的解法, 在int 范围内满足条件的数字就这5 个.
    @Answer
    public boolean checkPerfectNumber2(int num) {
        return num == 6 || num == 28 || num == 496 || num == 8128 || num == 33550336;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(28).expect(true);

    @TestData
    public DataExpectation border0 = DataExpectation.create(0).expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation.create(1).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation.create(99999998).expect(false);

}
