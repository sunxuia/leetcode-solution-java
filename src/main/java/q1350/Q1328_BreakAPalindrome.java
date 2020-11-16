package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1328. Break a Palindrome
 * https://leetcode.com/problems/break-a-palindrome/
 *
 * Given a palindromic string palindrome, replace exactly one character by any lowercase English letter so that the
 * string becomes the lexicographically smallest possible string that isn't a palindrome.
 *
 * After doing so, return the final string.  If there is no way to do so, return the empty string.
 *
 * Example 1:
 *
 * Input: palindrome = "abccba"
 * Output: "aaccba"
 *
 * Example 2:
 *
 * Input: palindrome = "a"
 * Output: ""
 *
 * Constraints:
 *
 * 1 <= palindrome.length <= 1000
 * palindrome consists of only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1328_BreakAPalindrome {

    @Answer
    public String breakPalindrome(String palindrome) {
        final int n = palindrome.length();
        if (n < 2) {
            return "";
        }
        char[] sc = palindrome.toCharArray();
        for (int i = 0; i < n; i++) {
            if (sc[i] == 'a' || n % 2 == 1 && i == n / 2) {
                continue;
            }
            sc[i] = 'a';
            return new String(sc);
        }
        sc[n - 1] = 'b';
        return new String(sc);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abccba").expect("aaccba");

    @TestData
    public DataExpectation example2 = DataExpectation.create("a").expect("");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("aa").expect("ab");

}
