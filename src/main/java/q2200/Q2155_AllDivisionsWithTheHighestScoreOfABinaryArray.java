package q2200;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2155. All Divisions With the Highest Score of a Binary Array</h3>
 * <a href="https://leetcode.com/problems/all-divisions-with-the-highest-score-of-a-binary-array/">
 * https://leetcode.com/problems/all-divisions-with-the-highest-score-of-a-binary-array/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> binary array <code>nums</code> of length <code>n</code>.
 * <code>nums</code> can be divided at index <code>i</code> (where <code>0 &lt;= i &lt;= n)</code> into two arrays
 * (possibly empty) <code>nums<sub>left</sub></code> and <code>nums<sub>right</sub></code>:</p>
 *
 * <ul>
 * 	<li><code>nums<sub>left</sub></code> has all the elements of <code>nums</code> between index <code>0</code> and
 * 	<code>i - 1</code> <strong>(inclusive)</strong>, while <code>nums<sub>right</sub></code> has all the elements of
 * 	nums between index <code>i</code> and <code>n - 1</code> <strong>(inclusive)</strong>.</li>
 * 	<li>If <code>i == 0</code>, <code>nums<sub>left</sub></code> is <strong>empty</strong>, while
 * 	<code>nums<sub>right</sub></code> has all the elements of <code>nums</code>.</li>
 * 	<li>If <code>i == n</code>, <code>nums<sub>left</sub></code> has all the elements of nums, while
 * 	<code>nums<sub>right</sub></code> is <strong>empty</strong>.</li>
 * </ul>
 *
 * <p>The <strong>division score</strong> of an index <code>i</code> is the <strong>sum</strong> of the number of
 * <code>0</code>&#39;s in <code>nums<sub>left</sub></code> and the number of <code>1</code>&#39;s in
 * <code>nums<sub>right</sub></code>.</p>
 *
 * <p>Return <em><strong>all distinct indices</strong> that have the <strong>highest</strong> possible
 * <strong>division score</strong></em>. You may return the answer in <strong>any order</strong>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [0,0,1,0]
 * <strong>Output:</strong> [2,4]
 * <strong>Explanation:</strong> Division at index
 * - 0: nums<sub>left</sub> is []. nums<sub>right</sub> is [0,0,<u><strong>1</strong></u>,0]. The score is 0 + 1 = 1.
 * - 1: nums<sub>left</sub> is [<u><strong>0</strong></u>]. nums<sub>right</sub> is [0,<u><strong>1</strong></u>,0]. The score is 1 + 1 = 2.
 * - 2: nums<sub>left</sub> is [<u><strong>0</strong></u>,<u><strong>0</strong></u>]. nums<sub>right</sub> is [<u><strong>1</strong></u>,0]. The score is 2 + 1 = 3.
 * - 3: nums<sub>left</sub> is [<u><strong>0</strong></u>,<u><strong>0</strong></u>,1]. nums<sub>right</sub> is [0]. The score is 2 + 0 = 2.
 * - 4: nums<sub>left</sub> is [<u><strong>0</strong></u>,<u><strong>0</strong></u>,1,<u><strong>0</strong></u>]. nums<sub>right</sub> is []. The score is 3 + 0 = 3.
 * Indices 2 and 4 both have the highest possible division score 3.
 * Note the answer [4,2] would also be accepted.</pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [0,0,0]
 * <strong>Output:</strong> [3]
 * <strong>Explanation:</strong> Division at index
 * - 0: nums<sub>left</sub> is []. nums<sub>right</sub> is [0,0,0]. The score is 0 + 0 = 0.
 * - 1: nums<sub>left</sub> is [<u><strong>0</strong></u>]. nums<sub>right</sub> is [0,0]. The score is 1 + 0 = 1.
 * - 2: nums<sub>left</sub> is [<u><strong>0</strong></u>,<u><strong>0</strong></u>]. nums<sub>right</sub> is [0]. The score is 2 + 0 = 2.
 * - 3: nums<sub>left</sub> is [<u><strong>0</strong></u>,<u><strong>0</strong></u>,<u><strong>0</strong></u>]. nums<sub>right</sub> is []. The score is 3 + 0 = 3.
 * Only index 3 has the highest possible division score 3.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,1]
 * <strong>Output:</strong> [0]
 * <strong>Explanation:</strong> Division at index
 * - 0: nums<sub>left</sub> is []. nums<sub>right</sub> is [<u><strong>1</strong></u>,<u><strong>1</strong></u>]. The score is 0 + 2 = 2.
 * - 1: nums<sub>left</sub> is [1]. nums<sub>right</sub> is [<u><strong>1</strong></u>]. The score is 0 + 1 = 1.
 * - 2: nums<sub>left</sub> is [1,1]. nums<sub>right</sub> is []. The score is 0 + 0 = 0.
 * Only index 0 has the highest possible division score 2.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == nums.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>nums[i]</code> is either <code>0</code> or <code>1</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2155_AllDivisionsWithTheHighestScoreOfABinaryArray {

    @Answer
    public List<Integer> maxScoreIndices(int[] nums) {
        int left = 0, right = 0, max = 0;
        for (int num : nums) {
            left += 1 - num;
            right -= num;
            max = Math.max(max, left + right);
        }

        List<Integer> res = new ArrayList<>();
        left = right = 0;
        if (max == 0) {
            res.add(0);
        }
        for (int i = 0; i < nums.length; i++) {
            left += 1 - nums[i];
            right -= nums[i];
            if (left + right == max) {
                res.add(i + 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 0, 1, 0}).expect(List.of(2, 4)).unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 0, 0}).expect(List.of(3));

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1}).expect(List.of(0));

}
