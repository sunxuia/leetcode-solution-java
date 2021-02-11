package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1736. Latest Time by Replacing Hidden Digits
 * https://leetcode.com/problems/latest-time-by-replacing-hidden-digits/
 *
 * You are given a string time in the form of  hh:mm, where some of the digits in the string are hidden (represented by
 * ?).
 *
 * The valid times are those inclusively between 00:00 and 23:59.
 *
 * Return the latest valid time you can get from time by replacing the hidden digits.
 *
 * Example 1:
 *
 * Input: time = "2?:?0"
 * Output: "23:50"
 * Explanation: The latest hour beginning with the digit '2' is 23 and the latest minute ending with the digit '0' is
 * 50.
 *
 * Example 2:
 *
 * Input: time = "0?:3?"
 * Output: "09:39"
 *
 * Example 3:
 *
 * Input: time = "1?:22"
 * Output: "19:22"
 *
 * Constraints:
 *
 * time is in the format hh:mm.
 * It is guaranteed that you can produce a valid time from the given string.
 */
@RunWith(LeetCodeRunner.class)
public class Q1736_LatestTimeByReplacingHiddenDigits {

    @Answer
    public String maximumTime(String time) {
        char[] cs = time.toCharArray();
        if (cs[0] == '?') {
            if (cs[1] == '?' || cs[1] <= '3') {
                cs[0] = '2';
            } else {
                cs[0] = '1';
            }
        }
        if (cs[1] == '?') {
            if (cs[0] == '2') {
                cs[1] = '3';
            } else {
                cs[1] = '9';
            }
        }
        if (cs[3] == '?') {
            cs[3] = '5';
        }
        if (cs[4] == '?') {
            cs[4] = '9';
        }
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("2?:?0").expect("23:50");

    @TestData
    public DataExpectation example2 = DataExpectation.create("0?:3?").expect("09:39");

    @TestData
    public DataExpectation example3 = DataExpectation.create("1?:22").expect("19:22");

}
