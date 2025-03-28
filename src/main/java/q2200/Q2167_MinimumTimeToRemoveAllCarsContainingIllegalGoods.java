package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2167. Minimum Time to Remove All Cars Containing Illegal Goods</h3>
 * <a href="https://leetcode.com/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/">
 * https://leetcode.com/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> binary string <code>s</code> which represents a sequence of train
 * cars.
 * <code>s[i] = &#39;0&#39;</code> denotes that the <code>i<sup>th</sup></code> car does <strong>not</strong> contain
 * illegal goods and <code>s[i] = &#39;1&#39;</code> denotes that the <code>i<sup>th</sup></code> car does contain
 * illegal goods.</p>
 *
 * <p>As the train conductor, you would like to get rid of all the cars containing illegal goods. You can do any of the
 * following three operations <strong>any</strong> number of times:</p>
 *
 * <ol>
 * 	<li>Remove a train car from the <strong>left</strong> end (i.e., remove <code>s[0]</code>) which takes 1 unit of
 * 	time.</li>
 * 	<li>Remove a train car from the <strong>right</strong> end (i.e., remove <code>s[s.length - 1]</code>) which takes
 * 	1 unit of time.</li>
 * 	<li>Remove a train car from <strong>anywhere</strong> in the sequence which takes 2 units of time.</li>
 * </ol>
 *
 * <p>Return <em>the <strong>minimum</strong> time to remove all the cars containing illegal goods</em>.</p>
 *
 * <p>Note that an empty sequence of cars is considered to have no cars containing illegal goods.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;<strong><u>11</u></strong>00<strong><u>1</u></strong>0<strong><u>1</u></strong>&quot;
 * <strong>Output:</strong> 5
 * <strong>Explanation:</strong>
 * One way to remove all the cars containing illegal goods from the sequence is to
 * - remove a car from the left end 2 times. Time taken is 2 * 1 = 2.
 * - remove a car from the right end. Time taken is 1.
 * - remove the car containing illegal goods found in the middle. Time taken is 2.
 * This obtains a total time of 2 + 1 + 2 = 5.
 *
 * An alternative way is to
 * - remove a car from the left end 2 times. Time taken is 2 * 1 = 2.
 * - remove a car from the right end 3 times. Time taken is 3 * 1 = 3.
 * This also obtains a total time of 2 + 3 = 5.
 *
 * 5 is the minimum time taken to remove all the cars containing illegal goods.
 * There are no other ways to remove them with less time.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;00<strong><u>1</u></strong>0&quot;
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong>
 * One way to remove all the cars containing illegal goods from the sequence is to
 * - remove a car from the left end 3 times. Time taken is 3 * 1 = 3.
 * This obtains a total time of 3.
 *
 * Another way to remove all the cars containing illegal goods from the sequence is to
 * - remove the car containing illegal goods found in the middle. Time taken is 2.
 * This obtains a total time of 2.
 *
 * Another way to remove all the cars containing illegal goods from the sequence is to
 * - remove a car from the right end 2 times. Time taken is 2 * 1 = 2.
 * This obtains a total time of 2.
 *
 * 2 is the minimum time taken to remove all the cars containing illegal goods.
 * There are no other ways to remove them with less time.</pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= s.length &lt;= 2 * 10<sup>5</sup></code></li>
 * 	<li><code>s[i]</code> is either <code>&#39;0&#39;</code> or <code>&#39;1&#39;</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2167_MinimumTimeToRemoveAllCarsContainingIllegalGoods {

    /**
     * 这题目出的不好, 有个隐藏条件: 可以将0 的车辆也移除掉.
     */
    @Answer
    public int minimumTime(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        // 从左边开始遍历, 全部从左边移除的最小时间
        int minTime = 0;
        int[] minTimeFromLeft = new int[n];
        for (int i = 0; i < n; i++) {
            if (cs[i] == '1') {
                minTime = Math.min(i + 1, minTime + 2);
            }
            minTimeFromLeft[i] = minTime;
        }

        // 从右边开始遍历, 找出最小时间
        int res = minTime;
        minTime = 0;
        for (int i = n - 1; i > 0; i--) {
            if (cs[i] == '1') {
                minTime = Math.min(n - i, minTime + 2);
                int time = minTime + minTimeFromLeft[i - 1];
                res = Math.min(res, time);
            }
        }
        return res;
    }

    /**
     * leetcode 上最快的解法.
     * 算是对上面解法的优化, 从右向左遍历的步骤可以被优化掉.
     */
    @Answer
    public int leetCodeMax(String s) {
        final int n = s.length();
        int removeFromLeft = 0;
        int res = n;
        for (int i = 0; i < n; i++) {
            int removeFromMiddle = removeFromLeft + (s.charAt(i) - '0') * 2;
            removeFromLeft = Math.min(removeFromMiddle, i + 1);
            // 在这一步假设右边全部移除, 因为如果是从中间移除, 那么将在这个从左往右的循环中被处理
            res = Math.min(res, removeFromLeft + n - 1 - i);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1100101").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create("0010").expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("010110").expect(5);

    @TestData
    public DataExpectation border0 = DataExpectation.create("").expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.create("0").expect(0);

    @TestData
    public DataExpectation border2 = DataExpectation.create("1").expect(1);

}
