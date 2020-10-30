package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1247. Minimum Swaps to Make Strings Equal
 * https://leetcode.com/problems/minimum-swaps-to-make-strings-equal/
 *
 * You are given two strings s1 and s2 of equal length consisting of letters "x" and "y" only. Your task is to make
 * these two strings equal to each other. You can swap any two characters that belong to different strings, which means:
 * swap s1[i] and s2[j].
 *
 * Return the minimum number of swaps required to make s1 and s2 equal, or return -1 if it is impossible to do so.
 *
 * Example 1:
 *
 * Input: s1 = "xx", s2 = "yy"
 * Output: 1
 * Explanation:
 * Swap s1[0] and s2[1], s1 = "yx", s2 = "yx".
 *
 * Example 2:
 *
 * Input: s1 = "xy", s2 = "yx"
 * Output: 2
 * Explanation:
 * Swap s1[0] and s2[0], s1 = "yy", s2 = "xx".
 * Swap s1[0] and s2[1], s1 = "xy", s2 = "xy".
 * Note that you can't swap s1[0] and s1[1] to make s1 equal to "yx", cause we can only swap chars in different
 * strings.
 *
 * Example 3:
 *
 * Input: s1 = "xx", s2 = "xy"
 * Output: -1
 *
 * Example 4:
 *
 * Input: s1 = "xxyyxyxyxx", s2 = "xyyxyxxxyx"
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= s1.length, s2.length <= 1000
 * s1, s2 only contain 'x' or 'y'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1247_MinimumSwapsToMakeStringsEqual {

    @Answer
    public int minimumSwap(String s1, String s2) {
        final int n = s1.length();
        int xy = 0, yx = 0;
        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) == 'x') {
                if (s2.charAt(i) == 'y') {
                    xy++;
                }
            } else {
                if (s2.charAt(i) == 'x') {
                    yx++;
                }
            }
        }
        if ((xy + yx) % 2 == 1) {
            // 单数无法分配
            return -1;
        }
        // xx<->yy 的情况 + xy<->yx 的情况
        return xy / 2 + yx / 2 + xy % 2 * 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("xx", "yy").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("xy", "yx").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("xx", "xy").expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("xxyyxyxyxx", "xyyxyxxxyx").expect(4);

}
