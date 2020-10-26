package q1250;

import java.util.Stack;
import org.junit.runner.RunWith;
import q1050.Q1047_RemoveAllAdjacentDuplicatesInString;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1209. Remove All Adjacent Duplicates in String II
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
 *
 * Given a string s, a k duplicate removal consists of choosing k adjacent and equal letters from s and removing them
 * causing the left and the right side of the deleted substring to concatenate together.
 *
 * We repeatedly make k duplicate removals on s until we no longer can.
 *
 * Return the final string after all such duplicate removals have been made.
 *
 * It is guaranteed that the answer is unique.
 *
 * Example 1:
 *
 * Input: s = "abcd", k = 2
 * Output: "abcd"
 * Explanation: There's nothing to delete.
 *
 * Example 2:
 *
 * Input: s = "deeedbbcccbdaa", k = 3
 * Output: "aa"
 * Explanation:
 * First delete "eee" and "ccc", get "ddbbbdaa"
 * Then delete "bbb", get "dddaa"
 * Finally delete "ddd", get "aa"
 *
 * Example 3:
 *
 * Input: s = "pbbcggttciiippooaais", k = 2
 * Output: "ps"
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * 2 <= k <= 10^4
 * s only contains lower case English letters.
 *
 * 上一题 {@link Q1047_RemoveAllAdjacentDuplicatesInString}
 * 和上一题相比, 这题的重复数量变为参数k.
 */
@RunWith(LeetCodeRunner.class)
public class Q1209_RemoveAllAdjacentDuplicatesInStringII {

    @Answer
    public String removeDuplicates(String s, int k) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        // 哨兵
        sb.append(' ');
        stack.push(0);
        for (int i = 0; i < s.length(); i++) {
            char prev = sb.charAt(sb.length() - 1);
            char curr = s.charAt(i);
            if (prev == curr && stack.peek() == k - 1) {
                stack.pop();
                sb.setLength(sb.length() - k + 1);
            } else if (prev == curr) {
                stack.push(stack.pop() + 1);
                sb.append(curr);
            } else {
                stack.push(1);
                sb.append(curr);
            }
        }
        return sb.substring(1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcd", 2).expect("abcd");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("deeedbbcccbdaa", 3).expect("aa");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("pbbcggttciiippooaais", 2).expect("ps");

}
