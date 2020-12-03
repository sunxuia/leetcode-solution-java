package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1358. Number of Substrings Containing All Three Characters
 * https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/
 *
 * Given a string s consisting only of characters a, b and c.
 *
 * Return the number of substrings containing at least one occurrence of all these characters a, b and c.
 *
 * Example 1:
 *
 * Input: s = "abcabc"
 * Output: 10
 * Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca",
 * "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).
 *
 * Example 2:
 *
 * Input: s = "aaacb"
 * Output: 3
 * Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and
 * "acb".
 *
 * Example 3:
 *
 * Input: s = "abc"
 * Output: 1
 *
 * Constraints:
 *
 * 3 <= s.length <= 5 x 10^4
 * s only consists of a, b or c characters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1358_NumberOfSubstringsContainingAllThreeCharacters {

    @Answer
    public int numberOfSubstrings(String s) {
        final int n = s.length();
        int res = 0;
        int cnts[] = new int[3];
        // s[i, j) 是以i 开始的包含abc 3 个字符的最短子字符串
        for (int i = 0, j = 0; ; i++) {
            while (j < n && (cnts[0] == 0 || cnts[1] == 0 || cnts[2] == 0)) {
                cnts[s.charAt(j) - 'a']++;
                j++;
            }
            if (cnts[0] == 0 || cnts[1] == 0 || cnts[2] == 0) {
                break;
            }
            res += n + 1 - j;
            cnts[s.charAt(i) - 'a']--;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abcabc").expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aaacb").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create("abc").expect(1);

}
