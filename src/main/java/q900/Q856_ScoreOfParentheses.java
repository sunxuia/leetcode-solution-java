package q900;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/score-of-parentheses/
 *
 * Given a balanced parentheses string S, compute the score of the string based on the following rule:
 *
 * () has score 1
 * AB has score A + B, where A and B are balanced parentheses strings.
 * (A) has score 2 * A, where A is a balanced parentheses string.
 *
 *
 *
 * Example 1:
 *
 * Input: "()"
 * Output: 1
 *
 * Example 2:
 *
 * Input: "(())"
 * Output: 2
 *
 * Example 3:
 *
 * Input: "()()"
 * Output: 2
 *
 * Example 4:
 *
 * Input: "(()(()))"
 * Output: 6
 *
 *
 *
 * Note:
 *
 * S is a balanced parentheses string, containing only ( and ).
 * 2 <= S.length <= 50
 */
@RunWith(LeetCodeRunner.class)
public class Q856_ScoreOfParentheses {

    @Answer
    public int scoreOfParentheses(String S) {
        Deque<Integer> scores = new ArrayDeque<>();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                scores.push(0);
            } else {
                int sum = 0;
                while (scores.peek() != 0) {
                    sum += scores.pop();
                }
                sum = sum == 0 ? 1 : sum * 2;
                scores.pop();
                scores.push(sum);
            }
        }
        return scores.stream().mapToInt(i -> i).sum();
    }

    // LeetCode 上更快的方式, 针对上面做法进行了一些小改进
    @Answer
    public int scoreOfParentheses2(String S) {
        Deque<Integer> scores = new ArrayDeque<>();
        scores.add(0);
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                scores.push(0);
            } else {
                int curr = scores.pop();
                curr = curr == 0 ? 1 : curr * 2;
                // 将当前的值和前面的值相加, 避免在结束后再相加
                scores.push(scores.pop() + curr);
            }
        }
        return scores.peek();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("()").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("(())").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("()()").expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create("(()(()))").expect(6);

}
