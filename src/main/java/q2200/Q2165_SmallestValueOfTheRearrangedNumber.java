package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2165. Smallest Value of the Rearranged Number</h3>
 * <a href="https://leetcode.com/problems/smallest-value-of-the-rearranged-number/">
 * https://leetcode.com/problems/smallest-value-of-the-rearranged-number/
 * </a><br/>
 *
 * <p>You are given an integer <code>num.</code> <strong>Rearrange</strong> the digits of <code>num</code> such that its
 * value is <strong>minimized</strong> and it does not contain <strong>any</strong> leading zeros.</p>
 *
 * <p>Return <em>the rearranged number with minimal value</em>.</p>
 *
 * <p>Note that the sign of the number does not change after rearranging the digits.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = 310
 * <strong>Output:</strong> 103
 * <strong>Explanation:</strong> The possible arrangements for the digits of 310 are 013, 031, 103, 130, 301, 310.
 * The arrangement with the smallest value that does not contain any leading zeros is 103.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = -7605
 * <strong>Output:</strong> -7650
 * <strong>Explanation:</strong> Some possible arrangements for the digits of -7605 are -7650, -6705, -5076, -0567.
 * The arrangement with the smallest value that does not contain any leading zeros is -7650.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>-10<sup>15</sup> &lt;= num &lt;= 10<sup>15</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2165_SmallestValueOfTheRearrangedNumber {

    @Answer
    public long smallestNumber(long num) {
        if (num / 10 == 0) {
            // 个位数和0 直接返回
            return num;
        }
        boolean negative = num < 0;
        if (negative) {
            num = -num;
        }
        int[] counts = new int[10];
        while (num != 0) {
            counts[(int) (num % 10)]++;
            num /= 10;
        }
        if (negative) {
            long res = 0;
            for (int i = 9; i >= 0; i--) {
                while (counts[i]-- > 0) {
                    res = res * 10 - i;
                }
            }
            return res;
        } else {
            long res;
            int i = 1;
            while (counts[i] == 0) {
                i++;
            }
            counts[i]--;
            res = i;
            while (counts[0]-- > 0) {
                res *= 10;
            }
            for (; i < 10; i++) {
                while (counts[i]-- > 0) {
                    res = res * 10 + i;
                }
            }
            return res;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(310).expect(103L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(-7605).expect(-7650L);

}
