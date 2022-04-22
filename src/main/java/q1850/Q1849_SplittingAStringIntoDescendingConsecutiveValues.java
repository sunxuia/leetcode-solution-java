package q1850;

import java.util.regex.Pattern;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DebugWith;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1849. Splitting a String Into Descending Consecutive Values
 * https://leetcode.com/problems/splitting-a-string-into-descending-consecutive-values/
 *
 * You are given a string s that consists of only digits.
 *
 * Check if we can split s into two or more non-empty substrings such that the numerical values of the substrings are in
 * descending order and the difference between numerical values of every two adjacent substrings is equal to 1.
 *
 * For example, the string s = "0090089" can be split into ["0090", "089"] with numerical values [90,89]. The values are
 * in descending order and adjacent values differ by 1, so this way is valid.
 * Another example, the string s = "001" can be split into ["0", "01"], ["00", "1"], or ["0", "0", "1"]. However all the
 * ways are invalid because they have numerical values [0,1], [0,1], and [0,0,1] respectively, all of which are not in
 * descending order.
 *
 * Return true if it is possible to split s as described above, or false otherwise.
 *
 * A substring is a contiguous sequence of characters in a string.
 *
 * Example 1:
 *
 * Input: s = "1234"
 * Output: false
 * Explanation: There is no valid way to split s.
 *
 * Example 2:
 *
 * Input: s = "050043"
 * Output: true
 * Explanation: s can be split into ["05", "004", "3"] with numerical values [5,4,3].
 * The values are in descending order with adjacent values differing by 1.
 *
 * Example 3:
 *
 * Input: s = "9080701"
 * Output: false
 * Explanation: There is no valid way to split s.
 *
 * Constraints:
 *
 * 1 <= s.length <= 20
 * s only consists of digits.
 */
@RunWith(LeetCodeRunner.class)
public class Q1849_SplittingAStringIntoDescendingConsecutiveValues {

    @Answer
    public boolean splitString(String s) {
        s = trimLeftZero(s);
        for (int i = 1; i < s.length(); i++) {
            if (match(s, i, s.substring(0, i))) {
                return true;
            }
        }
        return false;
    }

    private String trimLeftZero(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0') {
                return str.substring(i);
            }
        }
        return "";
    }

    private boolean match(String s, int start, String num) {
        if (start == s.length()) {
            return true;
        }
        if (isZero(num)) {
            return false;
        }
        String next = decrease(num);
        int zero = 0;
        while (start + zero < s.length()
                && s.charAt(start + zero) == '0') {
            zero++;
        }
        if ("0".equals(next) && zero > 0) {
            zero--;
        }
        for (int i = 0; i < next.length(); i++) {
            int index = start + zero + i;
            if (index == s.length() || s.charAt(index) != next.charAt(i)) {
                return false;
            }
        }
        return match(s, start + zero + next.length(), next);
    }

    private boolean isZero(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private String decrease(String str) {
        char[] cs = str.toCharArray();
        final int len = cs.length;
        int tail = len - 1;
        while (cs[tail] == '0') {
            cs[tail--] = '9';
        }
        cs[tail]--;
        String ret = new String(cs);
        if (isZero(ret)) {
            return "0";
        }
        return trimLeftZero(ret);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1234").expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create("050043").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create("9080701").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("001").expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("1000000000999999999").expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.create("1051546050").expect(false);

    @TestData
    public DataExpectation normal4 = DataExpectation.create("200100").expect(true);

    @TestData
    public DataExpectation normal5 = DataExpectation.create("43420").expect(false);

    /**
     * 20位数, 正好超过long 类型的范围
     */
    @TestData
    public DataExpectation digit20 = DataExpectation.create("94650723337775781477").expect(false);

}
