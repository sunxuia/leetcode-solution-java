package q1750;

import org.junit.runner.RunWith;
import q1300.Q1278_PalindromePartitioningIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1745. Palindrome Partitioning IV
 * https://leetcode.com/problems/palindrome-partitioning-iv/
 *
 * Given a string s, return true if it is possible to split the string s into three non-empty palindromic substrings.
 * Otherwise, return false.?????
 *
 * A string is said to be palindrome if it the same string when reversed.
 *
 * Example 1:
 *
 * Input: s = "abcbdd"
 * Output: true
 * Explanation: "abcbdd" = "a" + "bcb" + "dd", and all three substrings are palindromes.
 *
 * Example 2:
 *
 * Input: s = "bcbddxy"
 * Output: false
 * Explanation: s cannot be split into 3 palindromes.
 *
 * Constraints:
 *
 * 3 <= s.length <= 2000
 * s consists only of lowercase English letters.
 *
 * 上一题 {@link Q1278_PalindromePartitioningIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1745_PalindromePartitioningIV {

    @Answer
    public boolean checkPartitioning(String s) {
        final int n = s.length();
        final char[] cs = s.toCharArray();
        boolean[][] palidromes = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int st = i, ed = i;
                    0 <= st && ed < n && cs[st] == cs[ed];
                    st--, ed++) {
                palidromes[st][ed] = true;
            }
            for (int st = i, ed = i + 1;
                    0 <= st && ed < n && cs[st] == cs[ed];
                    st--, ed++) {
                palidromes[st][ed] = true;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (palidromes[0][i]
                        && palidromes[i + 1][j]
                        && palidromes[j + 1][n - 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abcbdd").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("bcbddxy").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("juchzcedhfesefhdeczhcujzzvbmoeombv").expect(true);

}
