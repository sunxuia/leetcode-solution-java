package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/third-maximum-number/
 *
 * Given a non-empty array of integers, return the third maximum number in this array. If it does not exist, return
 * the maximum number. The time complexity must be in O(n).
 *
 * Example 1:
 *
 * Input: [3, 2, 1]
 *
 * Output: 1
 *
 * Explanation: The third maximum is 1.
 *
 * Example 2:
 *
 * Input: [1, 2]
 *
 * Output: 2
 *
 * Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
 *
 * Example 3:
 *
 * Input: [2, 2, 3, 1]
 *
 * Output: 1
 *
 * Explanation: Note that the third maximum here means the third maximum distinct number.
 * Both numbers with value 2 are both considered as second maximum.
 */
@RunWith(LeetCodeRunner.class)
public class Q414_ThirdMaximumNumber {

    @Answer
    public int thirdMax(int[] nums) {
        long max1, max2, max3;
        max1 = max2 = max3 = Long.MIN_VALUE;
        for (long num : nums) {
            if (num > max1) {
                long t = max1;
                max1 = num;
                num = t;
            }
            if (num > max2 && num < max1) {
                long t = max2;
                max2 = num;
                num = t;
            }
            if (num > max3 && num < max2) {
                max3 = num;
            }
        }
        return (int) (max3 == Long.MIN_VALUE ? max1 : max3);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 2, 1}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, 2, 3, 1}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{1, 2, Integer.MIN_VALUE}).expect(Integer.MIN_VALUE);

}
