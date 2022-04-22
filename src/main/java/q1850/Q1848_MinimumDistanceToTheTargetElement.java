package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1848. Minimum Distance to the Target Element
 * https://leetcode.com/problems/minimum-distance-to-the-target-element/
 *
 * Given an integer array nums (0-indexed) and two integers target and start, find an index i such that nums[i] ==
 * target and abs(i - start) is minimized. Note that abs(x) is the absolute value of x.
 *
 * Return abs(i - start).
 *
 * It is guaranteed that target exists in nums.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,5], target = 5, start = 3
 * Output: 1
 * Explanation: nums[4] = 5 is the only value equal to target, so the answer is abs(4 - 3) = 1.
 *
 * Example 2:
 *
 * Input: nums = [1], target = 1, start = 0
 * Output: 0
 * Explanation: nums[0] = 1 is the only value equal to target, so the answer is abs(0 - 0) = 0.
 *
 * Example 3:
 *
 * Input: nums = [1,1,1,1,1,1,1,1,1,1], target = 1, start = 0
 * Output: 0
 * Explanation: Every value of nums is 1, but nums[0] minimizes abs(i - start), which is abs(0 - 0) = 0.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 10^4
 * 0 <= start < nums.length
 * target is in nums.
 */
@RunWith(LeetCodeRunner.class)
public class Q1848_MinimumDistanceToTheTargetElement {

    @Answer
    public int getMinDistance(int[] nums, int target, int start) {
        for (int offset = 0; ; offset++) {
            if (0 <= start - offset && nums[start - offset] == target
                    || start + offset < nums.length && nums[start + offset] == target) {
                return offset;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, 5, 3).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1}, 1, 0).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 1, 0).expect(0);

}
