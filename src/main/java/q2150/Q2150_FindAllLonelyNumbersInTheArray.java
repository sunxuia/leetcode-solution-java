package q2150;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2150. Find All Lonely Numbers in the Array</h3>
 * <a href="https://leetcode.com/problems/find-all-lonely-numbers-in-the-array/">
 * https://leetcode.com/problems/find-all-lonely-numbers-in-the-array/
 * </a><br/>
 *
 * <p>You are given an integer array <code>nums</code>. A number <code>x</code> is <strong>lonely</strong> when it
 * appears only <strong>once</strong>, and no <strong>adjacent</strong> numbers (i.e. <code>x + 1</code> and <code>x -
 * 1)</code> appear in the array.</p>
 *
 * <p>Return <em><strong>all</strong> lonely numbers in </em><code>nums</code>. You may return the answer in <strong>any
 * order</strong>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [10,6,5,8]
 * <strong>Output:</strong> [10,8]
 * <strong>Explanation:</strong>
 * - 10 is a lonely number since it appears exactly once and 9 and 11 does not appear in nums.
 * - 8 is a lonely number since it appears exactly once and 7 and 9 does not appear in nums.
 * - 5 is not a lonely number since 6 appears in nums and vice versa.
 * Hence, the lonely numbers in nums are [10, 8].
 * Note that [8, 10] may also be returned.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,3,5,3]
 * <strong>Output:</strong> [1,5]
 * <strong>Explanation:</strong>
 * - 1 is a lonely number since it appears exactly once and 0 and 2 does not appear in nums.
 * - 5 is a lonely number since it appears exactly once and 4 and 6 does not appear in nums.
 * - 3 is not a lonely number since it appears twice.
 * Hence, the lonely numbers in nums are [1, 5].
 * Note that [5, 1] may also be returned.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>0 &lt;= nums[i] &lt;= 10<sup>6</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2150_FindAllLonelyNumbersInTheArray {

    @Answer
    public List<Integer> findLonely(int[] nums) {
        int min = 100_0000, max = 0;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        // 为 min 的左边留出一个空位, 方便后面找结果的条件判断
        min -= 1;
        // max 的右边也留出一个空位, 方便后面找结果的条件判断
        int[] bucket = new int[max - min + 2];
        for (int num : nums) {
            bucket[num - min]++;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= max - min; i++) {
            if (bucket[i - 1] == 0
                    && bucket[i] == 1
                    && bucket[i + 1] == 0) {
                res.add(i + min);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{10, 6, 5, 8}).expect(List.of(10, 8)).unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 3, 5, 3}).expect(List.of(1, 5)).unOrder();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{61, 83, 92, 92, 42, 60, 16, 45, 32, 14, 40, 7, 10, 34, 62, 33, 65, 79, 7, 14, 85, 21, 36,
                    5, 99, 25, 0, 14, 52, 41, 40})
            .expect(List.of(83, 16, 45, 10, 65, 79, 85, 21, 36, 5, 99, 25, 0, 52))
            .unOrder();

}
