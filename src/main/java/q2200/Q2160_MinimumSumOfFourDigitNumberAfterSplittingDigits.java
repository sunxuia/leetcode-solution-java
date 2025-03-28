package q2200;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2160. Minimum Sum of Four Digit Number After Splitting Digits</h3>
 * <a href="https://leetcode.com/problems/minimum-sum-of-four-digit-number-after-splitting-digits/">
 * https://leetcode.com/problems/minimum-sum-of-four-digit-number-after-splitting-digits/
 * </a><br/>
 *
 * <p>You are given a <strong>positive</strong> integer <code>num</code> consisting of exactly four digits. Split
 * <code>num</code> into two new integers <code>new1</code> and <code>new2</code> by using the <strong>digits</strong>
 * found in <code>num</code>. <strong>Leading zeros</strong> are allowed in <code>new1</code> and <code>new2</code>, and
 * <strong>all</strong> the digits found in <code>num</code> must be used.</p>
 *
 * <ul>
 * 	<li>For example, given <code>num = 2932</code>, you have the following digits: two <code>2</code>&#39;s, one
 * 	<code>9</code> and one <code>3</code>. Some of the possible pairs <code>[new1, new2]</code> are <code>[22,
 * 	93]</code>, <code>[23, 92]</code>, <code>[223, 9]</code> and <code>[2, 329]</code>.</li>
 * </ul>
 *
 * <p>Return <em>the <strong>minimum</strong> possible sum of </em><code>new1</code><em> and </em><code>new2</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = 2932
 * <strong>Output:</strong> 52
 * <strong>Explanation:</strong> Some possible pairs [new1, new2] are [29, 23], [223, 9], etc.
 * The minimum sum can be obtained by the pair [29, 23]: 29 + 23 = 52.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = 4009
 * <strong>Output:</strong> 13
 * <strong>Explanation:</strong> Some possible pairs [new1, new2] are [0, 49], [490, 0], etc.
 * The minimum sum can be obtained by the pair [4, 9]: 4 + 9 = 13.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1000 &lt;= num &lt;= 9999</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2160_MinimumSumOfFourDigitNumberAfterSplittingDigits {

    @Answer
    public int minimumSum(int num) {
        int[] nums = new int[]{
                num / 1000,
                num / 100 % 10,
                num / 10 % 10,
                num % 10
        };
        Arrays.sort(nums);
        return nums[0] * 10 + nums[1] * 10 + nums[2] + nums[3];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2932).expect(52);

    @TestData
    public DataExpectation example2 = DataExpectation.create(4009).expect(13);

}
