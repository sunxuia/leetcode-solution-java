package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/magical-string/
 *
 * A magical string S consists of only '1' and '2' and obeys the following rules:
 *
 * The string S is magical because concatenating the number of contiguous occurrences of characters '1' and '2'
 * generates the string S itself.
 *
 * The first few elements of string S is the following: S = "1221121221221121122……"
 *
 * If we group the consecutive '1's and '2's in S, it will be:
 *
 * 1 22 11 2 1 22 1 22 11 2 11 22 ......
 *
 * and the occurrences of '1's or '2's in each group are:
 *
 * 1 2 2 1 1 2 1 2 2 1 2 2 ......
 *
 * You can see that the occurrence sequence above is the S itself.
 *
 * Given an integer N as input, return the number of '1's in the first N number in the magical string S.
 *
 * Note: N will not exceed 100,000.
 *
 * Example 1:
 *
 * Input: 6
 * Output: 3
 * Explanation: The first 6 elements of magical string S is "12211" and it contains three 1's, so return 3.
 */
@RunWith(LeetCodeRunner.class)
public class Q481_MagicalString {

    // https://www.cnblogs.com/grandyang/p/6286540.html
    // 难点在于找到字符串的生成规律
    @Answer
    public int magicalString(int n) {
        StringBuilder sb = new StringBuilder("122");
        for (int i = 2; sb.length() < n; i++) {
            char back = sb.charAt(sb.length() - 1);
            for (int j = sb.charAt(i) - '0'; j > 0; j--) {
                sb.append((char) (back ^ 3));
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (sb.charAt(i) == '1') {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(6).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(4).expect(2);

}
