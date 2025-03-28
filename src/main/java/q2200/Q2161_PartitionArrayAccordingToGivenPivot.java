package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2161. Partition Array According to Given Pivot</h3>
 * <a href="https://leetcode.com/problems/partition-array-according-to-given-pivot/">
 * https://leetcode.com/problems/partition-array-according-to-given-pivot/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> integer array <code>nums</code> and an integer <code>pivot</code>.
 * Rearrange <code>nums</code> such that the following conditions are satisfied:</p>
 *
 * <ul>
 * 	<li>Every element less than <code>pivot</code> appears <strong>before</strong> every element greater than
 * 	<code>pivot</code>.</li>
 * 	<li>Every element equal to <code>pivot</code> appears <strong>in between</strong> the elements less than and
 * 	greater than <code>pivot</code>.</li>
 * 	<li>The <strong>relative order</strong> of the elements less than <code>pivot</code> and the elements greater than
 * 	<code>pivot</code> is maintained.
 * 	<ul>
 * 		<li>More formally, consider every <code>p<sub>i</sub></code>, <code>p<sub>j</sub></code> where
 * 		<code>p<sub>i</sub></code> is the new position of the <code>i<sup>th</sup></code> element and
 * 		<code>p<sub>j</sub></code> is the new position of the <code>j<sup>th</sup></code> element. For elements less
 * 		than <code>pivot</code>, if <code>i &lt; j</code> and <code>nums[i] &lt; pivot</code> and <code>nums[j] &lt;
 * 		pivot</code>, then <code>p<sub>i</sub> &lt; p<sub>j</sub></code>. Similarly for elements greater than
 * 		<code>pivot</code>, if <code>i &lt; j</code> and <code>nums[i] &gt; pivot</code> and <code>nums[j] &gt;
 * 		pivot</code>, then <code>p<sub>i</sub> &lt; p<sub>j</sub></code>.</li>
 * 	</ul>
 * 	</li>
 * </ul>
 *
 * <p>Return <code>nums</code><em> after the rearrangement.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [9,12,5,10,14,3,10], pivot = 10
 * <strong>Output:</strong> [9,5,3,10,10,12,14]
 * <strong>Explanation:</strong>
 * The elements 9, 5, and 3 are less than the pivot so they are on the left side of the array.
 * The elements 12 and 14 are greater than the pivot so they are on the right side of the array.
 * The relative ordering of the elements less than and greater than pivot is also maintained. [9, 5, 3] and [12, 14] are the respective orderings.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [-3,4,3,2], pivot = 2
 * <strong>Output:</strong> [-3,2,4,3]
 * <strong>Explanation:</strong>
 * The element -3 is less than the pivot so it is on the left side of the array.
 * The elements 4 and 3 are greater than the pivot so they are on the right side of the array.
 * The relative ordering of the elements less than and greater than pivot is also maintained. [-3] and [4, 3] are the respective orderings.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>-10<sup>6</sup> &lt;= nums[i] &lt;= 10<sup>6</sup></code></li>
 * 	<li><code>pivot</code> equals to an element of <code>nums</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2161_PartitionArrayAccordingToGivenPivot {

    @Answer
    public int[] pivotArray(int[] nums, int pivot) {
        int[] copied = nums.clone();
        int p = 0;
        for (int num : copied) {
            if (num < pivot) {
                nums[p++] = num;
            }
        }
        for (int num : copied) {
            if (num == pivot) {
                nums[p++] = num;
            }
        }
        for (int num : copied) {
            if (num > pivot) {
                nums[p++] = num;
            }
        }
        return nums;
    }


    // 冒泡法, 时间复杂度 O(N^2), 空间复杂度 O(1)
    @Answer
    public int[] pivotArray2(int[] nums, int pivot) {
        final int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] < pivot) {
                for (int j = i - 1; j >= 0 && nums[j] >= pivot; j--) {
                    int t = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = t;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] == pivot) {
                for (int j = i - 1; j >= 0 && nums[j] > pivot; j--) {
                    int t = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = t;
                }
            }
        }
        return nums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{9, 12, 5, 10, 14, 3, 10}, 10)
            .expect(new int[]{9, 5, 3, 10, 10, 12, 14});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{-3, 4, 3, 2}, 2)
            .expect(new int[]{-3, 2, 4, 3});

}
