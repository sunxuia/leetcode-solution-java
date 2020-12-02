package q850;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/sum-of-distances-in-tree/
 *
 * An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
 *
 * The ith edge connects nodes edges[i][0] and edges[i][1] together.
 *
 * Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
 *
 * Example 1:
 *
 * Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
 * Output: [8,12,6,10,10,10]
 * Explanation:
 * Here is a diagram of the given tree:
 * >   0
 * >  / \
 * > 1   2
 * >    /|\
 * >   3 4 5
 * We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
 * equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
 *
 * Note: 1 <= N <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q834_SumOfDistancesInTree {

    /**
     * 这题使用普通的遍历图的方式会超时, 需要利用到这是一个无环图(树)的特性.
     * 但是这题既无法缓存节点到其他节点的距离也无法每次都计算到其他节点的距离, 对于测试用例overTime 会出现超时或超内存限制的错误.
     * 所以这题可以使用边来缓存到这个边另一端所有点的数量和距离.
     */
    @Answer
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        List<Edge>[] neighbors = new List[N];
        for (int i = 0; i < N; i++) {
            neighbors[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            neighbors[edge[0]].add(new Edge(edge[1]));
            neighbors[edge[1]].add(new Edge(edge[0]));
        }

        for (int i = 0; i < N; i++) {
            dfs(neighbors, i, -1);
        }

        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            for (Edge neighbor : neighbors[i]) {
                res[i] += neighbor.dist;
            }
        }
        return res;
    }

    // 单向边
    private static class Edge {

        // 边的另一点的下标
        int to;

        // 边另一端的节点的数量和总的距离. -1 表示还没有计算出来.
        int count = -1, dist;

        Edge(int to) {
            this.to = to;
        }
    }

    private void dfs(List<Edge>[] neighbors, int curr, int from) {
        for (Edge edge : neighbors[curr]) {
            if (edge.to == from || edge.count > -1) {
                continue;
            }

            dfs(neighbors, edge.to, curr);

            edge.count = 0;
            for (Edge other : neighbors[edge.to]) {
                if (other.to != curr) {
                    edge.count += other.count;
                    edge.dist += other.dist;
                }
            }
            // 从出发点(curr) 到edge 另一边的点需要额外走的距离
            edge.count++;
            edge.dist += edge.count;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(6, new int[][]{{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}})
            .expect(new int[]{8, 12, 6, 10, 10, 10});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(3, new int[][]{{2, 0}, {1, 0}})
            .expect(new int[]{2, 3, 3});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(4, new int[][]{{1, 2}, {3, 2}, {3, 0}})
            .expect(new int[]{6, 6, 4, 4});

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(7, new int[][]{{5, 2}, {0, 3}, {4, 5}, {0, 2}, {1, 0}, {6, 3}})
            .expect(new int[]{10, 15, 11, 13, 19, 14, 18});

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(10000, TestDataFileHelper.read(testDataFile, 1, int[][].class))
            .expect(TestDataFileHelper.read(testDataFile, 2, int[].class));

}
