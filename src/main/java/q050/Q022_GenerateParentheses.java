package q050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/generate-parentheses/
 *
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * For example, given n = 3, a solution set is:
 *
 * [
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q022_GenerateParentheses {

    /**
     * dfs, 比较慢 (40ms)
     */
    @Answer
    public List<String> generateParenthesisDfs(int n) {
        if (n == 0) {
            return Collections.emptyList();
        }
        if (n == 1) {
            return Arrays.asList("()");
        }
        Set<String> set = new HashSet<>();
        List<String> sub = generateParenthesisDfs(n - 1);
        for (String s : sub) {
            set.add("(" + s + ")");
        }
        for (int i = 1; i <= n / 2; i++) {
            List<String> sub1 = generateParenthesisDfs(i);
            List<String> sub2 = generateParenthesisDfs(n - i);
            for (String s1 : sub1) {
                for (String s2 : sub2) {
                    set.add(s1 + s2);
                    set.add(s2 + s1);
                }
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * dp. 针对上面的重复运行使用dp 来缓存, 快了 3ms. (37ms)
     */
    @Answer
    public List<String> dp(int n) {
        Set<String>[] cache = (Set<String>[]) new Set[n + 2];
        cache[0] = Collections.emptySet();
        cache[1] = Collections.singleton("()");
        for (int i = 2; i <= n; i++) {
            Set<String> set = new HashSet<>();
            for (String s : cache[i - 1]) {
                set.add("(" + s + ")");
            }
            for (int j = 1; j <= i / 2; j++) {
                Set<String> sub1 = cache[j];
                Set<String> sub2 = cache[i - j];
                for (String s1 : sub1) {
                    for (String s2 : sub2) {
                        set.add(s1 + s2);
                        set.add(s2 + s1);
                    }
                }
            }
            cache[i] = set;
        }
        return new ArrayList<>(cache[n]);
    }

    /**
     * 针对上面运行缓慢的问题进行修改. 利用括号的特性, 在最后才生成字符串.
     */
    @Answer
    public List<String> generateParenthesis(int n) {
        if (n == 0) {
            return Collections.emptyList();
        }
        List<String> res = new ArrayList<>();
        dfs(res, n, 0, 0, new StringBuilder(n * 2));
        return res;
    }

    private void dfs(List<String> res, int n, int open, int close, StringBuilder sb) {
        if (open + close == n * 2) {
            res.add(sb.toString());
            return;
        }
        if (open < n) {
            sb.append('(');
            dfs(res, n, open + 1, close, sb);
            sb.setLength(open + close);
        }
        if (close < open) {
            sb.append(')');
            dfs(res, n, open, close + 1, sb);
            sb.setLength(open + close);
        }
    }

    @TestData
    public DataExpectation normal0 = DataExpectation.builder()
            .addArgument(0)
            .expect(new String[]{})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(1)
            .expect(new String[]{"()"})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(2)
            .expect(new String[]{"()()", "(())"})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(3)
            .expect(new String[]{
                    "((()))",
                    "(()())",
                    "(())()",
                    "()(())",
                    "()()()"
            })
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument(4)
            .expect(new String[]{
                    "(((())))",
                    "((()()))",
                    "((())())",
                    "((()))()",
                    "(()(()))",
                    "(()()())",
                    "(()())()",
                    "(())(())",
                    "(())()()",
                    "()((()))",
                    "()(()())",
                    "()(())()",
                    "()()(())",
                    "()()()()"
            })
            .unorderResult()
            .build();

}
