package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2148. Count Elements With Strictly Smaller and Greater Elements </h3>
 * <a href="https://leetcode.com/problems/count-elements-with-strictly-smaller-and-greater-elements/">
 * https://leetcode.com/problems/count-elements-with-strictly-smaller-and-greater-elements/
 * </a><br/>
 *
 * <p>Given an integer array <code>nums</code>, return <em>the number of elements that have <strong>both</strong> a
 * strictly smaller and a strictly greater element appear in </em><code>nums</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [11,7,2,15]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> The element 7 has the element 2 strictly smaller than it and the element 11 strictly greater than it.
 * Element 11 has element 7 strictly smaller than it and element 15 strictly greater than it.
 * In total there are 2 elements having both a strictly smaller and a strictly greater element appear in <code>nums</code>.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [-3,3,3,90]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> The element 3 has the element -3 strictly smaller than it and the element 90 strictly greater than it.
 * Since there are two elements with the value 3, in total there are 2 elements having both a strictly smaller and a strictly greater element appear in <code>nums</code>.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
 * 	<li><code>-10<sup>5</sup> &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2148_CountElementsWithStrictlySmallerAndGreaterElements {

    @Answer
    public int countElements(int[] nums) {
        int min = nums[0], max = min;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int res = 0;
        for (int num : nums) {
            if (min < num && num < max) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{11, 7, 2, 15}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{-3, 3, 3, 90}).expect(2);

}
