package q1050;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1012. Numbers With Repeated Digits
 * https://leetcode.com/problems/numbers-with-repeated-digits/
 *
 * Given a positive integer N, return the number of positive integers less than or equal to N that have at least 1
 * repeated digit.
 *
 * Example 1:
 *
 * Input: 20
 * Output: 1
 * Explanation: The only positive number (<= 20) with at least 1 repeated digit is 11.
 *
 * Example 2:
 *
 * Input: 100
 * Output: 10
 * Explanation: The positive numbers (<= 100) with atleast 1 repeated digit are 11, 22, 33, 44, 55, 66, 77, 88, 99, and
 * 100.
 *
 * Example 3:
 *
 * Input: 1000
 * Output: 262
 *
 * Note:
 *
 * 1 <= N <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1012_NumbersWithRepeatedDigits {

    /**
     * 就是麻烦的边界值处理.
     * 参考文档 https://blog.csdn.net/qq_41685265/article/details/104232824
     * 这题可以通过统计所有位数都不重复的数字的数量来反过来计算.
     */
    @Answer
    public int numDupDigitsAtMostN(int N) {
        List<Integer> ns = new ArrayList<>(10);
        for (int num = N; num > 0; num /= 10) {
            ns.add(num % 10);
        }
        final int len = ns.size();
        int sum = 0;

        // 计算比N 小1 位的数字中的不重复位数的数量(这样不涉及到边界条件).
        for (int digit = 0; digit < len - 1; digit++) {
            // 以当前位(digit) 为最高位, 计算不重复位的数字的数量(total), 注意最高位不能是0
            int total = 9, available = 9;
            for (int curr = digit - 1; curr >= 0; curr--) {
                total *= available--;
            }
            sum += total;
        }

        // 计算和N 同位的数字中不重复数字的数量.
        boolean[] used = new boolean[10];
        // 从高到低遍历, 这样高于digit 的位数的数字就是N 中的对应数字
        for (int digit = len - 1; digit >= 0; digit--) {
            final int num = ns.get(digit);
            int total = 0;
            // 计算当前位可用的数字数量
            for (int i = 0; i < num; i++) {
                total += used[i] ? 0 : 1;
            }
            // 最高位不能是0
            if (digit == len - 1) {
                total--;
            }

            if (total > 0) {
                // 比digit 高的 len - digit 位已经将数字用掉了
                int avaliable = 10 - (len - digit);
                for (int curr = digit - 1; curr >= 0; curr--) {
                    total *= avaliable--;
                }
                sum += total;
            }
            // N 中本身就有重复数字, 到这1 位以上固定的高位已经重复了
            if (used[num]) {
                return N - sum;
            }
            used[num] = true;
        }
        // -1 表示N 本身(N 中如果有重复数字则会在上面的N-sum 中return)
        return N - sum - 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(20).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(100).expect(10);

    @TestData
    public DataExpectation example3 = DataExpectation.create(1000).expect(262);

}
