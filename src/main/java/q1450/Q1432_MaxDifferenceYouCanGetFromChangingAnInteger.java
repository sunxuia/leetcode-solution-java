package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1432. Max Difference You Can Get From Changing an Integer
 * https://leetcode.com/problems/max-difference-you-can-get-from-changing-an-integer/
 *
 * You are given an integer num. You will apply the following steps exactly two times:
 *
 * Pick a digit x (0 <= x <= 9).
 * Pick another digit y (0 <= y <= 9). The digit y can be equal to x.
 * Replace all the occurrences of x in the decimal representation of num by y.
 * The new integer cannot have any leading zeros, also the new integer cannot be 0.
 *
 * Let a and b be the results of applying the operations to num the first and second times, respectively.
 *
 * Return the max difference between a and b.
 *
 * Example 1:
 *
 * Input: num = 555
 * Output: 888
 * Explanation: The first time pick x = 5 and y = 9 and store the new integer in a.
 * The second time pick x = 5 and y = 1 and store the new integer in b.
 * We have now a = 999 and b = 111 and max difference = 888
 *
 * Example 2:
 *
 * Input: num = 9
 * Output: 8
 * Explanation: The first time pick x = 9 and y = 9 and store the new integer in a.
 * The second time pick x = 9 and y = 1 and store the new integer in b.
 * We have now a = 9 and b = 1 and max difference = 8
 *
 * Example 3:
 *
 * Input: num = 123456
 * Output: 820000
 *
 * Example 4:
 *
 * Input: num = 10000
 * Output: 80000
 *
 * Example 5:
 *
 * Input: num = 9288
 * Output: 8700
 *
 * Constraints:
 *
 * 1 <= num <= 10^8
 */
@RunWith(LeetCodeRunner.class)
public class Q1432_MaxDifferenceYouCanGetFromChangingAnInteger {

    @Answer
    public int maxDiff(int num) {
        int digit = 1;
        for (int val = num; val > 9; val /= 10) {
            digit *= 10;
        }
        return max(num, digit) - min(num, digit);
    }

    private int max(int num, int digit) {
        int res = 0, target = -1;
        while (digit > 0) {
            int val = num / digit % 10;
            if (target == -1 && val != 9) {
                target = val;
            }
            if (val == target) {
                val = 9;
            }
            res = res * 10 + val;
            digit /= 10;
        }
        return res;
    }

    private int min(int num, int digit) {
        int res = 0, limit = 0, target = -1, result = 0;
        if (num / digit % 10 == 1) {
            res = 1;
            digit /= 10;
            limit = 1;
        } else {
            target = num / digit % 10;
            result = 1;
        }
        while (digit > 0) {
            int val = num / digit % 10;
            if (target == -1 && val > limit) {
                target = val;
                result = 0;
            }
            if (val == target) {
                val = result;
            }
            res = res * 10 + val;
            digit /= 10;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(555).expect(888);

    @TestData
    public DataExpectation example2 = DataExpectation.create(9).expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create(123456).expect(820000);

    @TestData
    public DataExpectation example4 = DataExpectation.create(10000).expect(80000);

    @TestData
    public DataExpectation example5 = DataExpectation.create(9288).expect(8700);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(111).expect(888);

}
