package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2078. Two Furthest Houses With Different Colors</h3>
 * <a href="https://leetcode.com/problems/two-furthest-houses-with-different-colors/">
 * https://leetcode.com/problems/two-furthest-houses-with-different-colors/
 * </a><br/>
 *
 * <p>There are <code>n</code> houses evenly lined up on the street, and each house is beautifully painted. You are
 * given a <strong>0-indexed</strong> integer array <code>colors</code> of length <code>n</code>, where
 * <code>colors[i]</code> represents the color of the <code>i<sup>th</sup></code> house.</p>
 *
 * <p>Return <em>the <strong>maximum</strong> distance between <strong>two</strong> houses with
 * <strong>different</strong> colors</em>.</p>
 *
 * <p>The distance between the <code>i<sup>th</sup></code> and <code>j<sup>th</sup></code> houses is <code>abs(i -
 * j)</code>, where <code>abs(x)</code> is the <strong>absolute value</strong> of <code>x</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/10/31/eg1.png" style="width: 610px; height: 84px;" />
 * <pre>
 * <strong>Input:</strong> colors = [<u><strong>1</strong></u>,1,1,<strong><u>6</u></strong>,1,1,1]
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong> In the above image, color 1 is blue, and color 6 is red.
 * The furthest two houses with different colors are house 0 and house 3.
 * House 0 has color 1, and house 3 has color 6. The distance between them is abs(0 - 3) = 3.
 * Note that houses 3 and 6 can also produce the optimal answer.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/10/31/eg2.png" style="width: 426px; height: 84px;" />
 * <pre>
 * <strong>Input:</strong> colors = [<u><strong>1</strong></u>,8,3,8,<u><strong>3</strong></u>]
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong> In the above image, color 1 is blue, color 8 is yellow, and color 3 is green.
 * The furthest two houses with different colors are house 0 and house 4.
 * House 0 has color 1, and house 4 has color 3. The distance between them is abs(0 - 4) = 4.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> colors = [<u><strong>0</strong></u>,<strong><u>1</u></strong>]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> The furthest two houses with different colors are house 0 and house 1.
 * House 0 has color 0, and house 1 has color 1. The distance between them is abs(0 - 1) = 1.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n ==&nbsp;colors.length</code></li>
 * 	<li><code>2 &lt;= n &lt;= 100</code></li>
 * 	<li><code>0 &lt;= colors[i] &lt;= 100</code></li>
 * 	<li>Test data are generated such that <strong>at least</strong> two houses have different colors.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2078_TwoFurthestHousesWithDifferentColors {

    @Answer
    public int maxDistance(int[] colors) {
        final int n = colors.length;
        int i = 0;
        int c1 = colors[0], c1i = 0;

        int c2 = c1, c2i = 0;
        for (; i < n; i++) {
            if (colors[i] != c1) {
                c2 = colors[i];
                c2i = i;
                break;
            }
        }

        int res = c2i - c1i;
        for (; i < n; i++) {
            if (colors[i] != c1) {
                res = i - c1i;
            }
            if (colors[i] != c2) {
                res = Math.max(i - c2i, res);
            }
        }
        return res;
    }


    /**
     * leetcode 上思路比较好的做法, 从头到尾和从尾到头找一遍.
     */
    @Answer
    public int maxDistance2(int[] colors) {
        final int last = colors.length - 1;
        int res = 0;
        for (int i = 0; i < last; i++) {
            if (colors[i] != colors[last]) {
                res = Math.max(res, last - i);
            }
        }
        for (int i = last; i > 0; i--) {
            if (colors[0] != colors[i]) {
                res = Math.max(res, i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 1, 6, 1, 1, 1}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 8, 3, 8, 3}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 1}).expect(1);

}
