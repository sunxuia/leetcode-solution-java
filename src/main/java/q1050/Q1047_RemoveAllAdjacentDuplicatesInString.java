package q1050;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1047. Remove All Adjacent Duplicates In String
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
 *
 * Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and
 * removing them.
 *
 * We repeatedly make duplicate removals on S until we no longer can.
 *
 * Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.
 *
 * Example 1:
 *
 * Input: "abbaca"
 * Output: "ca"
 * Explanation:
 * For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible
 * move.  The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is
 * "ca".
 *
 * Note:
 *
 * 1 <= S.length <= 20000
 * S consists only of English lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1047_RemoveAllAdjacentDuplicatesInString {

    @Answer
    public String removeDuplicates(String S) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }
        return sb.toString();
    }

    /**
     * 针对上面的优化: 使用数组来替换栈
     */
    @Answer
    public String removeDuplicates2(String S) {
        char[] sc = S.toCharArray();
        int len = 0;
        for (int i = 0; i < sc.length; i++) {
            if (len > 0 && sc[i] == sc[len - 1]) {
                len--;
            } else {
                sc[len++] = sc[i];
            }
        }
        return new String(sc, 0, len);
    }

    @TestData
    public DataExpectation example = DataExpectation.create("abbaca").expect("ca");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("aaaaaaaaa").expect("a");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("aaaaaaaa").expect("");

}
