package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2091. Removing Minimum and Maximum From Array</h3>
 * <a href="https://leetcode.com/problems/removing-minimum-and-maximum-from-array/">
 * https://leetcode.com/problems/removing-minimum-and-maximum-from-array/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> array of <strong>distinct</strong> integers <code>nums</code>.</p>
 *
 * <p>There is an element in <code>nums</code> that has the <strong>lowest</strong> value and an element that has the
 * <strong>highest</strong> value. We call them the <strong>minimum</strong> and <strong>maximum</strong> respectively.
 * Your goal is to remove <strong>both</strong> these elements from the array.</p>
 *
 * <p>A <strong>deletion</strong> is defined as either removing an element from the <strong>front</strong> of the array
 * or removing an element from the <strong>back</strong> of the array.</p>
 *
 * <p>Return <em>the <strong>minimum</strong> number of deletions it would take to remove <strong>both</strong> the
 * minimum and maximum element from the array.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,<u><strong>10</strong></u>,7,5,4,<u><strong>1</strong></u>,8,6]
 * <strong>Output:</strong> 5
 * <strong>Explanation:</strong>
 * The minimum element in the array is nums[5], which is 1.
 * The maximum element in the array is nums[1], which is 10.
 * We can remove both the minimum and maximum by removing 2 elements from the front and 3 elements from the back.
 * This results in 2 + 3 = 5 deletions, which is the minimum number possible.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [0,<u><strong>-4</strong></u>,<u><strong>19</strong></u>,1,8,-2,-3,5]
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong>
 * The minimum element in the array is nums[1], which is -4.
 * The maximum element in the array is nums[2], which is 19.
 * We can remove both the minimum and maximum by removing 3 elements from the front.
 * This results in only 3 deletions, which is the minimum number possible.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [<u><strong>101</strong></u>]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong>
 * There is only one element in the array, which makes it both the minimum and maximum element.
 * We can remove it with 1 deletion.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>-10<sup>5</sup> &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
 * 	<li>The integers in <code>nums</code> are <strong>distinct</strong>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2091_RemovingMinimumAndMaximumFromArray {

    @Answer
    public int minimumDeletions(int[] nums) {
        final int n = nums.length;
        int min = 0, max = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[min]) {
                min = i;
            } else if (nums[max] < nums[i]) {
                max = i;
            }
        }

        int left = Math.min(min, max);
        int right = Math.max(min, max);
        return Math.min(Math.min(right + 1, n - left), left + 1 + n - right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 10, 7, 5, 4, 1, 8, 6}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, -4, 19, 1, 8, -2, -3, 5}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{101}).expect(1);

}
