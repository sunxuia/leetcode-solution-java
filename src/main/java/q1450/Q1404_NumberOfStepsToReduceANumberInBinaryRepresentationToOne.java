package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1404. Number of Steps to Reduce a Number in Binary Representation to One
 * https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/
 *
 * Given a number s in their binary representation. Return the number of steps to reduce it to 1 under the following
 * rules:
 *
 * If the current number is even, you have to divide it by 2.
 *
 *
 * If the current number is odd, you have to add 1 to it.
 *
 * It's guaranteed that you can always reach to one for all testcases.
 *
 * Example 1:
 *
 * Input: s = "1101"
 * Output: 6
 * Explanation: "1101" corressponds to number 13 in their decimal representation.
 * Step 1) 13 is odd, add 1 and obtain 14.
 * Step 2) 14 is even, divide by 2 and obtain 7.
 * Step 3) 7 is odd, add 1 and obtain 8.
 * Step 4) 8 is even, divide by 2 and obtain 4.
 * Step 5) 4 is even, divide by 2 and obtain 2.
 * Step 6) 2 is even, divide by 2 and obtain 1.
 *
 * Example 2:
 *
 * Input: s = "10"
 * Output: 1
 * Explanation: "10" corressponds to number 2 in their decimal representation.
 * Step 1) 2 is even, divide by 2 and obtain 1.
 *
 * Example 3:
 *
 * Input: s = "1"
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * s consists of characters '0' or '1'
 * s[0] == '1'
 */
@RunWith(LeetCodeRunner.class)
public class Q1404_NumberOfStepsToReduceANumberInBinaryRepresentationToOne {

    @Answer
    public int numSteps(String s) {
        int steps = 0, carry = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            int sum = s.charAt(i) - '0' + carry;
            if (sum % 2 == 0) {
                // ÷2 操作
                steps++;
                carry = sum / 2;
            } else {
                // +1 操作
                steps++;
                carry++;
                i++;
            }
        }
        // 最后剩的数字如果是1 则满足条件, 如果是2 则要再÷2,
        // 因为s 没有前导0 所以sum 不可能是0.
        int sum = s.charAt(0) - '0' + carry;
        return sum == 1 ? steps : steps + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1101").expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create("10").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("1").expect(0);

}
