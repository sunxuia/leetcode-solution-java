package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1342. Number of Steps to Reduce a Number to Zero
 * https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/
 *
 * Given a non-negative integer num, return the number of steps to reduce it to zero. If the current number is even, you
 * have to divide it by 2, otherwise, you have to subtract 1 from it.
 *
 * Example 1:
 *
 * Input: num = 14
 * Output: 6
 * Explanation:
 * Step 1) 14 is even; divide by 2 and obtain 7.
 * Step 2) 7 is odd; subtract 1 and obtain 6.
 * Step 3) 6 is even; divide by 2 and obtain 3.
 * Step 4) 3 is odd; subtract 1 and obtain 2.
 * Step 5) 2 is even; divide by 2 and obtain 1.
 * Step 6) 1 is odd; subtract 1 and obtain 0.
 *
 * Example 2:
 *
 * Input: num = 8
 * Output: 4
 * Explanation:
 * Step 1) 8 is even; divide by 2 and obtain 4.
 * Step 2) 4 is even; divide by 2 and obtain 2.
 * Step 3) 2 is even; divide by 2 and obtain 1.
 * Step 4) 1 is odd; subtract 1 and obtain 0.
 *
 * Example 3:
 *
 * Input: num = 123
 * Output: 12
 *
 * Constraints:
 *
 * 0 <= num <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1342_NumberOfStepsToReduceANumberToZero {

    @Answer
    public int numberOfSteps(int num) {
        int res = 0;
        while (num > 0) {
            num = num % 2 == 0 ? num / 2 : num - 1;
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(14).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(8).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(123).expect(12);

}
