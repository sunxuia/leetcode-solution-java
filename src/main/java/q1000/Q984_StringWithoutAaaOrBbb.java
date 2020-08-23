package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 984. String Without AAA or BBB
 * https://leetcode.com/problems/string-without-aaa-or-bbb/
 *
 * Given two integers A and B, return any string S such that:
 *
 * S has length A + B and contains exactly A 'a' letters, and exactly B 'b' letters;
 * The substring 'aaa' does not occur in S;
 * The substring 'bbb' does not occur in S.
 *
 * Example 1:
 *
 * Input: A = 1, B = 2
 * Output: "abb"
 * Explanation: "abb", "bab" and "bba" are all correct answers.
 *
 * Example 2:
 *
 * Input: A = 4, B = 1
 * Output: "aabaa"
 *
 * Note:
 *
 * 0 <= A <= 100
 * 0 <= B <= 100
 * It is guaranteed such an S exists for the given A and B.
 */
@RunWith(LeetCodeRunner.class)
public class Q984_StringWithoutAaaOrBbb {

    @Answer
    public String strWithout3a3b(int A, int B) {
        StringBuilder sb = new StringBuilder(A + B);
        char prev2 = ' ', prev1 = ' ';
        while (A > 0 || B > 0) {
            char c;
            if (A > B && (prev2 != 'a' || prev1 != 'a')
                    || prev2 == 'b' && prev1 == 'b') {
                c = 'a';
                A--;
            } else {
                c = 'b';
                B--;
            }
            sb.append(c);
            prev2 = prev1;
            prev1 = c;
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(1)
            .addArgument(2)
            .expect("abb").orExpect("bab").orExpect("bba")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 1).expect("aabaa");

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(1)
            .addArgument(3)
            .expect("bbab").orExpect("babb")
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(1, 4).expect("bbabb");

}
