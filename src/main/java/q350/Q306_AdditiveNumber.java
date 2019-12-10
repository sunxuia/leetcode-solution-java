package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/additive-number/
 *
 * Additive number is a string whose digits can form additive sequence.
 *
 * A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent
 * number in the sequence must be the sum of the preceding two.
 *
 * Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.
 *
 * Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
 *
 *
 *
 * Example 1:
 *
 * Input: "112358"
 * Output: true
 * Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
 * 1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 *
 * Example 2:
 *
 * Input: "199100199"
 * Output: true
 * Explanation: The additive sequence is: 1, 99, 100, 199.
 * 1 + 99 = 100, 99 + 100 = 199
 *
 *
 *
 * Constraints:
 *
 * num consists only of digits '0'-'9'.
 * 1 <= num.length <= 35
 *
 * Follow up:
 * How would you handle overflow for very large input integers?
 */
@RunWith(LeetCodeRunner.class)
public class Q306_AdditiveNumber {

    @Answer
    public boolean isAdditiveNumber(String num) {
        final int len = num.length();
        if (len < 3) {
            return false;
        }
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = num.charAt(i) - '0';
        }
        if (nums[0] + nums[1] == 0) {
            for (int i = 2; i < len; i++) {
                if (nums[i] != 0) {
                    return false;
                }
            }
            return true;
        }

        int end1 = nums[0] == 0 ? 1 : len / 2;
        long v1 = 0;
        for (int i = 0; i < end1; i++) {
            v1 = v1 * 10 + nums[i];
            int end2 = nums[i + 1] == 0 ? i + 2 : len - i - 1;
            long v2 = 0;
            for (int j = i + 1; j < end2; j++) {
                v2 = v2 * 10 + nums[j];
                long sum = v1 + v2;
                long prev = v2;
                long level = 0;
                int index = j + 1;
                while (index < len) {
                    long s = sum;
                    level = (long) Math.pow(10, (int) Math.log10(sum));
                    while (level > 0 && index < len && nums[index] == s / level) {
                        index++;
                        s %= level;
                        level /= 10;
                    }
                    if (level != 0) {
                        break;
                    }
                    sum = prev + sum;
                    prev = sum - prev;
                }
                if (index == len && level == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create("112358").expect(true);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create("199100199").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("199100199").expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("111").expect(false);

    @TestData
    public DataExpectation normal3 = DataExpectation.create("101").expect(true);

    @TestData
    public DataExpectation normal4 = DataExpectation.create("1203").expect(false);

    @TestData
    public DataExpectation normal5 = DataExpectation.create("000").expect(true);

    @TestData
    public DataExpectation normal6 = DataExpectation.create("121474836472147483648").expect(true);

}
