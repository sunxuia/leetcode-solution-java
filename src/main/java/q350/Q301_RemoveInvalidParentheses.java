package q350;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/remove-invalid-parentheses/
 *
 * Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible
 * results.
 *
 * Note: The input string may contain letters other than the parentheses ( and ).
 *
 * Example 1:
 *
 * Input: "()())()"
 * Output: ["()()()", "(())()"]
 *
 * Example 2:
 *
 * Input: "(a)())()"
 * Output: ["(a)()()", "(a())()"]
 *
 * Example 3:
 *
 * Input: ")("
 * Output: [""]
 */
@RunWith(LeetCodeRunner.class)
public class Q301_RemoveInvalidParentheses {

    // BFS 的解法, 这个比较慢
    @Answer
    public List<String> removeInvalidParentheses_BFS(String s) {
        List<String> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        StringBuilder cache = new StringBuilder();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(s);
        boolean found = false;
        while (!queue.isEmpty()) {
            String str = queue.remove();
            // 这个字符串是否已经检查过了(顺便加进去)
            if (!visited.add(str)) {
                continue;
            }
            // 这个字符串是否有效
            if (isValid(str)) {
                res.add(str);
                found = true;
            }
            // 如果已经找到了, 就不会再减少字符继续尝试了
            if (found) {
                continue;
            }
            // 减掉一个字符, 继续尝试.
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c == '(' || c == ')') {
                    cache.setLength(0);
                    cache.append(str, 0, i).append(str, i + 1, str.length());
                    queue.add(cache.toString());
                }
            }
        }
        return res;
    }

    private boolean isValid(String t) {
        int leftCount = 0;
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (c == '(') {
                leftCount++;
            } else if (c == ')' && leftCount-- == 0) {
                return false;
            }
        }
        return leftCount == 0;
    }

    // dfs 的解法. 与bfs 的解法类似.
    @Answer
    public List<String> removeInvalidParentheses_DFS(String s) {
        // 计算左括号和右括号多出来的数量 (最后肯定也是要删除这么多的左右括号的)
        int[] open = openP(s);
        List<String> res = new ArrayList<>();
        dfs(new HashSet<>(), res, s, open[0], open[1]);
        return res;
    }

    private int[] openP(CharSequence s) {
        int leftCount = 0, rightCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftCount++;
            } else if (c == ')') {
                if (leftCount == 0) {
                    rightCount++;
                } else {
                    leftCount--;
                }
            }
        }
        return new int[]{leftCount, rightCount};
    }

    private void dfs(Set<String> visited, List<String> res, String str, int leftCount, int rightCount) {
        if (!visited.add(str)) {
            return;
        }
        if (leftCount + rightCount == 0) {
            if (isValid(str)) {
                res.add(str);
            }
            return;
        }

        StringBuilder sb = new StringBuilder(str.length() - 1);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(' && leftCount > 0 || c == ')' && rightCount > 0) {
                sb.setLength(0);
                sb.append(str, 0, i).append(str, i + 1, str.length());
                int newLeft = leftCount - (c == '(' ? 1 : 0);
                int newRight = rightCount - (c == ')' ? 1 : 0);
                // 确认减少的趋势是正确的
                int[] open = openP(sb);
                if (open[0] == newLeft && open[1] == newRight) {
                    dfs(visited, res, sb.toString(), newLeft, newRight);
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("()())()")
            .expect(Arrays.asList("()()()", "(())()"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("(a)())()")
            .expect(Arrays.asList("(a)()()", "(a())()"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(")(")
            .expect(Collections.singleton(""))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("")
            .expect(Collections.singleton(""))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument("()")
            .expect(Collections.singleton("()"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("()())()")
            .expect(Arrays.asList("(())()", "()()()"))
            .unorderResult("")
            .build();

}
