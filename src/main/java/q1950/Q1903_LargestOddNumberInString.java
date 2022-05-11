package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1903. Largest Odd Number in String
 * https://leetcode.com/problems/largest-odd-number-in-string/
 *
 * You are given a string num, representing a large integer. Return the largest-valued odd integer (as a string) that is
 * a non-empty substring of num, or an empty string "" if no odd integer exists.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: num = "52"
 * Output: "5"
 * Explanation: The only non-empty substrings are "5", "2", and "52". "5" is the only odd number.
 *
 * Example 2:
 *
 * Input: num = "4206"
 * Output: ""
 * Explanation: There are no odd numbers in "4206".
 *
 * Example 3:
 *
 * Input: num = "35427"
 * Output: "35427"
 * Explanation: "35427" is already an odd number.
 *
 * Constraints:
 *
 * 1 <= num.length <= 10^5
 * num only consists of digits and does not contain any leading zeros.
 */
@RunWith(LeetCodeRunner.class)
public class Q1903_LargestOddNumberInString {

    @Answer
    public String largestOddNumber(String num) {
        int end = num.length() - 1;
        while (end >= 0 && (num.charAt(end) - '0' & 1) == 0) {
            end--;
        }
        if (end == -1) {
            return "";
        } else {
            return num.substring(0, end + 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("52").expect("5");

    @TestData
    public DataExpectation example2 = DataExpectation.create("4206").expect("");

    @TestData
    public DataExpectation example3 = DataExpectation.create("35427").expect("35427");

}
