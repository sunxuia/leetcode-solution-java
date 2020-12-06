package q1400;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1374. Generate a String With Characters That Have Odd Counts
 * https://leetcode.com/problems/generate-a-string-with-characters-that-have-odd-counts/
 *
 * Given an integer n, return a string with n characters such that each character in such string occurs an odd number of
 * times.
 *
 * The returned string must contain only lowercase English letters. If there are multiples valid strings, return any of
 * them.
 *
 * Example 1:
 *
 * Input: n = 4
 * Output: "pppz"
 * Explanation: "pppz" is a valid string since the character 'p' occurs three times and the character 'z' occurs once.
 * Note that there are many other valid strings such as "ohhh" and "love".
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: "xy"
 * Explanation: "xy" is a valid string since the characters 'x' and 'y' occur once. Note that there are many other valid
 * strings such as "ag" and "ur".
 *
 * Example 3:
 *
 * Input: n = 7
 * Output: "holasss"
 *
 * Constraints:
 *
 * 1 <= n <= 500
 */
@RunWith(LeetCodeRunner.class)
public class Q1374_GenerateAStringWithCharactersThatHaveOddCounts {

    @Answer
    public String generateTheString(int n) {
        char[] cs = new char[n];
        Arrays.fill(cs, 'a');
        if (n % 2 == 0) {
            cs[n - 1] = 'b';
        }
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = createTestData(4);

    private DataExpectation createTestData(int n) {
        return DataExpectation.builder()
                .addArgument(n)
                .assertMethod((String res) -> {
                    Assert.assertEquals(n, res.length());
                    int[] times = new int[26];
                    for (int i = 0; i < n; i++) {
                        times[res.charAt(i) - 'a']++;
                    }
                    for (int i = 0; i < 26; i++) {
                        if (times[i] > 0) {
                            Assert.assertEquals(1, times[i] % 2);
                        }
                    }
                }).build();
    }

    @TestData
    public DataExpectation example2 = createTestData(2);

    @TestData
    public DataExpectation example3 = createTestData(7);

}
