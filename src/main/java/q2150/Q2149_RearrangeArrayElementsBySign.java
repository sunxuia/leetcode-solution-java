package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2149. Rearrange Array Elements by Sign</h3>
 * <a href="https://leetcode.com/problems/rearrange-array-elements-by-sign/">
 * https://leetcode.com/problems/rearrange-array-elements-by-sign/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> integer array <code>nums</code> of <strong>even</strong> length
 * consisting of an <strong>equal</strong> number of positive and negative integers.</p>
 *
 * <p>You should return the array of nums such that the the array follows the given conditions:</p>
 *
 * <ol>
 * 	<li>Every <strong>consecutive pair</strong> of integers have <strong>opposite signs</strong>.</li>
 * 	<li>For all integers with the same sign, the <strong>order</strong> in which they were present in
 * 	<code>nums</code> is <strong>preserved</strong>.</li>
 * 	<li>The rearranged array begins with a positive integer.</li>
 * </ol>
 *
 * <p>Return <em>the modified array after rearranging the elements to satisfy the aforementioned conditions</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [3,1,-2,-5,2,-4]
 * <strong>Output:</strong> [3,-2,1,-5,2,-4]
 * <strong>Explanation:</strong>
 * The positive integers in nums are [3,1,2]. The negative integers are [-2,-5,-4].
 * The only possible way to rearrange them such that they satisfy all conditions is [3,-2,1,-5,2,-4].
 * Other ways such as [1,-2,2,-5,3,-4], [3,1,2,-2,-5,-4], [-2,3,-5,1,-4,2] are incorrect because they do not satisfy one or more conditions.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [-1,1]
 * <strong>Output:</strong> [1,-1]
 * <strong>Explanation:</strong>
 * 1 is the only positive integer and -1 the only negative integer in nums.
 * So nums is rearranged to [1,-1].
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 &lt;= nums.length &lt;= 2 * 10<sup>5</sup></code></li>
 * 	<li><code>nums.length</code> is <strong>even</strong></li>
 * 	<li><code>1 &lt;= |nums[i]| &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>nums</code> consists of <strong>equal</strong> number of positive and negative integers.</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * It is not required to do the modifications in-place.
 */
@RunWith(LeetCodeRunner.class)
public class Q2149_RearrangeArrayElementsBySign {

    @Answer
    public int[] rearrangeArray(int[] nums) {
        int[] res = new int[nums.length];
        int positive = 0, negative = 1;
        for (int num : nums) {
            if (num > 0) {
                res[positive] = num;
                positive += 2;
            } else {
                res[negative] = num;
                negative += 2;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{3, 1, -2, -5, 2, -4})
            .expect(new int[]{3, -2, 1, -5, 2, -4});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{-1, 1})
            .expect(new int[]{1, -1});

}
