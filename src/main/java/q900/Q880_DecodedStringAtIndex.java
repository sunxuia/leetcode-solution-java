package q900;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 880. Decoded String at Index
 * https://leetcode.com/problems/decoded-string-at-index/
 *
 * An encoded string S is given.  To find and write the decoded string to a tape, the encoded string is read one
 * character at a time and the following steps are taken:
 *
 * If the character read is a letter, that letter is written onto the tape.
 * If the character read is a digit (say d), the entire current tape is repeatedly written d-1 more times in total.
 *
 * Now for some encoded string S, and an index K, find and return the K-th letter (1 indexed) in the decoded string.
 *
 * Example 1:
 *
 * Input: S = "leet2code3", K = 10
 * Output: "o"
 * Explanation:
 * The decoded string is "leetleetcodeleetleetcodeleetleetcode".
 * The 10th letter in the string is "o".
 *
 * Example 2:
 *
 * Input: S = "ha22", K = 5
 * Output: "h"
 * Explanation:
 * The decoded string is "hahahaha".  The 5th letter is "h".
 *
 * Example 3:
 *
 * Input: S = "a2345678999999999999999", K = 1
 * Output: "a"
 * Explanation:
 * The decoded string is "a" repeated 8301530446056247680 times.  The 1st letter is "a".
 *
 * Constraints:
 *
 * 2 <= S.length <= 100
 * S will only contain lowercase letters and digits 2 through 9.
 * S starts with a letter.
 * 1 <= K <= 10^9
 * It's guaranteed that K is less than or equal to the length of the decoded string.
 * The decoded string is guaranteed to have less than 2^63 letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q880_DecodedStringAtIndex {

    @Answer
    public String decodeAtIndex(String S, int K) {
        Stack<Long> lens = new Stack<>();
        lens.push(0L);
        Stack<Character> ends = new Stack<>();
        for (int i = 0; lens.peek() < K; i++) {
            char c = S.charAt(i);
            if (Character.isDigit(c)) {
                lens.push(lens.peek() * (c - '0'));
                ends.push(ends.peek());
            } else {
                lens.push(lens.peek() + 1);
                ends.push(c);
            }
        }

        long len = K;
        while (true) {
            char c = ends.pop();
            len %= lens.pop();
            if (len == 0) {
                return String.valueOf(c);
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("leet2code3", 10).expect("o");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ha22", 5).expect("h");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("a2345678999999999999999", 1).expect("a");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("a2b3c4d5e6f7g8h9", 10).expect("c");

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("y959q969u3hb22odq595", 222280369).expect("y");

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(
            "ajx37nyx97niysdrzice4petvcvmcgqn282zicpbx6okybw93vhk782unctdbgmcjmbqn25rorktmu5ig2qn2y4xagtru2nehmsp",
            427922657).expect("x");

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(
            "ajx37nyx97niysdrzice4petvcvmcgqn282zicpbx6okybw93vhk782unctdbgmcjmbqn25rorktmu5ig2qn2y4xagtru2nehmsp",
            976159153).expect("a");

}
