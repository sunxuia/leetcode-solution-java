package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1295. Find Numbers with Even Number of Digits
 * https://leetcode.com/problems/find-numbers-with-even-number-of-digits/
 *
 * Given an array nums of integers, return how many of them contain an even number of digits.
 *
 * Example 1:
 *
 * Input: nums = [12,345,2,6,7896]
 * Output: 2
 * Explanation:
 * 12 contains 2 digits (even number of digits).
 * 345 contains 3 digits (odd number of digits).
 * 2 contains 1 digit (odd number of digits).
 * 6 contains 1 digit (odd number of digits).
 * 7896 contains 4 digits (even number of digits).
 * Therefore only 12 and 7896 contain an even number of digits.
 *
 * Example 2:
 *
 * Input: nums = [555,901,482,1771]
 * Output: 1
 * Explanation:
 * Only 1771 contains an even number of digits.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 500
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1295_FindNumbersWithEvenNumberOfDigits {

    @Answer
    public int findNumbers(int[] nums) {
        int res = 0;
        for (int num : nums) {
            int digit = 1;
            while (num > 9) {
                num /= 10;
                digit++;
            }
            res += 1 - digit % 2;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{12, 345, 2, 6, 7896}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{555, 901, 482, 1771}).expect(1);

}
