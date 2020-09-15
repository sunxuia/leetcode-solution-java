package q1100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1071. Greatest Common Divisor of Strings
 * https://leetcode.com/problems/greatest-common-divisor-of-strings/
 *
 * For two strings s and t, we say "t divides s" if and only if s = t + ... + t  (t concatenated with itself 1 or more
 * times)
 *
 * Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.
 *
 * Example 1:
 * Input: str1 = "ABCABC", str2 = "ABC"
 * Output: "ABC"
 * Example 2:
 * Input: str1 = "ABABAB", str2 = "ABAB"
 * Output: "AB"
 * Example 3:
 * Input: str1 = "LEET", str2 = "CODE"
 * Output: ""
 * Example 4:
 * Input: str1 = "ABCDEF", str2 = "ABC"
 * Output: ""
 *
 * Constraints:
 *
 * 1 <= str1.length <= 1000
 * 1 <= str2.length <= 1000
 * str1 and str2 consist of English uppercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1071_GreatestCommonDivisorOfStrings {

    @Answer
    public String gcdOfStrings(String str1, String str2) {
        final int m = str1.length(), n = str2.length();
        loop:
        for (int len = Math.min(m, n); len > 0; len--) {
            if (m % len == 0 && n % len == 0) {
                String str = str1.substring(0, len);
                for (int j = len; j < m; j += len) {
                    if (!str1.startsWith(str, j)) {
                        continue loop;
                    }
                }
                for (int j = 0; j < n; j += len) {
                    if (!str2.startsWith(str, j)) {
                        continue loop;
                    }
                }
                return str;
            }
        }
        return "";
    }

    /**
     * LeetCode 上比较快的解法. 利用了类似辗转相除法的方式.
     */
    @Answer
    public String gcdOfStrings2(String str1, String str2) {
        final int m = str1.length(), n = str2.length();
        if (m == 0) {
            return str2;
        }
        if (m < n) {
            return gcdOfStrings2(str2, str1);
        }
        if (!(str1.substring(0, n).equals(str2))) {
            return "";
        }
        return gcdOfStrings2(str1.substring(n), str2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ABCABC", "ABC").expect("ABC");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ABABAB", "ABAB").expect("AB");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("LEET", "CODE").expect("");

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("ABCDEF", "ABC").expect("");

}
