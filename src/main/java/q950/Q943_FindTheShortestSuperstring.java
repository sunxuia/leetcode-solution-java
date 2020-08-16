package q950;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.runner.RunWith;
import q850.Q847_ShortestPathVisitingAllNodes;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 943. Find the Shortest Superstring
 * https://leetcode.com/problems/find-the-shortest-superstring/
 *
 * Given an array A of strings, find any smallest string that contains each string in A as a substring.
 *
 * We may assume that no string in A is substring of another string in A.
 *
 * Example 1:
 *
 * Input: ["alex","loves","leetcode"]
 * Output: "alexlovesleetcode"
 * Explanation: All permutations of "alex","loves","leetcode" would also be accepted.
 *
 * Example 2:
 *
 * Input: ["catg","ctaagt","gcta","ttca","atgcatc"]
 * Output: "gctaagttcatgcatc"
 *
 * Note:
 *
 * 1 <= A.length <= 12
 * 1 <= A[i].length <= 20
 *
 * 相关题目 {@link Q847_ShortestPathVisitingAllNodes}
 */
@RunWith(LeetCodeRunner.class)
public class Q943_FindTheShortestSuperstring {

    @TestData
    public DataExpectation example1 = createDataExpectation(
            new String[]{"alex", "loves", "leetcode"},
            "alexlovesleetcode");
    @TestData
    public DataExpectation example2 = createDataExpectation(
            new String[]{"catg", "ctaagt", "gcta", "ttca", "atgcatc"},
            "gctaagttcatgcatc");
    @TestData
    public DataExpectation normal1 = createDataExpectation(
            new String[]{"cedefifgstkyxfcuajfa", "ooncedefifgstkyxfcua", "assqjfwarvjcjedqtoz", "fcuajfassqjfwarvjc",
                    "fwarvjcjedqtozctcd", "zppedxfumcfsngp", "kyxfcuajfassqjfwa", "fumcfsngphjyfhhwkqa",
                    "fassqjfwarvjcjedq", "ppedxfumcfsngphjyf", "dqtozctcdk"},
            "ooncedefifgstkyxfcuajfassqjfwarvjcjedqtozctcdkzppedxfumcfsngphjyfhhwkqa");
    @TestData
    public DataExpectation normal2 = createDataExpectation(new String[]{"ebc", "xchdh", "hieb", "bcu"}, "xchdhiebcu");
    @TestData
    public DataExpectation normal3 = createDataExpectation(new String[]{"gruuz", "zjr", "uuzj", "rfgr"}, "rfgruuzjr");
    private List<Integer> totalPath;
    private int totalLen;

    /**
     * 计算字符串结尾和其他字符串开头的重合度, 转换为遍历有向图的最短路径(哈密顿路径).
     * LeetCode 上还有一种dp 的方式更快, 比这里使用的dfs 快.
     */
    @Answer
    public String shortestSuperstring(String[] A) {
        final int n = A.length;
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int len = Math.min(A[i].length(), A[j].length());
                while (len > 0 && !A[i].endsWith(A[j].substring(0, len))) {
                    len--;
                }
                graph[i][j] = A[j].length() - len;
            }
        }

        totalLen = Integer.MAX_VALUE;
        ArrayList<Integer> path = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            dfs(graph, 0, i, path, A[i].length());
        }

        StringBuilder sb = new StringBuilder(totalLen);
        sb.append(A[totalPath.get(0)]);
        for (int i = 1; i < n; i++) {
            int prev = totalPath.get(i - 1);
            int curr = totalPath.get(i);
            sb.append(A[curr], A[curr].length() - graph[prev][curr], A[curr].length());
        }
        return sb.toString();
    }

    private void dfs(int[][] graph, int visited, int curr, ArrayList<Integer> path, int len) {
        // 减枝, 否则会超时
        if (len >= totalLen) {
            return;
        }
        final int n = graph.length;
        visited |= 1 << curr;
        path.add(curr);
        if (visited == (1 << n) - 1) {
            if (len < totalLen) {
                totalPath = (List<Integer>) path.clone();
                totalLen = len;
            }
        } else {
            for (int i = 0; i < n; i++) {
                if ((visited >> i & 1) == 0) {
                    dfs(graph, visited, i, path, len + graph[curr][i]);
                }
            }
        }
        path.remove(path.size() - 1);
    }

    private DataExpectation createDataExpectation(String[] arg, String resExample) {
        return DataExpectation.builder()
                .addArgument(arg)
                .expect(resExample)
                .assertMethod((String ret) -> {
                    Assert.assertEquals(ret.length(), resExample.length());
                    for (String s : arg) {
                        Assert.assertTrue(ret.contains(s));
                    }
                }).build();
    }

}
