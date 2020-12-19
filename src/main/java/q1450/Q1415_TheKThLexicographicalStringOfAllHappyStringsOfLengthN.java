package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1415. The k-th Lexicographical String of All Happy Strings of Length n
 * https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/
 *
 * A happy string is a string that:
 *
 * consists only of letters of the set ['a', 'b', 'c'].
 * s[i] != s[i + 1] for all values of i from 1 to s.length - 1 (string is 1-indexed).
 *
 * For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc" are
 * not happy strings.
 *
 * Given two integers n and k, consider a list of all happy strings of length n sorted in lexicographical order.
 *
 * Return the kth string of this list or return an empty string if there are less than k happy strings of length n.
 *
 * Example 1:
 *
 * Input: n = 1, k = 3
 * Output: "c"
 * Explanation: The list ["a", "b", "c"] contains all happy strings of length 1. The third string is "c".
 *
 * Example 2:
 *
 * Input: n = 1, k = 4
 * Output: ""
 * Explanation: There are only 3 happy strings of length 1.
 *
 * Example 3:
 *
 * Input: n = 3, k = 9
 * Output: "cab"
 * Explanation: There are 12 different happy string of length 3 ["aba", "abc", "aca", "acb", "bab", "bac", "bca", "bcb",
 * "cab", "cac", "cba", "cbc"]. You will find the 9th string = "cab"
 *
 * Example 4:
 *
 * Input: n = 2, k = 7
 * Output: ""
 *
 * Example 5:
 *
 * Input: n = 10, k = 100
 * Output: "abacbabacb"
 *
 * Constraints:
 *
 * 1 <= n <= 10
 * 1 <= k <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1415_TheKThLexicographicalStringOfAllHappyStringsOfLengthN {

    @Answer
    public String getHappyString(int n, int k) {
        char[] cs = new char[n];
        fill(cs, 0);
        for (int i = 1; i < k; i++) {
            if (!next(cs)) {
                return "";
            }
        }
        return new String(cs);
    }

    private void fill(char[] cs, int i) {
        int bit = i > 0 && cs[i - 1] == 'a' ? 1 : 0;
        for (int j = 0; i + j < cs.length; j++) {
            cs[i + j] = j % 2 == bit ? 'a' : 'b';
        }
    }

    private boolean next(char[] cs) {
        for (int i = cs.length - 1; i >= 0; i--) {
            cs[i]++;
            if (0 < i && cs[i - 1] == cs[i]) {
                cs[i]++;
            }
            if (cs[i] <= 'c') {
                fill(cs, i + 1);
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 3).expect("c");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(1, 4).expect("");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, 9).expect("cab");

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(2, 7).expect("");

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(10, 100).expect("abacbabacb");

}
