package q050;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.UsingTestData;
import util.runner.data.DataExpectation;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 *
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 *
 * Note:
 *
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters like . or *.
 *
 * Example 1:
 *
 * Input:
 * s = "aa"
 * p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 *
 * Input:
 * s = "aa"
 * p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 *
 * Example 3:
 *
 * Input:
 * s = "ab"
 * p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 *
 * Example 4:
 *
 * Input:
 * s = "aab"
 * p = "c*a*b"
 * Output: true
 * Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
 *
 * Example 5:
 *
 * Input:
 * s = "mississippi"
 * p = "mis*is*p*."
 * Output: false
 *
 * 解析:
 * 输入s 只有小写字母[a-z].
 * 输入p 只有小写字母和2 个通配符[a-z.*], 通配符.匹配[a-z], 通配符*表示0或多个匹配.
 * 输入p 和q 都可能是"".
 * 返回值表示是否 s 完全匹配 p.
 */
@RunWith(LeetCodeRunner.class)
public class Q010_RegularExpressionMatching {

    /**
     * 直接使用java 的正则表达式, 这个执行时间43ms.
     */
    @Answer
    public boolean usingJavaPattern(String s, String p) {
        return java.util.regex.Pattern.matches(p, s);
    }

    /**
     * 自己实现简易正则表达式, 并通过递归方式进行匹配.
     */
    @Answer
    public boolean isMatch_Recursion(String s, String p) {
        List<Node> nodes = createNodes(p);
        return isMatch(s, nodes, 0, 0);
    }

    private boolean isMatch(String s, List<Node> nodes, int sIndex, int nodeIndex) {
        if (nodeIndex == nodes.size()) {
            return sIndex == s.length();
        }
        Node node = nodes.get(nodeIndex);
        Match match = node.match(s, sIndex);
        if (match != null) {
            do {
                if (isMatch(s, nodes, sIndex + match.matchCount(), nodeIndex + 1)) {
                    return true;
                }
            } while (match.tryOtherMatch());
        }
        return false;
    }

    private List<Node> createNodes(String pattern) {
        final int length = pattern.length();
        List<Node> nodes = new ArrayList<>();
        SingleMatch singleMatch = new SingleMatch();

        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(i);

            Node node;
            if (c == '.') {
                node = (s, j) -> j < s.length() ? singleMatch : null;
            } else if (c == '*') {
                Node lastNode = nodes.remove(nodes.size() - 1);
                node = (s, j) -> new StarMatch(lastNode, s, j);
            } else {
                node = (s, j) -> j < s.length() && s.charAt(j) == c ? singleMatch : null;
            }
            nodes.add(node);
        }
        return nodes;
    }

    private interface Node {

        Match match(String s, int i);

    }

    private interface Match {

        int matchCount();

        boolean tryOtherMatch();
    }

    private static class SingleMatch implements Match {

        @Override
        public int matchCount() {
            return 1;
        }

        @Override
        public boolean tryOtherMatch() {
            return false;
        }
    }

    private static class StarMatch implements Match {

        private int count;

        private StarMatch(Node node, String s, int sIndex) {
            for (; sIndex < s.length() && node.match(s, sIndex) != null; sIndex++) {
                // 这里因为*前面的匹配的都是单个匹配, 所以这里可以直接匹配并计数
                count++;
            }
        }

        @Override
        public int matchCount() {
            return count;
        }

        @Override
        public boolean tryOtherMatch() {
            return --count >= 0;
        }
    }

    /**
     * 上面的实现有些复杂/花里胡哨, 这里因为只是简单的匹配, 所以按照
     * 如下方式简单匹配一下就好了. 这种方式内存占用比较小, 但是时间不如下面的dp 方法
     */
    @Answer
    public boolean isMatch_Simple(String s, String p) {
        char[] sc = s.toCharArray();
        char[] pc = p.toCharArray();
        Stack<Integer> stack = new Stack<>();
        int sci = 0, pci = 0, matchCount = -1;
        while (true) {
            boolean star = pci < pc.length - 1 && pc[pci + 1] == '*';
            if (pci < pc.length) {
                char pcc = pc[pci];
                if (star && matchCount >= 0) {
                    // 退回的* 匹配
                    matchCount--;
                } else if (matchCount >= 0) {
                    // 退回的单个匹配匹配肯定失败
                    matchCount = -1;
                } else if (star && pcc == '.') {
                    // 新的 .* 匹配
                    matchCount = sc.length - sci;
                } else if (star) {
                    //新的 a* 匹配
                    if (matchCount == -1) {
                        matchCount = 0;
                        while (sci + matchCount < sc.length && sc[sci + matchCount] == pcc) {
                            matchCount++;
                        }
                    }
                } else if (sci < sc.length && (pcc == '.' || sc[sci] == pcc)) {
                    // 单字符匹配
                    matchCount = 1;
                } else {
                    // 不匹配
                    matchCount = -1;
                }
            } else if (sci == sc.length) {
                return true;
            }

            // 匹配成功
            if (matchCount >= 0) {
                stack.push(matchCount);
                stack.push(pci);
                stack.push(sci);
                sci += matchCount;
                pci += (star ? 2 : 1);
                matchCount = -1;
            } else if (stack.isEmpty()) {
                return false;
            } else {
                sci = stack.pop();
                pci = stack.pop();
                matchCount = stack.pop();
            }
        }
    }

    /**
     * leetcode 中运行最快的方式是动态规划.
     */
    @Answer
    @UsingTestData({})
    public boolean isMatch_Dp(String s, String p) {
        final int sLength = s.length();
        final int pLength = p.length();
//        var tablePrinter = new TablePrinter();
//        tablePrinter.leftAxisChars(" " + s);
//        tablePrinter.topAxisChars(" " + p);
        boolean[][] dp = new boolean[sLength + 1][pLength + 1];
        dp[0][0] = true;
        // 开头的* 匹配
        for (int i = 0; i < pLength; i++) {
            if (p.charAt(i) == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }
//        tablePrinter.print(dp, v -> v ? "√" : "×");
        for (int si = 1; si <= sLength; si++) {
            for (int pi = 1; pi <= pLength; pi++) {
                char pc = p.charAt(pi - 1);
                if (pc == '*') {
                    // 0 匹配. 此时的值表示前一个匹配结果.
                    boolean match = dp[si][pi - 2];
                    // 1或多次匹配. [si, pi -1] 表示* 前面的匹配与当前字符匹配, 这是第2 次匹配,
                    // [si -1, pi] 表示前一个字符与* 匹配, 这是第N 次匹配.
                    if (!match && (dp[si][pi - 1] || dp[si - 1][pi])) {
                        // .* 的多次匹配.
                        match = p.charAt(pi - 2) == '.';
                        // * 的多次匹配, 与上一个字符相等.
                        match = match || p.charAt(pi - 2) == s.charAt(si - 1);
                    }
                    dp[si][pi] = match;
                } else {
                    boolean match = pc == '.' || pc == s.charAt(si - 1);
                    dp[si][pi] = match && dp[si - 1][pi - 1];
                }
            }
//            tablePrinter.print(dp, v -> v ? "√" : "×");
        }
        return dp[sLength][pLength];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("aa")
            .addArgument("a")
            .expect(false)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("aa")
            .addArgument("a*")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("ab")
            .addArgument(".*")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument("aab")
            .addArgument("c*a*b")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("mississippi")
            .addArgument("mis*is*p*.")
            .expect(false)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("mississippi")
            .addArgument("mis*is*ip*.")
            .expect(true)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument("aaa")
            .addArgument("a*a")
            .expect(true)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument("aaa")
            .addArgument("ab*a")
            .expect(false)
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument("aaa")
            .addArgument("ab*a*c*a")
            .expect(true)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("")
            .addArgument(".*")
            .expect(true)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument("")
            .addArgument("")
            .expect(true)
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument("")
            .addArgument("a")
            .expect(false)
            .build();
}
