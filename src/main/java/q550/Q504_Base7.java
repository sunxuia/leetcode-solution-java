package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/base-7/
 *
 * Given an integer, return its base 7 string representation.
 *
 * Example 1:
 *
 * Input: 100
 * Output: "202"
 *
 * Example 2:
 *
 * Input: -7
 * Output: "-10"
 *
 * Note: The input will be in range of [-1e7, 1e7].
 */
@RunWith(LeetCodeRunner.class)
public class Q504_Base7 {

    @Answer
    public String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = Math.abs(num); i > 0; i /= 7) {
            sb.append(i % 7);
        }
        if (num < 0) {
            sb.append('-');
        }
        return sb.reverse().toString();
    }

    // LeetCode 上最快的解法
    @Answer
    public String convertToBase7_LeetCode(int num) {
        return Integer.toString(num, 7);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(100).expect("202");

    @TestData
    public DataExpectation example2 = DataExpectation.create(-7).expect("-10");

}
