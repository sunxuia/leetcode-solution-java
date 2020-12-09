package q1450;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1405. Longest Happy String
 * https://leetcode.com/problems/longest-happy-string/
 *
 * A string is called happy if it does not have any of the strings 'aaa', 'bbb' or 'ccc' as a substring.
 *
 * Given three integers a, b and c, return any string s, which satisfies following conditions:
 *
 * s is happy and longest possible.
 * s contains at most a occurrences of the letter 'a', at most b occurrences of the letter 'b' and at most c occurrences
 * of the letter 'c'.
 * s will only contain 'a', 'b' and 'c' letters.
 *
 * If there is no such string s return the empty string "".
 *
 * Example 1:
 *
 * Input: a = 1, b = 1, c = 7
 * Output: "ccaccbcc"
 * Explanation: "ccbccacc" would also be a correct answer.
 *
 * Example 2:
 *
 * Input: a = 2, b = 2, c = 1
 * Output: "aabbc"
 *
 * Example 3:
 *
 * Input: a = 7, b = 1, c = 0
 * Output: "aabaa"
 * Explanation: It's the only correct answer in this case.
 *
 * Constraints:
 *
 * 0 <= a, b, c <= 100
 * a + b + c > 0
 */
@RunWith(LeetCodeRunner.class)
public class Q1405_LongestHappyString {

    @Answer
    public String longestDiverseString(int a, int b, int c) {
        int[] counts = new int[]{a, b, c};
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        while (true) {
            int max = maxIdx(counts, -1);
            int prev1 = sb.charAt(sb.length() - 1) - 'a';
            int prev2 = sb.charAt(sb.length() - 2) - 'a';
            if (prev1 == max && prev2 == max) {
                max = maxIdx(counts, max);
            }
            if (counts[max] > 0) {
                counts[max]--;
                sb.append((char) (max + 'a'));
            } else {
                break;
            }
        }
        return sb.substring(2);
    }

    private int maxIdx(int[] counts, int ignore) {
        int max = 0;
        for (int i = 0; i < counts.length; i++) {
            if (i != ignore) {
                max = Math.max(max, counts[i]);
            }
        }
        for (int i = 0; i < counts.length; i++) {
            if (i != ignore && max == counts[i]) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = createTestData(1, 1, 7, "ccaccbcc");

    private DataExpectation createTestData(int a, int b, int c, String example) {
        return DataExpectation.createWith(a, b, c)
                .assertResult((String res) -> {
                    Assert.assertNotNull(res);
                    Assert.assertFalse(res.contains("aaa"));
                    Assert.assertFalse(res.contains("bbb"));
                    Assert.assertFalse(res.contains("ccc"));
                    Assert.assertEquals(res.length(), example.length());
                    int[] counts = new int[3];
                    for (int i = 0; i < res.length(); i++) {
                        counts[res.charAt(i) - 'a']++;
                    }
                    Assert.assertTrue(a >= counts[0]);
                    Assert.assertTrue(b >= counts[1]);
                    Assert.assertTrue(c >= counts[2]);
                });
    }

    @TestData
    public DataExpectation example2 = createTestData(2, 2, 1, "aabbc");

    @TestData
    public DataExpectation example3 = createTestData(7, 1, 0, "aabaa");

}
