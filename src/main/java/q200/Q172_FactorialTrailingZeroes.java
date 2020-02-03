package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/factorial-trailing-zeroes/
 *
 * Given an integer n, return the number of trailing zeroes in n!.
 *
 * Example 1:
 *
 * Input: 3
 * Output: 0
 * Explanation: 3! = 6, no trailing zero.
 *
 * Example 2:
 *
 * Input: 5
 * Output: 1
 * Explanation: 5! = 120, one trailing zero.
 *
 * Note: Your solution should be in logarithmic time complexity.
 */
@RunWith(LeetCodeRunner.class)
public class Q172_FactorialTrailingZeroes {

    /**
     * 判断末尾的0 有多少个只需要判断有多少个5*2, 因为偶数的数量多于5 的数量, 所以只需要计算有多少5 即可,
     * 因此也比较容易实现题目要求的log(n) 的时间复杂度.
     */
    @Answer
    public int trailingZeroes(int n) {
        int res = 0;
        while (n > 0) {
            res += n /= 5;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(5).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(25).expect(6);

}
