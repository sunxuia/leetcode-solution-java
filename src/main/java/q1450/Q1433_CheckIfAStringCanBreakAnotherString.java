package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1433. Check If a String Can Break Another String
 * https://leetcode.com/problems/check-if-a-string-can-break-another-string/
 *
 * Given two strings: s1 and s2 with the same size, check if some permutation of string s1 can break some permutation of
 * string s2 or vice-versa. In other words s2 can break s1 or vice-versa.
 *
 * A string x can break string y (both of size n) if x[i] >= y[i] (in alphabetical order) for all i between 0 and n-1.
 *
 * Example 1:
 *
 * Input: s1 = "abc", s2 = "xya"
 * Output: true
 * Explanation: "ayx" is a permutation of s2="xya" which can break to string "abc" which is a permutation of s1="abc".
 *
 * Example 2:
 *
 * Input: s1 = "abe", s2 = "acd"
 * Output: false
 * Explanation: All permutations for s1="abe" are: "abe", "aeb", "bae", "bea", "eab" and "eba" and all permutation for
 * s2="acd" are: "acd", "adc", "cad", "cda", "dac" and "dca". However, there is not any permutation from s1 which can
 * break some permutation from s2 and vice-versa.
 *
 * Example 3:
 *
 * Input: s1 = "leetcodee", s2 = "interview"
 * Output: true
 *
 * Constraints:
 *
 * s1.length == n
 * s2.length == n
 * 1 <= n <= 10^5
 * All strings consist of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1433_CheckIfAStringCanBreakAnotherString {

    @Answer
    public boolean checkIfCanBreak(String s1, String s2) {
        final int n = s1.length();
        int[] counts1 = new int[26];
        int[] counts2 = new int[26];
        for (int i = 0; i < n; i++) {
            counts1[s1.charAt(i) - 'a']++;
            counts2[s2.charAt(i) - 'a']++;
        }
        return canBreak(counts1, counts2)
                || canBreak(counts2, counts1);
    }

    private boolean canBreak(int[] breaker, int[] breakable) {
        int c1 = 0, c2 = 0;
        for (int i = 0; c1 >= c2 && i < 26; i++) {
            c1 += breaker[i];
            c2 += breakable[i];
        }
        return c1 >= c2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abc", "xya").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abe", "acd").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("leetcodee", "interview").expect(true);

}
