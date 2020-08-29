package q1050;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1003. Check If Word Is Valid After Substitutions
 * https://leetcode.com/problems/check-if-word-is-valid-after-substitutions/
 *
 * We are given that the string "abc" is valid.
 *
 * From any valid string V, we may split V into two pieces X and Y such that X + Y (X concatenated with Y) is equal to
 * V.  (X or Y may be empty.)  Then, X + "abc" + Y is also valid.
 *
 * If for example S = "abc", then examples of valid strings are: "abc", "aabcbc", "abcabc", "abcabcababcc".  Examples of
 * invalid strings are: "abccba", "ab", "cababc", "bac".
 *
 * Return true if and only if the given string S is valid.
 *
 * Example 1:
 *
 * Input: "aabcbc"
 * Output: true
 * Explanation:
 * We start with the valid string "abc".
 * Then we can insert another "abc" between "a" and "bc", resulting in "a" + "abc" + "bc" which is "aabcbc".
 *
 * Example 2:
 *
 * Input: "abcabcababcc"
 * Output: true
 * Explanation:
 * "abcabcabc" is valid after consecutive insertings of "abc".
 * Then we can insert "abc" before the last letter, resulting in "abcabcab" + "abc" + "c" which is "abcabcababcc".
 *
 * Example 3:
 *
 * Input: "abccba"
 * Output: false
 *
 * Example 4:
 *
 * Input: "cababc"
 * Output: false
 *
 * Note:
 *
 * 1 <= S.length <= 20000
 * S[i] is 'a', 'b', or 'c'
 */
@RunWith(LeetCodeRunner.class)
public class Q1003_CheckIfWordIsValidAfterSubstitutions {

    @Answer
    public boolean isValid(String S) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            int idx = S.charAt(i) - 'a';
            if (!stack.isEmpty() && stack.peek() + 1 == idx) {
                stack.pop();
            }
            if (idx < 2) {
                stack.push(idx);
            }
        }
        return stack.isEmpty();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aabcbc").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abcabcababcc").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create("abccba").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create("cababc").expect(false);

}
