package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/smallest-good-base/
 *
 * For an integer n, we call k>=2 a good base of n, if all digits of n base k are 1.
 *
 * Now given a string representing n, you should return the smallest good base of n in string format.
 *
 * Example 1:
 *
 * Input: "13"
 * Output: "3"
 * Explanation: 13 base 3 is 111.
 *
 *
 *
 * Example 2:
 *
 * Input: "4681"
 * Output: "8"
 * Explanation: 4681 base 8 is 11111.
 *
 *
 *
 * Example 3:
 *
 * Input: "1000000000000000000"
 * Output: "999999999999999999"
 * Explanation: 1000000000000000000 base 999999999999999999 is 11.
 *
 *
 *
 * Note:
 *
 * The range of n is [3, 10^18].
 * The string representing n is always valid and will not have leading zeros.
 *
 * 题解: 这里的基数值的是进位计数法(二进制/ 八进制/ 十进制)的这个基数的意思,
 * 题目要求以此为基数表示的数字n 的各位上全是1 (输入n 本身是10 进的)
 */
@RunWith(LeetCodeRunner.class)
public class Q483_SmallestGoodBase {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/6620351.html
     * 就是要求出让数字 n = x^d + x^(d-1) + x^(d-2) + ... + x^0 其中x 的值.
     * 按照等比数列的求和公式得出 n = (x^d - 1) / (x - 1)
     */
    @Answer
    public String smallestGoodBase(String n) {
        // n 取值范围在 [3, 10^18], 可以被转化为Long 类型.
        long num = Long.parseLong(n);
        // 判断以i 为基数是否满足条件.
        for (int i = (int) (Math.log(num + 1) / Math.log(2)); i >= 2; i--) {
            // 二分法查找合适的d, 让 (x^d - 1) / (x - 1) = n 等式成立
            long left = 2, right = (long) Math.pow(num, 1.0 / (i - 1)) + 1;
            while (left < right) {
                long mid = left + (right - left) / 2, sum = 0;
                for (int j = 0; j < i; j++) {
                    sum = sum * mid + 1;
                }
                if (sum == num) {
                    return String.valueOf(mid);
                }
                if (sum < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }
        return String.valueOf(num - 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("13").expect("3");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("4681").expect("8");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("1000000000000000000").expect("999999999999999999");

}
