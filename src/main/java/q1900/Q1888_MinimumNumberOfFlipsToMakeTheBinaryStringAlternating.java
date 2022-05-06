package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1888. Minimum Number of Flips to Make the Binary String Alternating
 * https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/
 *
 * You are given a binary string s. You are allowed to perform two types of operations on the string in any sequence:
 *
 * Type-1: Remove the character at the start of the string s and append it to the end of the string.
 * Type-2: Pick any character in s and flip its value, i.e., if its value is '0' it becomes '1' and vice-versa.
 *
 * Return the minimum number of type-2 operations you need to perform such that s becomes alternating.
 *
 * The string is called alternating if no two adjacent characters are equal.
 *
 * For example, the strings "010" and "1010" are alternating, while the string "0100" is not.
 *
 * Example 1:
 *
 * Input: s = "111000"
 * Output: 2
 * Explanation: Use the first operation two times to make s = "100011".
 * Then, use the second operation on the third and sixth elements to make s = "101010".
 *
 * Example 2:
 *
 * Input: s = "010"
 * Output: 0
 * Explanation: The string is already alternating.
 *
 * Example 3:
 *
 * Input: s = "1110"
 * Output: 1
 * Explanation: Use the second operation on the second element to make s = "1010".
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s[i] is either '0' or '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1888_MinimumNumberOfFlipsToMakeTheBinaryStringAlternating {

    @Answer
    public int minFlips(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        // counts[0][0] : 在偶数位的0 的数量
        // counts[0][1] : 在偶数位的1 的数量
        // counts[1][0] : 在奇数位的0 的数量
        // counts[1][1] : 在奇数位的1 的数量
        // 则需要变更的位数就可以通过Type-2 操作将奇偶都变为01或10 的方式来计算得到
        int[][] counts = new int[2][2];
        for (int i = 0; i < n; i++) {
            counts[i & 1][cs[i] - '0']++;
        }
        // 如果位数为偶数, 则不论怎样Type-1 操作都不会改变相对应的数量.
        if (n % 2 == 0) {
            return Math.min(counts[0][1] + counts[1][0],
                    counts[0][0] + counts[1][1]);
        }

        int res = n;
        for (int i = 0; i < n; i++) {
            int flip = Math.min(counts[0][1] + counts[1][0],
                    counts[0][0] + counts[1][1]);
            res = Math.min(res, flip);

            // Type-1操作全部向左移动1 位
            int t = counts[0][0];
            counts[0][0] = counts[1][0];
            counts[1][0] = t;
            t = counts[0][1];
            counts[0][1] = counts[1][1];
            counts[1][1] = t;
            // 最开头的 cs[-1] 移动到 cs[n-1] 上
            counts[1][cs[i] - '0']--;
            counts[(n - 1) % 2][cs[i] - '0']++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("111000").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("010").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("1110").expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("01001001101").expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("10001100101000000").expect(5);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create("0001100010101000111101000110101111000000101100000001001")
            .expect(22);

}
