package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1748. Sum of Unique Elements
 * https://leetcode.com/problems/sum-of-unique-elements/
 *
 * You are given an integer array nums. The unique elements of an array are the elements that appear exactly once in the
 * array.
 *
 * Return the sum of all the unique elements of nums.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,2]
 * Output: 4
 * Explanation: The unique elements are [1,3], and the sum is 4.
 *
 * Example 2:
 *
 * Input: nums = [1,1,1,1,1]
 * Output: 0
 * Explanation: There are no unique elements, and the sum is 0.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3,4,5]
 * Output: 15
 * Explanation: The unique elements are [1,2,3,4,5], and the sum is 15.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1748_SumOfUniqueElements {

    @Answer
    public int sumOfUnique(int[] nums) {
        int[] counts = new int[101];
        for (int num : nums) {
            counts[num]++;
        }

        int res = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 1) {
                res += i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 2}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 1, 1}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(15);

}
