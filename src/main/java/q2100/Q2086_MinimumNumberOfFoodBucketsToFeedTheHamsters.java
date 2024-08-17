package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2086. Minimum Number of Food Buckets to Feed the Hamsters</h3>
 * <a href="https://leetcode.com/problems/minimum-number-of-food-buckets-to-feed-the-hamsters/">
 * https://leetcode.com/problems/minimum-number-of-food-buckets-to-feed-the-hamsters/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> string <code>hamsters</code> where <code>hamsters[i]</code> is
 * either:</p>
 *
 * <ul>
 * 	<li><code>&#39;H&#39;</code> indicating that there is a hamster at index <code>i</code>, or</li>
 * 	<li><code>&#39;.&#39;</code> indicating that index <code>i</code> is empty.</li>
 * </ul>
 *
 * <p>You will add some number of food buckets at the empty indices in order to feed the hamsters. A hamster can be
 * fed if there is at least one food bucket to its left or to its right. More formally, a hamster at index
 * <code>i</code> can be fed if you place a food bucket at index <code>i - 1</code> <strong>and/or</strong> at index
 * <code>i + 1</code>.</p>
 *
 * <p>Return <em>the minimum number of food buckets you should <strong>place at empty indices</strong> to feed all
 * the hamsters or </em><code>-1</code><em> if it is impossible to feed all of them</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/11/01/example1.png" style="width: 482px; height: 162px;" />
 * <pre>
 * <strong>Input:</strong> hamsters = &quot;H..H&quot;
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> We place two food buckets at indices 1 and 2.
 * It can be shown that if we place only one food bucket, one of the hamsters will not be fed.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/11/01/example2.png" style="width: 602px; height: 162px;" />
 * <pre>
 * <strong>Input:</strong> hamsters = &quot;.H.H.&quot;
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> We place one food bucket at index 2.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/11/01/example3.png" style="width: 602px; height: 162px;" />
 * <pre>
 * <strong>Input:</strong> hamsters = &quot;.HHH.&quot;
 * <strong>Output:</strong> -1
 * <strong>Explanation:</strong> If we place a food bucket at every empty index as shown, the hamster at index 2 will not be able to eat.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= hamsters.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>hamsters[i]</code> is either<code>&#39;H&#39;</code> or <code>&#39;.&#39;</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2086_MinimumNumberOfFoodBucketsToFeedTheHamsters {

    @Answer
    public int minimumBuckets(String hamsters) {
        final int n = hamsters.length();
        int res = 0;
        // 0 : 空位, 1 : 仓鼠, 2 : 食物
        int prev = 3;
        int curr = hamsters.charAt(0) == 'H' ? 1 : 0;
        int next;
        for (int i = 0; i < n; i++) {
            next = i < n - 1 ? hamsters.charAt(i + 1) == 'H' ? 1 : 0 : -1;
            if (curr == 1) {
                if (prev == 2) {
                    // 前面是食物, 跳过
                } else if (next == 0) {
                    // 后面有一个空位, 食物放在后面
                    next = 2;
                    res++;
                } else if (prev == 0) {
                    // 前面是空位, 食物放在前面
                    res++;
                } else {
                    return -1;
                }
            }
            prev = curr;
            curr = next;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("H..H").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(".H.H.").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(".HHH.").expect(-1);

}
