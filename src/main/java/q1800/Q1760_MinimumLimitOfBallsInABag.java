package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1760. Minimum Limit of Balls in a Bag
 * https://leetcode.com/problems/minimum-limit-of-balls-in-a-bag/
 *
 * You are given an integer array nums where the ith bag contains nums[i] balls. You are also given an integer
 * maxOperations.
 *
 * You can perform the following operation at most maxOperations times:
 *
 * Take any bag of balls and divide it into two new bags with a positive number of balls.
 *
 * For example, a bag of 5 balls can become two new bags of 1 and 4 balls, or two new bags of 2 and 3 balls.
 *
 *
 *
 * Your penalty is the maximum number of balls in a bag. You want to minimize your penalty after the operations.
 *
 * Return the minimum possible penalty after performing the operations.
 *
 * Example 1:
 *
 * Input: nums = [9], maxOperations = 2
 * Output: 3
 * Explanation:
 * - Divide the bag with 9 balls into two bags of sizes 6 and 3. [9] -> [6,3].
 * - Divide the bag with 6 balls into two bags of sizes 3 and 3. [6,3] -> [3,3,3].
 * The bag with the most number of balls has 3 balls, so your penalty is 3 and you should return 3.
 *
 * Example 2:
 *
 * Input: nums = [2,4,8,2], maxOperations = 4
 * Output: 2
 * Explanation:
 * - Divide the bag with 8 balls into two bags of sizes 4 and 4. [2,4,8,2] -> [2,4,4,4,2].
 * - Divide the bag with 4 balls into two bags of sizes 2 and 2. [2,4,4,4,2] -> [2,2,2,4,4,2].
 * - Divide the bag with 4 balls into two bags of sizes 2 and 2. [2,2,2,4,4,2] -> [2,2,2,2,2,4,2].
 * - Divide the bag with 4 balls into two bags of sizes 2 and 2. [2,2,2,2,2,4,2] -> [2,2,2,2,2,2,2,2].
 * The bag with the most number of balls has 2 balls, so your penalty is 2 an you should return 2.
 *
 * Example 3:
 *
 * Input: nums = [7,17], maxOperations = 2
 * Output: 7
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= maxOperations, nums[i] <= 10^9
 *
 * 题解 :
 * 可以将 nums 中的每个元素进行拆分( nums[i] = a + b, a > 0, b > 0, a 和 b 还可以进行拆分),
 * 最多可以做 maxOperations 次拆分操作, 最后剩下的元素中取最大值, 现在要求这个最大值最小可能的值.
 */
@RunWith(LeetCodeRunner.class)
public class Q1760_MinimumLimitOfBallsInABag {

    /**
     * 根据 hint 中的提示, 可以使用二分法逼近结果
     */
    @Answer
    public int minimumSize(int[] nums, int maxOperations) {
        Arrays.sort(nums);
        // 二分法寻找可能的结果
        int start = 1, end = (int) 1e9;
        while (start < end) {
            int mid = (start + end) / 2;
            // 最大数字 <= mid 需要的操作次数
            int ops = 0;
            for (int i = 1, prev = 0; prev < nums.length; i++) {
                int size = countLessEqualThan(nums, i * mid);
                ops += (i - 1) * (size - prev);
                prev = size;
            }
            if (ops > maxOperations) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    /**
     * 二分法查找 <= target 的个数
     */
    private int countLessEqualThan(int[] nums, int target) {
        int start = 0, end = nums.length;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] <= target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{9}, 2).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{2, 4, 8, 2}, 4).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{7, 17}, 2).expect(7);

}
