package q1150;

import java.util.function.IntFunction;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1131. Maximum of Absolute Value Expression
 * https://leetcode.com/problems/maximum-of-absolute-value-expression/
 *
 * Given two arrays of integers with equal lengths, return the maximum value of:
 *
 * |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| + |i - j|
 *
 * where the maximum is taken over all 0 <= i, j < arr1.length.
 *
 * Example 1:
 *
 * Input: arr1 = [1,2,3,4], arr2 = [-1,4,5,6]
 * Output: 13
 *
 * Example 2:
 *
 * Input: arr1 = [1,-2,-5,0,10], arr2 = [0,-2,-1,-7,-4]
 * Output: 20
 *
 * Constraints:
 *
 * 2 <= arr1.length == arr2.length <= 40000
 * -10^6 <= arr1[i], arr2[i] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1131_MaximumOfAbsoluteValueExpression {

    /**
     * 根据hint 中的提示, abs(A) + abs(B) = max(A+B, A-B, -A+B, -A-B)
     * 那么 |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| 就变成了
     * |(a1[i] + a2[i]) - (a1[j] + a2[j])| 或 |(a1[i] - a2[i]) - (a1[j] - a2[j])|  中的最大值,
     * 令 m[i] = a1[i] + a2[i] 则同理可以将上面的计算式 | m[i] - m[j] | + |i - j| 转换为
     * | (m[i] + i) - (m[j] + j) | 或 | (m[i] - i) - (m[j] - j)| 中的最大值.
     */
    @Answer
    public int maxAbsValExpr(int[] arr1, int[] arr2) {
        final int n = arr1.length;
        int res = calculate(n, i -> arr1[i] + arr2[i] + i);
        res = Math.max(res, calculate(n, i -> arr1[i] + arr2[i] - i));
        res = Math.max(res, calculate(n, i -> arr1[i] - arr2[i] + i));
        res = Math.max(res, calculate(n, i -> arr1[i] - arr2[i] - i));
        return res;
    }

    private int calculate(final int n, IntFunction<Integer> converter) {
        int zero = converter.apply(0);
        int res = 0, min = zero, max = zero;
        for (int i = 1; i < n; i++) {
            int value = converter.apply(i);
            res = Math.max(res, Math.abs(value - min));
            res = Math.max(res, Math.abs(value - max));
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法. 优化了上面的解法.
     */
    @Answer
    public int maxAbsValExpr2(int[] arr1, int[] arr2) {
        final int n = arr1.length;
        int[] sign = {-1, 1};
        int res = 0;
        for (int p : sign) {
            for (int q : sign) {
                int min = p * arr1[0] + q * arr2[0];
                for (int i = 1; i < n; i++) {
                    int value = p * arr1[i] + q * arr2[i] + i;
                    res = Math.max(res, value - min);
                    min = Math.min(min, value);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, new int[]{-1, 4, 5, 6})
            .expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, -2, -5, 0, 10}, new int[]{0, -2, -1, -7, -4})
            .expect(20);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, -2}, new int[]{8, 8})
            .expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{10, 5, 2, -1, 5, 1}, new int[]{-5, -4, 2, 9, -8, -5})
            .expect(28);

}
