package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1509. Minimum Difference Between Largest and Smallest Value in Three Moves
 * https://leetcode.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/
 *
 * Given an array nums, you are allowed to choose one element of nums and change it by any value in one move.
 *
 * Return the minimum difference between the largest and smallest value of nums after perfoming at most 3 moves.
 *
 * Example 1:
 *
 * Input: nums = [5,3,2,4]
 * Output: 0
 * Explanation: Change the array [5,3,2,4] to [2,2,2,2].
 * The difference between the maximum and minimum is 2-2 = 0.
 *
 * Example 2:
 *
 * Input: nums = [1,5,0,10,14]
 * Output: 1
 * Explanation: Change the array [1,5,0,10,14] to [1,1,0,1,1].
 * The difference between the maximum and minimum is 1-0 = 1.
 *
 * Example 3:
 *
 * Input: nums = [6,6,0,1,1,4,6]
 * Output: 2
 *
 * Example 4:
 *
 * Input: nums = [1,5,6,14,15]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1509_MinimumDifferenceBetweenLargestAndSmallestValueInThreeMoves {

    @Answer
    public int minDifference(int[] nums) {
        if (nums.length <= 4) {
            return 0;
        }
        // 分别表示最小/ 最大的 4个数
        int max1, max2, max3, max4;
        int min1, min2, min3, min4;
        max1 = max2 = max3 = max4 = Integer.MIN_VALUE;
        min1 = min2 = min3 = min4 = Integer.MAX_VALUE;
        for (int num : nums) {
            int max = num, min = num;
            if (max > max1) {
                int t = max;
                max = max1;
                max1 = t;
            }
            if (max > max2) {
                int t = max;
                max = max2;
                max2 = t;
            }
            if (max > max3) {
                int t = max;
                max = max3;
                max3 = t;
            }
            if (max > max4) {
                max4 = max;
            }

            if (min < min1) {
                int t = min;
                min = min1;
                min1 = t;
            }
            if (min < min2) {
                int t = min;
                min = min2;
                min2 = t;
            }
            if (min < min3) {
                int t = min;
                min = min3;
                min3 = t;
            }
            if (min < min4) {
                min4 = min;
            }
        }
        return Math.min(
                Math.min(max4 - min1, max3 - min2),
                Math.min(max2 - min3, max1 - min4)
        );
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 3, 2, 4}).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 5, 0, 10, 14}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{6, 6, 0, 1, 1, 4, 6}).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 5, 6, 14, 15}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1}).expect(0);

}
