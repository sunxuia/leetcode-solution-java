package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/plus-one/
 *
 * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
 *
 * The digits are stored such that the most significant digit is at the head of the list, and each element in the
 * array contain a single digit.
 *
 * You may assume the integer does not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 * Example 2:
 *
 * Input: [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 */
@RunWith(LeetCodeRunner.class)
public class Q066_PlusOne {

    @Answer
    public int[] plusOne(int[] digits) {
        int carry = 1;
        for (int i = digits.length - 1; carry == 1 && i >= 0; i--) {
            digits[i] += carry;
            carry = digits[i] / 10;
            digits[i] = digits[i] % 10;
        }
        if (carry == 1) {
            int[] temp = new int[digits.length + 1];
            temp[0] = 1;
            System.arraycopy(digits, 0, temp, 1, digits.length);
            digits = temp;
        }
        return digits;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3}).expect(new int[]{1, 2, 4});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 3, 2, 1}).expect(new int[]{4, 3, 2, 2});

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{}).expect(new int[]{1});

    @TestData
    public DataExpectation border2 = DataExpectation.create(new int[]{9}).expect(new int[]{1, 0});
}
