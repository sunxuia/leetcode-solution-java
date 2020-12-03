package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1365. How Many Numbers Are Smaller Than the Current Number
 * https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/
 *
 * Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it. That is, for each
 * nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].
 *
 * Return the answer in an array.
 *
 * Example 1:
 *
 * Input: nums = [8,1,2,2,3]
 * Output: [4,0,1,1,3]
 * Explanation:
 * For nums[0]=8 there exist four smaller numbers than it (1, 2, 2 and 3).
 * For nums[1]=1 does not exist any smaller number than it.
 * For nums[2]=2 there exist one smaller number than it (1).
 * For nums[3]=2 there exist one smaller number than it (1).
 * For nums[4]=3 there exist three smaller numbers than it (1, 2 and 2).
 *
 * Example 2:
 *
 * Input: nums = [6,5,4,8]
 * Output: [2,1,0,3]
 *
 * Example 3:
 *
 * Input: nums = [7,7,7,7]
 * Output: [0,0,0,0]
 *
 * Constraints:
 *
 * 2 <= nums.length <= 500
 * 0 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1365_HowManyNumbersAreSmallerThanTheCurrentNumber {

    @Answer
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] counts = new int[101];
        for (int num : nums) {
            counts[num]++;
        }
        for (int i = 1; i < 101; i++) {
            counts[i] += counts[i - 1];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i] == 0 ? 0 : counts[nums[i] - 1];
        }
        return nums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{8, 1, 2, 2, 3}).expect(new int[]{4, 0, 1, 1, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{6, 5, 4, 8}).expect(new int[]{2, 1, 0, 3});

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{7, 7, 7, 7}).expect(new int[]{0, 0, 0, 0});

}
