package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2154. Keep Multiplying Found Values by Two</h3>
 * <a href="https://leetcode.com/problems/keep-multiplying-found-values-by-two/">
 * https://leetcode.com/problems/keep-multiplying-found-values-by-two/
 * </a><br/>
 *
 * <p>You are given an array of integers <code>nums</code>. You are also given an integer <code>original</code> which is
 * the first number that needs to be searched for in <code>nums</code>.</p>
 *
 * <p>You then do the following steps:</p>
 *
 * <ol>
 * 	<li>If <code>original</code> is found in <code>nums</code>, <strong>multiply</strong> it by two (i.e., set
 * 	<code>original = 2 * original</code>).</li>
 * 	<li>Otherwise, <strong>stop</strong> the process.</li>
 * 	<li><strong>Repeat</strong> this process with the new number as long as you keep finding the number.</li>
 * </ol>
 *
 * <p>Return <em>the <strong>final</strong> value of </em><code>original</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [5,3,6,1,12], original = 3
 * <strong>Output:</strong> 24
 * <strong>Explanation:</strong>
 * - 3 is found in nums. 3 is multiplied by 2 to obtain 6.
 * - 6 is found in nums. 6 is multiplied by 2 to obtain 12.
 * - 12 is found in nums. 12 is multiplied by 2 to obtain 24.
 * - 24 is not found in nums. Thus, 24 is returned.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,7,9], original = 4
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong>
 * - 4 is not found in nums. Thus, 4 is returned.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 1000</code></li>
 * 	<li><code>1 &lt;= nums[i], original &lt;= 1000</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2154_KeepMultiplyingFoundValuesByTwo {

    @Answer
    public int findFinalValue(int[] nums, int original) {
        boolean[] buckets = new boolean[1001];
        for (int num : nums) {
            buckets[num] = true;
        }
        while (original <= 1000 && buckets[original]) {
            original *= 2;
        }
        return original;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{5, 3, 6, 1, 12}, 3)
            .expect(24);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 7, 9}, 4)
            .expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{161, 28, 640, 264, 81, 561, 320, 2, 61, 244, 183, 108, 773, 61, 976, 122, 988, 2, 370,
                    392, 488, 375, 349, 432, 713, 563}, 61)
            .expect(1952);

}
