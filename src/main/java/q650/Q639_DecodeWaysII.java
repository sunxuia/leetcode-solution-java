package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/decode-ways-ii/
 *
 * A message containing letters from A-Z is being encoded to numbers using the following mapping way:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 *
 * Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers
 * from 1 to 9.
 *
 * Given the encoded message containing digits and the character '*', return the total number of ways to decode it.
 *
 * Also, since the answer may be very large, you should return the output mod 109 + 7.
 *
 * Example 1:
 *
 * Input: "*"
 * Output: 9
 * Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".
 *
 * Example 2:
 *
 * Input: "1*"
 * Output: 9 + 9 = 18
 *
 * Note:
 *
 * The length of the input string will fit in range [1, 10^5].
 * The input string will only contain the character '*' and digits '0' - '9'.
 */
@RunWith(LeetCodeRunner.class)
public class Q639_DecodeWaysII {

    // 正常的dfs 会超时, 带缓存的可以通过但是本地调试会出现方法栈溢出的情况(递归方式)
    // 因此使用 DP 方式的解答
    @Answer
    public int numDecodings(String s) {
        int next = 1;
        long val1 = 1, val2 = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int curr = s.charAt(i) - '0';
            long val;
            if (curr < 0) {
                if (next < 0) {
                    val = 9 * val1 + 15 * val2;
                } else if (next <= 6) {
                    val = 9 * val1 + 2 * val2;
                } else {
                    val = 9 * val1 + val2;
                }
            } else if (curr == 0) {
                val = 0;
            } else if (curr == 1) {
                if (next < 0) {
                    val = val1 + 9 * val2;
                } else {
                    val = val1 + val2;
                }
            } else if (curr == 2) {
                if (next < 0) {
                    val = val1 + 6 * val2;
                } else if (next <= 6) {
                    val = val1 + val2;
                } else {
                    val = val1;
                }
            } else {
                val = val1;
            }
            val2 = val1;
            val1 = val % 10_0000_0007;
            next = curr;
        }
        return (int) val1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("*").expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.create("1*").expect(18);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("3*").expect(9);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("**").expect(96);

    @TestData
    public DataExpectation normal3 = DataExpectation.create("*1").expect(11);

    @TestData
    public DataExpectation normal4 = DataExpectation.create("*1*").expect(180);

    @TestData
    public DataExpectation normal5 = DataExpectation.create("904").expect(0);

    @TestData
    public DataExpectation normal6 = DataExpectation.create("1*72*").expect(285);

    // 这个结果是 % 10_0000_0007 之后得到的结果
    @TestData
    public DataExpectation normal7 = DataExpectation.create("*********").expect(291868912);

    @TestData
    public DataExpectation normal8 = DataExpectation.create("********************").expect(104671669);

    @TestData
    public DataExpectation normal9 = DataExpectation.create("*1*1*0").expect(404);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readString("Q639_TestData")).expect(424827479);

}
