package q1800;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1786. Number of Restricted Paths From First to Last Node
 * https://leetcode.com/problems/number-of-restricted-paths-from-first-to-last-node/
 *
 * There is an undirected weighted connected graph. You are given a positive integer n which denotes that the graph has
 * n nodes labeled from 1 to n, and an array edges where each edges[i] = [ui, vi, weighti] denotes that there is an
 * edge between nodes ui and vi with weight equal to weighti.
 *
 * A path from node start to node end is a sequence of nodes [z0, z1, z2, ..., zk] such that z0 = start and zk = end
 * and there is an edge between zi and zi+1 where 0 <= i <= k-1.
 *
 * The distance of a path is the sum of the weights on the edges of the path. Let distanceToLastNode(x) denote the
 * shortest distance of a path between node n and node x. A restricted path is a path that also satisfies that
 * distanceToLastNode(zi) > distanceToLastNode(zi+1) where 0 <= i <= k-1.
 *
 * Return the number of restricted paths from node 1 to node n. Since that number may be too large, return it modulo
 * 10^9 + 7.
 *
 * Example 1:
 * (图Q1786_PIC1.png)
 * Input: n = 5, edges = [[1,2,3],[1,3,3],[2,3,1],[1,4,2],[5,2,2],[3,5,1],[5,4,10]]
 * Output: 3
 * Explanation: Each circle contains the node number in black and its distanceToLastNode value in blue. The three
 * restricted paths are:
 * 1) 1 --> 2 --> 5
 * 2) 1 --> 2 --> 3 --> 5
 * 3) 1 --> 3 --> 5
 *
 * Example 2:
 * (图Q1786_PIC2.png)
 * Input: n = 7, edges = [[1,3,1],[4,1,2],[7,3,4],[2,5,3],[5,6,1],[6,7,2],[7,5,3],[2,6,4]]
 * Output: 1
 * Explanation: Each circle contains the node number in black and its distanceToLastNode value in blue. The only
 * restricted path is 1 --> 3 --> 7.
 *
 * Constraints:
 *
 * 1 <= n <= 2 * 10^4
 * n - 1 <= edges.length <= 4 * 10^4
 * edges[i].length == 3
 * 1 <= ui, vi <= n
 * ui != vi
 * 1 <= weighti <= 10^5
 * There is at most one edge between any two nodes.
 * There is at least one path between any two nodes.
 */
@RunWith(LeetCodeRunner.class)
public class Q1786_NumberOfRestrictedPathsFromFirstToLastNode {

    /**
     * distanceToLastNode(x) 表示的就是节点n 到节点x 的最短路径,
     * 受限路径 [z1(节点1), z2, ..., zk(节点n)] 表示节点1到节点n 之间的路径,
     * 且distanceToLastNode(zi) < distanceToLastNode(zi+1),
     * 题目要求这个路径的数量, 则可以反过来计算从n 到所有点的受限路径的数量.
     */
    @Answer
    public int countRestrictedPaths(int n, int[][] edges) {
        // 构建稀疏无向联通矩阵
        Map<Integer, Integer>[] graph = new Map[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new HashMap<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].put(edge[1], edge[2]);
            graph[edge[1]].put(edge[0], edge[2]);
        }

        // 从n 点出发, 求出到所有点的最短路径 (distanceToLastNode) (dijkstra 优先队列优化)
        int[] dtln = new int[n + 1];
        Arrays.fill(dtln, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(arr -> arr[1]));
        pq.offer(new int[]{n, 0});
        while (!pq.isEmpty()) {
            int[] arr = pq.poll();
            int i = arr[0], dist = arr[1];
            if (dtln[i] > dist) {
                dtln[i] = dist;
                for (int neighbor : graph[i].keySet()) {
                    pq.offer(new int[]{neighbor, dist + graph[i].get(neighbor)});
                }
            }
        }

        // 计算所有节点从1 开始的可达的入度
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        int[] inDegree = new int[n + 1];
        while (!queue.isEmpty()) {
            int i = queue.poll();
            for (int neighbor : graph[i].keySet()) {
                if (dtln[i] > dtln[neighbor]) {
                    if (inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                    inDegree[neighbor]++;
                }
            }
        }

        // 从节点1 开始遍历所有节点计算路径数量
        int[] paths = new int[n + 1];
        queue.offer(1);
        paths[1] = 1;
        while (!queue.isEmpty()) {
            int i = queue.poll();
            for (int neighbor : graph[i].keySet()) {
                if (dtln[i] > dtln[neighbor]) {
                    paths[neighbor] = (paths[neighbor] + paths[i]) % 10_0000_0007;
                    // 如果入度变为0, 则可以确定到达该节点的路径数量, 可以继续从该节点寻找之后的节点
                    if (--inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return paths[n];
    }

    /**
     * leetcode 上比较快的方法.
     * 开始的解法与上面的基本类似, 最后是使用带缓存的dfs 来计算.
     */
    @Answer
    public int countRestrictedPaths2(int n, int[][] edges) {
        // 构建稀疏无向联通矩阵
        List<int[]>[] graph = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[2], edge[1]});
            graph[edge[1]].add(new int[]{edge[2], edge[0]});
        }

        // 从n 点出发, 求出到所有点的最短路径 (distanceToLastNode) (dijkstra 优先队列优化)
        int[] dtln = new int[n + 1];
        Arrays.fill(dtln, Integer.MAX_VALUE);
        dtln[n] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{n, 0});
        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            int i = node[0], dist = node[1];
            for (int[] nextNode : graph[i]) {
                int totalWeight = dist + nextNode[0];
                int next = nextNode[1];
                if (totalWeight < dtln[next]) {
                    dtln[next] = totalWeight;
                    pq.add(new int[]{next, totalWeight});
                }
            }
        }

        return dfs(1, n, dtln, graph, new Integer[n + 1]);
    }

    public int dfs(int start, int end, int[] dtln, List<int[]>[] graph, Integer[] memo) {
        if (memo[start] != null) {
            return memo[start];
        }
        if (start == end) {
            return 1;
        }
        int res = 0;
        int currentDist = dtln[start];
        for (int[] nextNode : graph[start]) {
            if (currentDist > dtln[nextNode[1]]) {
                res = (res + dfs(nextNode[1], end, dtln, graph, memo)) % 10_0000_0007;
            }
        }
        memo[start] = res;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{
                    {1, 2, 3},
                    {1, 3, 3},
                    {2, 3, 1},
                    {1, 4, 2},
                    {5, 2, 2},
                    {3, 5, 1},
                    {5, 4, 10}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(7, new int[][]{
                    {1, 3, 1},
                    {4, 1, 2},
                    {7, 3, 4},
                    {2, 5, 3},
                    {5, 6, 1},
                    {6, 7, 2},
                    {7, 5, 3},
                    {2, 6, 4}})
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(6, new int[][]{
                    {6, 2, 9},
                    {2, 1, 7},
                    {6, 5, 10},
                    {4, 3, 1},
                    {3, 1, 8},
                    {4, 6, 4},
                    {5, 1, 7},
                    {1, 4, 7},
                    {4, 5, 3},
                    {3, 6, 6},
                    {5, 3, 9},
                    {1, 6, 6},
                    {3, 2, 2},
                    {5, 2, 8}})
            .expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(422, TestDataFileHelper.read(int[][].class))
            .expect(13944179);

}
