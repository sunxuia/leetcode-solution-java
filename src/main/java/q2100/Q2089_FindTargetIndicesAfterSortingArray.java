package q2100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2089. Find Target Indices After Sorting Array</h3>
 * <a href="https://leetcode.com/problems/find-target-indices-after-sorting-array/">
 * https://leetcode.com/problems/find-target-indices-after-sorting-array/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> integer array <code>nums</code> and a target element
 * <code>target</code>.</p>
 *
 * <p>A <strong>target index</strong> is an index <code>i</code> such that <code>nums[i] == target</code>.</p>
 *
 * <p>Return <em>a list of the target indices of</em> <code>nums</code> after<em> sorting </em><code>nums</code><em> in
 * <strong>non-decreasing</strong> order</em>. If there are no target indices, return <em>an <strong>empty</strong>
 * list</em>. The returned list must be sorted in <strong>increasing</strong> order.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,2,5,2,3], target = 2
 * <strong>Output:</strong> [1,2]
 * <strong>Explanation:</strong> After sorting, nums is [1,<u><strong>2</strong></u>,<u><strong>2</strong></u>,3,5].
 * The indices where nums[i] == 2 are 1 and 2.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,2,5,2,3], target = 3
 * <strong>Output:</strong> [3]
 * <strong>Explanation:</strong> After sorting, nums is [1,2,2,<u><strong>3</strong></u>,5].
 * The index where nums[i] == 3 is 3.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,2,5,2,3], target = 5
 * <strong>Output:</strong> [4]
 * <strong>Explanation:</strong> After sorting, nums is [1,2,2,3,<u><strong>5</strong></u>].
 * The index where nums[i] == 5 is 4.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
 * 	<li><code>1 &lt;= nums[i], target &lt;= 100</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2089_FindTargetIndicesAfterSortingArray {

    @Answer
    public List<Integer> targetIndices(int[] nums, int target) {
        int start = 0, count = 0;
        for (int num : nums) {
            if (num < target) {
                start++;
            } else if (num == target) {
                count++;
            }
        }
        List<Integer> res = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            res.add(i + start);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 5, 2, 3}, 2).expect(List.of(1, 2));

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 5, 2, 3}, 3).expect(List.of(3));

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 5, 2, 3}, 5).expect(List.of(4));

}
