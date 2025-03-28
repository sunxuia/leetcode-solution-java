package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2164. Sort Even and Odd Indices Independently</h3>
 * <a href="https://leetcode.com/problems/sort-even-and-odd-indices-independently/">
 * https://leetcode.com/problems/sort-even-and-odd-indices-independently/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> integer array <code>nums</code>. Rearrange the values of
 * <code>nums</code> according to the following rules:</p>
 *
 * <ol>
 * 	<li>Sort the values at <strong>odd indices</strong> of <code>nums</code> in <strong>non-increasing</strong> order.
 *
 * 	<ul>
 * 		<li>For example, if <code>nums = [4,<strong><u>1</u></strong>,2,<u><strong>3</strong></u>]</code> before this
 * 		step, it becomes <code>[4,<u><strong>3</strong></u>,2,<strong><u>1</u></strong>]</code> after. The values at
 * 		odd indices <code>1</code> and <code>3</code> are sorted in non-increasing order.</li>
 * 	</ul>
 * 	</li>
 * 	<li>Sort the values at <strong>even indices</strong> of <code>nums</code> in <strong>non-decreasing</strong> order.
 * 	<ul>
 * 		<li>For example, if <code>nums = [<u><strong>4</strong></u>,1,<u><strong>2</strong></u>,3]</code> before this
 * 		step, it becomes <code>[<u><strong>2</strong></u>,1,<u><strong>4</strong></u>,3]</code> after. The values at
 * 		even indices <code>0</code> and <code>2</code> are sorted in non-decreasing order.</li>
 * 	</ul>
 * 	</li>
 * </ol>
 *
 * <p>Return <em>the array formed after rearranging the values of</em> <code>nums</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [4,1,2,3]
 * <strong>Output:</strong> [2,3,4,1]
 * <strong>Explanation:</strong>
 * First, we sort the values present at odd indices (1 and 3) in non-increasing order.
 * So, nums changes from [4,<strong><u>1</u></strong>,2,<strong><u>3</u></strong>] to [4,<u><strong>3</strong></u>,2,<strong><u>1</u></strong>].
 * Next, we sort the values present at even indices (0 and 2) in non-decreasing order.
 * So, nums changes from [<u><strong>4</strong></u>,1,<strong><u>2</u></strong>,3] to [<u><strong>2</strong></u>,3,<u><strong>4</strong></u>,1].
 * Thus, the array formed after rearranging the values is [2,3,4,1].
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,1]
 * <strong>Output:</strong> [2,1]
 * <strong>Explanation:</strong>
 * Since there is exactly one odd index and one even index, no rearrangement of values takes place.
 * The resultant array formed is [2,1], which is the same as the initial array.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
 * 	<li><code>1 &lt;= nums[i] &lt;= 100</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2164_SortEvenAndOddIndicesIndependently {

    @Answer
    public int[] sortEvenOdd(int[] nums) {
        final int n = nums.length;
        sortAsc(nums, 0, n - 2 + n % 2);
        sortDesc(nums, 1, n - 1 - n % 2);
        return nums;
    }

    private void sortAsc(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int sentinel = nums[start];
        int i = start, j = end;
        while (i < j) {
            while (i < j && sentinel >= nums[i]) {
                i += 2;
            }
            while (i < j && sentinel < nums[j]) {
                j -= 2;
            }
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
        if (nums[j] < sentinel) {
            nums[start] = nums[j];
            nums[j] = sentinel;
        }
        sortAsc(nums, start, i - 2);
        sortAsc(nums, i, end);
    }

    private void sortDesc(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int sentinel = nums[start];
        int i = start, j = end;
        while (i < j) {
            while (i < j && sentinel <= nums[i]) {
                i += 2;
            }
            while (i < j && sentinel > nums[j]) {
                j -= 2;
            }
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
        if (nums[j] > sentinel) {
            nums[start] = nums[j];
            nums[j] = sentinel;
        }
        sortDesc(nums, start, i - 2);
        sortDesc(nums, i, end);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 1, 2, 3}).expect(new int[]{2, 3, 4, 1});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1}).expect(new int[]{2, 1});

}
