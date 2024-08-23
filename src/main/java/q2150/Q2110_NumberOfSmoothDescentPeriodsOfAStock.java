package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2110. Number of Smooth Descent Periods of a Stock</h3>
 * <a href="https://leetcode.com/problems/number-of-smooth-descent-periods-of-a-stock/">
 * https://leetcode.com/problems/number-of-smooth-descent-periods-of-a-stock/
 * </a><br/>
 *
 * <p>You are given an integer array <code>prices</code> representing the daily price history of a stock, where
 * <code>prices[i]</code> is the stock price on the <code>i<sup>th</sup></code> day.</p>
 *
 * <p>A <strong>smooth descent period</strong> of a stock consists of <strong>one or more contiguous</strong> days such
 * that the price on each day is <strong>lower</strong> than the price on the <strong>preceding day</strong> by
 * <strong>exactly</strong> <code>1</code>. The first day of the period is exempted from this rule.</p>
 *
 * <p>Return <em>the number of <strong>smooth descent periods</strong></em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> prices = [3,2,1,4]
 * <strong>Output:</strong> 7
 * <strong>Explanation:</strong> There are 7 smooth descent periods:
 * [3], [2], [1], [4], [3,2], [2,1], and [3,2,1]
 * Note that a period with one day is a smooth descent period by the definition.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> prices = [8,6,7,7]
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong> There are 4 smooth descent periods: [8], [6], [7], and [7]
 * Note that [8,6] is not a smooth descent period as 8 - 6 &ne; 1.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> prices = [1]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> There is 1 smooth descent period: [1]
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= prices.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= prices[i] &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2110_NumberOfSmoothDescentPeriodsOfAStock {

    @Answer
    public long getDescentPeriods(int[] prices) {
        long res = 1;
        int continuous = 1;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] - 1 == prices[i]) {
                continuous++;
            } else {
                continuous = 1;
            }
            res += continuous;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 2, 1, 4}).expect(7L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{8, 6, 7, 7}).expect(4L);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1}).expect(1L);

}
