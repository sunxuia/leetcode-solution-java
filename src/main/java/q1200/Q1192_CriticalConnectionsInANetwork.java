package q1200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1192. Critical Connections in a Network
 * https://leetcode.com/problems/critical-connections-in-a-network/
 *
 * There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network
 * where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any other server
 * directly or indirectly through the network.
 *
 * A critical connection is a connection that, if removed, will make some server unable to reach some other server.
 *
 * Return all critical connections in the network in any order.
 *
 * Example 1:
 * <img src="Q1192_PIC.png">
 * Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
 * Output: [[1,3]]
 * Explanation: [[3,1]] is also accepted.
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * n-1 <= connections.length <= 10^5
 * connections[i][0] != connections[i][1]
 * There are no repeated connections.
 */
@RunWith(LeetCodeRunner.class)
public class Q1192_CriticalConnectionsInANetwork {

    /**
     * 这题应该使用 Tarjan 算法, 寻找图中的强联通分量.
     * 参考文档 https://leetcode.jp/leetcode-1192-critical-connections-in-a-network-解题思路分析/
     */
    @Answer
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // 深度数组, 所有节点初始深度为-1
        int[] deepArray = new int[n];
        Arrays.fill(deepArray, -1);

        // 联通图, links[i]代表节点i可以连通哪些节点
        List<Integer>[] links = new List[n];
        for (int i = 0; i < n; i++) {
            links[i] = new ArrayList<>();
        }
        for (List<Integer> connection : connections) {
            links[connection.get(0)].add(connection.get(1));
            links[connection.get(1)].add(connection.get(0));
        }

        List<List<Integer>> res = new ArrayList<>();
        dfs(deepArray, links, res, 0, 0, 0);
        return res;
    }

    /**
     * @param curr 当前节点
     * @param prev 前节点
     * @param depth 当前深度
     * @return 当前节点所有dfs路径终点的最小深度
     */
    private int dfs(int[] deepArray, List<Integer>[] links, List<List<Integer>> res,
            int curr, int prev, int depth) {
        // 将当前深度存入深度数组
        deepArray[curr] = depth;
        // 返回值, 表示该节点到终点的深度
        int minEndDepth = Integer.MAX_VALUE;
        // 遍历当前节点能走的所有节点
        for (int i : links[curr]) {
            if (i == prev) {
                // 不能往回走
                continue;
            }
            // 到终点的深度
            int endDeep;
            if (deepArray[i] == -1) {
                // 深度为-1的点表示没走过, 可以dfs
                endDeep = dfs(deepArray, links, res, i, curr, depth + 1);
                // 如果深度大于当前深度, 说明当前点不在闭环上, 当前点与下一节点 i 之间的连线为答案之一
                if (endDeep > depth) {
                    List<Integer> list = new ArrayList<>();
                    list.add(curr);
                    list.add(i);
                    res.add(list);
                }
            } else {
                // i节点深度不为-1, 说明已经走过, i节点为dfs终点
                endDeep = deepArray[i];
            }
            // 更新最小深度
            minEndDepth = Math.min(minEndDepth, endDeep);
        }
        return minEndDepth;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(4)
            .addArgument(Arrays.asList(
                    Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 0), Arrays.asList(1, 3)))
            .expect(Arrays.asList(Arrays.asList(1, 3)))
            .unorderResult("**")
            .build();

}
