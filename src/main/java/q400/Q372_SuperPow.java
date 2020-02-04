package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/super-pow/
 *
 * Your task is to calculate a^b mod 1337 where a is a positive integer and b is an extremely large positive integer
 * given in the form of an array.
 *
 * Example 1:
 *
 * Input: a = 2, b = [3]
 * Output: 8
 *
 * Example 2:
 *
 * Input: a = 2, b = [1,0]
 * Output: 1024
 */
@RunWith(LeetCodeRunner.class)
public class Q372_SuperPow {

    /**
     * 这个1337 没有特殊含义, 只是一个满足 n^2 < Integer.MAX_VALUE 的数字
     * a^123 % 1337 可以拆分为 (((a^1)^10)^10 * (a^2^10) * a^3) % 1337
     * 采用二分法计算次方结果, 大于1337 的对结果没有影响
     */
    @Answer
    public int superPow(int a, int[] b) {
        int res = 1;
        for (int i : b) {
            res = pow(res, 10) * pow(a, i) % 1337;
        }
        return res;
    }

    private int pow(int a, int b) {
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return a % 1337;
        }
        return pow(a % 1337, b / 2) * pow(a % 1337, (b + 1) / 2) % 1337;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, new int[]{3}).expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, new int[]{1, 0}).expect(1024);

}
