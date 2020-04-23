package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/license-key-formatting/
 *
 * You are given a license key represented as a string S which consists only alphanumeric character and dashes. The
 * string is separated into N+1 groups by N dashes.
 *
 * Given a number K, we would want to reformat the strings such that each group contains exactly K characters, except
 * for the first group which could be shorter than K, but still must contain at least one character. Furthermore,
 * there must be a dash inserted between two groups and all lowercase letters should be converted to uppercase.
 *
 * Given a non-empty string S and a number K, format the string according to the rules described above.
 *
 * Example 1:
 *
 * Input: S = "5F3Z-2e-9-w", K = 4
 *
 * Output: "5F3Z-2E9W"
 *
 * Explanation: The string S has been split into two parts, each part has 4 characters.
 * Note that the two extra dashes are not needed and can be removed.
 *
 * Example 2:
 *
 * Input: S = "2-5g-3-J", K = 2
 *
 * Output: "2-5G-3J"
 *
 * Explanation: The string S has been split into three parts, each part has 2 characters except the first part as it
 * could be shorter as mentioned above.
 *
 * Note:
 *
 * The length of string S will not exceed 12,000, and K is a positive integer.
 * String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
 * String S is non-empty.
 */
@RunWith(LeetCodeRunner.class)
public class Q482_LicenseKeyFormatting {

    @Answer
    public String licenseKeyFormatting(String S, int K) {
        StringBuilder sb = new StringBuilder();
        for (int i = S.length() - 1; i >= 0; i--) {
            char c = Character.toUpperCase(S.charAt(i));
            if (c == '-') {
                continue;
            }
            sb.append(c);
            if ((sb.length() + 1) % (K + 1) == 0) {
                sb.append('-');
            }
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '-') {
            sb.setLength(sb.length() - 1);
        }
        return sb.reverse().toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("5F3Z-2e-9-w", 4).expect("5F3Z-2E9W");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("2-5g-3-J", 2).expect("2-5G-3J");

}
