package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1864. Minimum Number of Swaps to Make the Binary String Alternating
 * https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-binary-string-alternating/
 *
 * Given a binary string s, return the minimum number of character swaps to make it alternating, or -1 if it is
 * impossible.
 *
 * The string is called alternating if no two adjacent characters are equal. For example, the strings "010" and "1010"
 * are alternating, while the string "0100" is not.
 *
 * Any two characters may be swapped, even if they are not adjacent.
 *
 * Example 1:
 *
 * Input: s = "111000"
 * Output: 1
 * Explanation: Swap positions 1 and 4: "111000" -> "101010"
 * The string is now alternating.
 *
 * Example 2:
 *
 * Input: s = "010"
 * Output: 0
 * Explanation: The string is already alternating, no swaps are needed.
 *
 * Example 3:
 *
 * Input: s = "1110"
 * Output: -1
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s[i] is either '0' or '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1864_MinimumNumberOfSwapsToMakeTheBinaryStringAlternating {

    @Answer
    public int minSwaps(String s) {
        final char[] cs = s.toCharArray();
        // counts[0][0] : 0 在偶数位的数量;
        // counts[0][1] : 0 在奇数位的数量;
        // counts[1][0] : 1 在偶数位的数量;
        // counts[1][1] : 1 在奇数位的数量;
        int[][] counts = new int[2][2];
        for (int i = 0; i < cs.length; i++) {
            counts[cs[i] - '0'][i & 1]++;
        }
        int zero = counts[0][0] + counts[0][1];
        int one = counts[1][0] + counts[1][1];
        // 0和1 的数量差距超过1 则不能组成
        if (Math.abs(zero - one) > 1) {
            return -1;
        }
        if (zero > one) {
            // 01010 这样的, 偶数位的1 要移动到奇数位
            return counts[1][0];
        } else if (zero < one) {
            // 10101 这样的, 偶数位的0 要移动到奇数位
            return counts[0][0];
        } else {
            // 0101或1010 这样的, 选择移动数量少的
            return Math.min(counts[0][0], counts[0][1]);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("111000").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("010").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("1110").expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("0010111").expect(1);

}
