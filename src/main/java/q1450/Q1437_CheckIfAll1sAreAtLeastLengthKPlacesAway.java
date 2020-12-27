package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1437. Check If All 1's Are at Least Length K Places Away
 * https://leetcode.com/problems/check-if-all-1s-are-at-least-length-k-places-away/
 *
 * Given an array nums of 0s and 1s and an integer k, return True if all 1's are at least k places away from each other,
 * otherwise return False.
 *
 * Example 1:
 * <img src="./Q1437_PIC1.png">
 * Input: nums = [1,0,0,0,1,0,0,1], k = 2
 * Output: true
 * Explanation: Each of the 1s are at least 2 places away from each other.
 *
 * Example 2:
 * <img src="./Q1437_PIC2.png">
 * Input: nums = [1,0,0,1,0,1], k = 2
 * Output: false
 * Explanation: The second 1 and third 1 are only one apart from each other.
 *
 * Example 3:
 *
 * Input: nums = [1,1,1,1,1], k = 0
 * Output: true
 *
 * Example 4:
 *
 * Input: nums = [0,1,0,1], k = 1
 * Output: true
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 0 <= k <= nums.length
 * nums[i] is 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1437_CheckIfAll1sAreAtLeastLengthKPlacesAway {

    @Answer
    public boolean kLengthApart(int[] nums, int k) {
        int prev = -k - 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (i - prev <= k) {
                    return false;
                }
                prev = i;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 0, 0, 0, 1, 0, 0, 1}, 2)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 0, 0, 1, 0, 1}, 2)
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1}, 0)
            .expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{0, 1, 0, 1}, 1)
            .expect(true);

}
