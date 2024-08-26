package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2134. Minimum Swaps to Group All 1's Together II</h3>
 * <a href="https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together-ii/">
 * https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together-ii/
 * </a><br/>
 *
 * <p>A <strong>swap</strong> is defined as taking two <strong>distinct</strong> positions in an array and swapping the
 * values in them.</p>
 *
 * <p>A <strong>circular</strong> array is defined as an array where we consider the <strong>first</strong> element and
 * the <strong>last</strong> element to be <strong>adjacent</strong>.</p>
 *
 * <p>Given a <strong>binary</strong> <strong>circular</strong> array <code>nums</code>, return <em>the minimum number
 * of swaps required to group all </em><code>1</code><em>&#39;s present in the array together at <strong>any
 * location</strong></em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [0,1,0,1,1,0,0]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> Here are a few of the ways to group all the 1&#39;s together:
 * [0,<u>0</u>,<u>1</u>,1,1,0,0] using 1 swap.
 * [0,1,<u>1</u>,1,<u>0</u>,0,0] using 1 swap.
 * [1,1,0,0,0,0,1] using 2 swaps (using the circular property of the array).
 * There is no way to group all 1&#39;s together with 0 swaps.
 * Thus, the minimum number of swaps required is 1.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [0,1,1,1,0,0,1,1,0]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> Here are a few of the ways to group all the 1&#39;s together:
 * [1,1,1,0,0,0,0,1,1] using 2 swaps (using the circular property of the array).
 * [1,1,1,1,1,0,0,0,0] using 2 swaps.
 * There is no way to group all 1&#39;s together with 0 or 1 swaps.
 * Thus, the minimum number of swaps required is 2.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,1,0,0,1]
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong> All the 1&#39;s are already grouped together due to the circular property of the array.
 * Thus, the minimum number of swaps required is 0.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>nums[i]</code> is either <code>0</code> or <code>1</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2134_MinimumSwapsToGroupAll1sTogetherII {

    // 可以用滑动窗口来做
    @Answer
    public int minSwaps(int[] nums) {
        final int n = nums.length;

        // 1 的总数, 将其作为滑动窗口的大小
        int size = 0;
        for (int i = 0; i < n; i++) {
            size += nums[i];
        }

        // 凡是为 0 的位置就需要挪动外面的1 进来,
        // 因此就是要求这个滑动窗口中0 的最小值
        int zero = 0;
        for (int i = 0; i < size; i++) {
            zero += 1 - nums[i];
        }
        int res = zero;
        for (int i = size; i < n + size; i++) {
            zero -= 1 - nums[(i - size) % n];
            zero += 1 - nums[i % n];
            res = Math.min(res, zero);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1, 0, 1, 1, 0, 0}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 1, 1, 0, 0, 1, 1, 0}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1, 0, 0, 1}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 1, 1, 0, 0, 1, 0, 1, 1, 0}).expect(1);

}
