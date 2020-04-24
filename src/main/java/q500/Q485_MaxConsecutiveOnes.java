package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/max-consecutive-ones/
 *
 * Given a binary array, find the maximum number of consecutive 1s in this array.
 *
 * Example 1:
 *
 * Input: [1,1,0,1,1,1]
 * Output: 3
 * Explanation: The first two digits or the last three digits are consecutive 1s.
 * The maximum number of consecutive 1s is 3.
 *
 * Note:
 *
 * The input array will only contain 0 and 1.
 * The length of input array is a positive integer and will not exceed 10,000
 */
@RunWith(LeetCodeRunner.class)
public class Q485_MaxConsecutiveOnes {

    @Answer
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, cur = 0;
        for (int num : nums) {
            if (num == 0) {
                max = Math.max(max, cur);
                cur = 0;
            } else {
                cur++;
            }
        }
        return Math.max(max, cur);
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 1, 0, 1, 1, 1}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 0, 1, 1, 0, 1}).expect(2);

}
