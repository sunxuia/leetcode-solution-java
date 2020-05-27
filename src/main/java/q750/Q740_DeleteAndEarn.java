package q750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/delete-and-earn/
 *
 * Given an array nums of integers, you can perform operations on the array.
 *
 * In each operation, you pick any nums[i] and delete it to earn nums[i] points. After, you must delete every element
 * equal to nums[i] - 1 or nums[i] + 1.
 *
 * You start with 0 points. Return the maximum number of points you can earn by applying such operations.
 *
 * Example 1:
 *
 * Input: nums = [3, 4, 2]
 * Output: 6
 * Explanation:
 * Delete 4 to earn 4 points, consequently 3 is also deleted.
 * Then, delete 2 to earn 2 points. 6 total points are earned.
 *
 *
 *
 * Example 2:
 *
 * Input: nums = [2, 2, 3, 3, 3, 4]
 * Output: 9
 * Explanation:
 * Delete 3 to earn 3 points, deleting both 2's and the 4.
 * Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
 * 9 total points are earned.
 *
 *
 *
 * Note:
 *
 * The length of nums is at most 20000.
 * Each element nums[i] is an integer in the range [1, 10000].
 */
@RunWith(LeetCodeRunner.class)
public class Q740_DeleteAndEarn {

    @Answer
    public int deleteAndEarn(int[] nums) {
        int[] counts = new int[10002];
        for (int num : nums) {
            counts[num]++;
        }
        int curr = 0, prev = 0;
        for (int i = 1; i <= 10000; i++) {
            int next = Math.max(curr, prev + counts[i] * i);
            prev = curr;
            curr = next;
        }
        return curr;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 4, 2}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 3, 3, 3, 4}).expect(9);

}
