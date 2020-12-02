package q900;

import org.junit.runner.RunWith;
import q800.Q785_IsGraphBipartite;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 886. Possible Bipartition
 * https://leetcode.com/problems/possible-bipartition/
 *
 * Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
 *
 * Each person may dislike some other people, and they should not go into the same group.
 *
 * Formally, if dislikes[i] = [a, b], it means it is not allowed to put the people numbered a and b into the same group.
 *
 * Return true if and only if it is possible to split everyone into two groups in this way.
 *
 * Example 1:
 *
 * Input: N = 4, dislikes = [[1,2],[1,3],[2,4]]
 * Output: true
 * Explanation: group1 [1,4], group2 [2,3]
 *
 * Example 2:
 *
 * Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
 * Output: false
 *
 * Example 3:
 *
 * Input: N = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * Output: false
 *
 * Constraints:
 *
 * 1 <= N <= 2000
 * 0 <= dislikes.length <= 10000
 * dislikes[i].length == 2
 * 1 <= dislikes[i][j] <= N
 * dislikes[i][0] < dislikes[i][1]
 * There does not exist i != j for which dislikes[i] == dislikes[j].
 *
 * 关联问题 {@link Q785_IsGraphBipartite}
 */
@RunWith(LeetCodeRunner.class)
public class Q886_PossibleBipartition {

    /**
     * 这题就是分隔图为2 个集合, 让集合内的点都互不相连.
     */
    @Answer
    public boolean possibleBipartition(int N, int[][] dislikes) {
        boolean[][] graph = new boolean[N][N];
        for (int[] dislike : dislikes) {
            int a = dislike[0] - 1, b = dislike[1] - 1;
            graph[a][b] = graph[b][a] = true;
        }

        int[] colors = new int[N];
        for (int i = 0; i < N; i++) {
            if (!dfs(graph, i, colors, 1) && !dfs(graph, i, colors, -1)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(boolean[][] graph, int i, int[] colors, int expect) {
        if (colors[i] != 0) {
            return colors[i] == expect;
        }
        colors[i] = expect;
        for (int j = 0; j < graph[i].length; j++) {
            if (graph[i][j]) {
                if (!dfs(graph, j, colors, -expect)) {
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(4, new int[][]{{1, 2}, {1, 3}, {2, 4}})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, new int[][]{{1, 2}, {1, 3}, {2, 3}})
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(5, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}})
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(50, TestDataFileHelper.read(int[][].class))
            .expect(true);

}
