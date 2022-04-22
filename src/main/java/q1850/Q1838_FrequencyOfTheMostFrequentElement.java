package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1838. Frequency of the Most Frequent Element
 * https://leetcode.com/problems/frequency-of-the-most-frequent-element/
 *
 * The frequency of an element is the number of times it occurs in an array.
 *
 * You are given an integer array nums and an integer k. In one operation, you can choose an index of nums and increment
 * the element at that index by 1.
 *
 * Return the maximum possible frequency of an element after performing at most k operations.
 *
 * Example 1:
 *
 * Input: nums = [1,2,4], k = 5
 * Output: 3
 * Explanation: Increment the first element three times and the second element two times to make nums = [4,4,4].
 * 4 has a frequency of 3.
 *
 * Example 2:
 *
 * Input: nums = [1,4,8,13], k = 5
 * Output: 2
 * Explanation: There are multiple optimal solutions:
 * - Increment the first element three times to make nums = [4,4,8,13]. 4 has a frequency of 2.
 * - Increment the second element four times to make nums = [1,8,8,13]. 8 has a frequency of 2.
 * - Increment the third element five times to make nums = [1,4,13,13]. 13 has a frequency of 2.
 *
 * Example 3:
 *
 * Input: nums = [3,9,6], k = 2
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1838_FrequencyOfTheMostFrequentElement {

    @Answer
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int i = 0, j = 1, area = 0, res = 1;
        while (j < nums.length) {
            area += (j - i) * (nums[j] - nums[j - 1]);
            while (area > k) {
                area -= nums[j] - nums[i];
                i++;
            }
            res = Math.max(res, j - i + 1);
            j++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 4}, 5).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 4, 8, 13}, 5).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{3, 9, 6}, 2).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{100000}, 100000).expect(1);

}
