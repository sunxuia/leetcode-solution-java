package q2200;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * <h3>[Hard] 2163. Minimum Difference in Sums After Removal of Elements</h3>
 * <a href="https://leetcode.com/problems/minimum-difference-in-sums-after-removal-of-elements/">
 * https://leetcode.com/problems/minimum-difference-in-sums-after-removal-of-elements/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> integer array <code>nums</code> consisting of <code>3 * n</code>
 * elements.</p>
 *
 * <p>You are allowed to remove any <strong>subsequence</strong> of elements of size <strong>exactly</strong>
 * <code>n</code> from <code>nums</code>. The remaining <code>2 * n</code> elements will be divided into two
 * <strong>equal</strong> parts:</p>
 *
 * <ul>
 * 	<li>The first <code>n</code> elements belonging to the first part and their sum is
 * 	<code>sum<sub>first</sub></code>.</li>
 * 	<li>The next <code>n</code> elements belonging to the second part and their sum is
 * 	<code>sum<sub>second</sub></code>.</li>
 * </ul>
 *
 * <p>The <strong>difference in sums</strong> of the two parts is denoted as <code>sum<sub>first</sub> -
 * sum<sub>second</sub></code>.</p>
 *
 * <ul>
 * 	<li>For example, if <code>sum<sub>first</sub> = 3</code> and <code>sum<sub>second</sub> = 2</code>, their
 * 	difference is <code>1</code>.</li>
 * 	<li>Similarly, if <code>sum<sub>first</sub> = 2</code> and <code>sum<sub>second</sub> = 3</code>, their difference
 * 	is <code>-1</code>.</li>
 * </ul>
 *
 * <p>Return <em>the <strong>minimum difference</strong> possible between the sums of the two parts after the removal
 * of </em><code>n</code><em> elements</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [3,1,2]
 * <strong>Output:</strong> -1
 * <strong>Explanation:</strong> Here, nums has 3 elements, so n = 1.
 * Thus we have to remove 1 element from nums and divide the array into two equal parts.
 * - If we remove nums[0] = 3, the array will be [1,2]. The difference in sums of the two parts will be 1 - 2 = -1.
 * - If we remove nums[1] = 1, the array will be [3,2]. The difference in sums of the two parts will be 3 - 2 = 1.
 * - If we remove nums[2] = 2, the array will be [3,1]. The difference in sums of the two parts will be 3 - 1 = 2.
 * The minimum difference between sums of the two parts is min(-1,1,2) = -1.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [7,9,5,8,1,3]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> Here n = 2. So we must remove 2 elements and divide the remaining array into two parts containing two elements each.
 * If we remove nums[2] = 5 and nums[3] = 8, the resultant array will be [7,9,1,3]. The difference in sums will be (7+9) - (1+3) = 12.
 * To obtain the minimum difference, we should remove nums[1] = 9 and nums[4] = 1. The resultant array becomes [7,5,8,3]. The difference in sums of the two parts is (7+5) - (8+3) = 1.
 * It can be shown that it is not possible to obtain a difference smaller than 1.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>nums.length == 3 * n</code></li>
 * 	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2163_MinimumDifferenceInSumsAfterRemovalOfElements {

    /**
     * 假设分隔点为i, 左边的数字和为left, 右边(含i点)的数字和为right,
     * 差值为 left - right 要最小, 因此要去掉左边最大的i-n 个值和右边最小的 3n-i-n 个值.
     * 时间复杂度 O(NlogN)
     */
    @Answer
    public long minimumDifference(int[] nums) {
        final int n = nums.length / 3;

        // 先从右到左计算右边最大n 个元素之和
        // right[i-n] 表示 nums[i:3n) 的最大n 个元素之和 (n <= i <= 2n)
        long[] right = new long[n + 1];
        // 右边最大的n 个元素的集合
        PriorityQueue<Integer> rightMax = new PriorityQueue<>(n + 1);
        for (int i = 3 * n - 1; i >= 2 * n; i--) {
            right[n] += nums[i];
            rightMax.offer(nums[i]);
        }
        for (int i = 2 * n - 1; i >= n; i--) {
            rightMax.offer(nums[i]);
            right[i - n] = right[i - n + 1] - rightMax.poll() + nums[i];
        }

        // 计算 i==n 时左边最小值的集合
        long left = 0;
        // 左边最小的n 个元素的集合
        PriorityQueue<Integer> leftMin = new PriorityQueue<>(n + 1, Comparator.reverseOrder());
        for (int i = 0; i < n; i++) {
            left += nums[i];
            leftMin.offer(nums[i]);
        }

        long res = left - right[0];
        // 从左到右移动i 的值, 将 nums[i] 从右边移动到左边, 计算最小差值
        for (int i = n; i < 2 * n; i++) {
            leftMin.offer(nums[i]);
            left += nums[i] - leftMin.poll();
            long diff = left - right[i - n + 1];
            res = Math.min(res, diff);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 1, 2}).expect(-1L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 9, 5, 8, 1, 3}).expect(1L);

    @TestData
    public DataExpectation overflow = DataExpectation
            .create(TestDataFileHelper.read(int[].class))
            .expect(-9999900000L);

}
