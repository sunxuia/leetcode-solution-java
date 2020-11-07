package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1283. Find the Smallest Divisor Given a Threshold
 * https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/
 *
 * Given an array of integers nums and an integer threshold, we will choose a positive integer divisor and divide all
 * the array by it and sum the result of the division. Find the smallest divisor such that the result mentioned above is
 * less than or equal to threshold.
 *
 * Each result of division is rounded to the nearest integer greater than or equal to that element. (For example: 7/3 =
 * 3 and 10/2 = 5).
 *
 * It is guaranteed that there will be an answer.
 *
 * Example 1:
 *
 * Input: nums = [1,2,5,9], threshold = 6
 * Output: 5
 * Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1.
 * If the divisor is 4 we can get a sum to 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2).
 *
 * Example 2:
 *
 * Input: nums = [2,3,5,7,11], threshold = 11
 * Output: 3
 *
 * Example 3:
 *
 * Input: nums = [19], threshold = 5
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= nums.length <= 5 * 10^4
 * 1 <= nums[i] <= 10^6
 * nums.length <= threshold <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1283_FindTheSmallestDivisorGivenAThreshold {

    @Answer
    public int smallestDivisor(int[] nums, int threshold) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        int start = 1, end = max;
        while (start < end) {
            int mid = (start + end) / 2;
            int sum = getSum(nums, mid);
            if (sum > threshold) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    private int getSum(int[] nums, int divisor) {
        int res = 0;
        for (int num : nums) {
            res += (num + divisor - 1) / divisor;
        }
        return res;
    }


    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 5, 9}, 6).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{2, 3, 5, 7, 11}, 11).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{19}, 5).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{962551, 933661, 905225, 923035, 990560}, 10)
            .expect(495280);

}
