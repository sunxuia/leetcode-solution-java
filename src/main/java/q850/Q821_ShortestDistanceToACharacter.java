package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shortest-distance-to-a-character/
 *
 * Given a string S and a character C, return an array of integers representing the shortest distance from the
 * character C in the string.
 *
 * Example 1:
 *
 * Input: S = "loveleetcode", C = 'e'
 * Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
 *
 *
 *
 * Note:
 *
 * S string length is in [1, 10000].
 * C is a single character, and guaranteed to be in string S.
 * All letters in S and C are lowercase.
 */
@RunWith(LeetCodeRunner.class)
public class Q821_ShortestDistanceToACharacter {

    @Answer
    public int[] shortestToChar(String S, char C) {
        final int n = S.length();
        char[] sc = S.toCharArray();
        int[] res = new int[n];

        // 从左到右寻找最小距离
        int pos = -n;
        for (int i = 0; i < n; i++) {
            if (sc[i] == C) {
                pos = i;
            }
            res[i] = i - pos;
        }

        // 从右到左寻找最小距离
        pos = 2 * n;
        for (int i = n - 1; i >= 0; i--) {
            if (sc[i] == C) {
                pos = i;
            }
            res[i] = Math.min(res[i], pos - i);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("loveleetcode", 'e')
            .expect(new int[]{3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0});

}
