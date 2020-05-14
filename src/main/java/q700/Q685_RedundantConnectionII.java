package q700;

import java.util.Arrays;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/redundant-connection-ii/
 *
 * In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all
 * other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which
 * has no parents.
 *
 * The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N)
 * , with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was
 * not an edge that already existed.
 *
 * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a
 * directed edge connecting nodes u and v, where u is a parent of child v.
 *
 * Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple
 * answers, return the answer that occurs last in the given 2D-array.
 *
 * Example 1:
 *
 * Input: [[1,2], [1,3], [2,3]]
 * Output: [2,3]
 * Explanation: The given directed graph will be like this:
 * >   1
 * >  / \
 * > v   v
 * > 2-->3
 *
 * Example 2:
 *
 * Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
 * Output: [4,1]
 * Explanation: The given directed graph will be like this:
 * > 5 <- 1 -> 2
 * >      ^    |
 * >      |    v
 * >      4 <- 3
 *
 * Note:
 * The size of the input 2D-array will be between 3 and 1000.
 * Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 */
@RunWith(LeetCodeRunner.class)
public class Q685_RedundantConnectionII {

    /**
     * 相比上一题这题的图是有向图.
     * 参考 https://www.cnblogs.com/grandyang/p/8445733.html
     * 这题还是可以使用union find 的方式来解答, 但是需要加入检测环的逻辑
     *
     * 此时的图有如下3 种可能:
     * 1. 无环图.
     * >   1
     * >  / \
     * > v   v
     * > 2-->3
     * 由于题目限制这个节点有且只会有1 个(否则就已经是树或者要删除多个条边才能变成树了),
     * 此时只需要删除入度为2 的节点的1 个入边即可.
     *
     * 2. 有环图, 没有入度为2 个节点.
     * > 5 <- 1 -> 2
     * >      ^    |
     * >      |    v
     * >      4 <- 3
     * 此时删除环路中任意一个边即可 (注意题目中并未要求是单根树, 所以破坏环路即可).
     *
     * 3. 有环图, 有入度为2 的节点.
     * > 1 -> 2 --> 3
     * >      ^   /
     * >      | ∟
     * >      4
     * 由于题目限制这个节点只会有1 个, 此时移除这个入度为2 的节点的环路的入边即可.
     */
    @Answer
    public int[] findRedundantDirectedConnection(int[][] edges) {
        final int n = edges.length;

        // 找出入度为2 的点, 根据题意, 这个点只会有0 或1 个.
        // 表示节点的父节点.
        int[] parent = new int[n + 1];
        // 如果入度为2, 那么要删除的边肯定是2 个入边中的1 个,
        // 使用如下两个变量分别表示2 个入边.
        int[] firstEdge = null, secondEdge = null;
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            if (parent[to] == 0) {
                // 节点入度为1
                parent[to] = from;
            } else {
                // 节点入度为2
                firstEdge = new int[]{parent[to], to};
                secondEdge = edge.clone();
                // 设为0 表示移除这个边, 整个遍历过程中只会有这个边被移除
                edge[1] = 0;
            }
        }

        // union find
        // (这里root = parent 只是为了复用内存空间)
        int[] root = parent;
        for (int i = 0; i <= n; i++) {
            root[i] = i;
        }
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            if (to == 0) {
                // 这个边是上面被移除的边
                continue;
            }
            // union find
            int r1 = getRoot(root, from), r2 = getRoot(root, to);
            // 如果r1 == r2 则说明可能是如下2 种情况:
            // 情况2 中的环路, 此时 firstEdge 为null, 且edge 这个边将闭合环路
            // 情况3 中在上面移除的边是 1->2 这条, 此时 firstEdge 不为null, 仍然形成环路, 且edge 这个边将闭合环路
            if (r1 == r2) {
                return firstEdge == null ? edge : firstEdge;
            }
            // 更新to 的根节点为from 的(反过来也可以, 都表示带方向的联通)
            root[r2] = r1;
        }
        // 此时表示拆掉了secondEdge 边之后图就变成树了
        return secondEdge;
    }

    private int getRoot(int[] root, int i) {
        while (root[i] != i) {
            i = root[i] = root[root[i]];
        }
        return i;
    }

    /**
     * Solution 中给出的另一种不使用 union find 的方法, 这个比较慢.
     * 对图的分析和上面还是一样的, 联通性测试方法则是直接遍历.
     * 这个解法利用了OJ 实际上只需要删除环中的节点变成无环图即可, 不需要是单个根的树.
     */
    @Answer
    public int[] findRedundantDirectedConnection2(int[][] edges) {
        final int n = edges.length;
        int[] parent = new int[n + 1];
        int[] firstEdge = null, secondEdge = null;
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            if (parent[to] > 0) {
                firstEdge = new int[]{parent[to], to};
                secondEdge = edge;
            } else {
                parent[to] = from;
            }
        }

        // 找出根节点, 对于情况2或3 也可能是环路中的任意一个点
        int root = 1;
        boolean[] visited = new boolean[n + 1];
        while (parent[root] > 0 && !visited[root]) {
            visited[root] = true;
            root = parent[root];
        }

        // 没有入度为2 的点, 对应情况2, 破坏环路中的一个边即可.
        if (firstEdge == null) {
            for (int i = n - 1; ; i--) {
                int[] edge = edges[i];
                if (visited[edge[0]] && visited[edge[1]]) {
                    return edge;
                }
            }
        }

        // 重新以parent 构建连通图 (排除了secondEdge)
        boolean[][] links = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            links[parent[i]][i] = true;
        }
        // 以root 为中心开始从上往下遍历所有可达节点.
        Arrays.fill(visited, false);
        visited[0] = true;
        Stack<Integer> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visited[node]) {
                continue;
            }
            visited[node] = true;
            for (int i = 1; i <= n; i++) {
                if (links[node][i]) {
                    stack.push(i);
                }
            }
        }

        // 如果secondEdge 是第三种情况的 1-> 2 边, 则会出现无法到达的点.
        for (boolean b : visited) {
            if (!b) {
                return firstEdge;
            }
        }
        // 其他情况
        return secondEdge;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2}, {1, 3}, {2, 3}
    }).expect(new int[]{2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}, {1, 5}})
            .expect(new int[]{4, 1});

    // 相比example2 是边3->4 的位置移动到后面去了
    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {4, 1}, {1, 5}, {3, 4}})
            .expect(new int[]{3, 4});

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
            {1, 2}, {2, 3}, {3, 4}, {4, 2}
    }).expect(new int[]{4, 2});

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[][]{
            {2, 1}, {3, 1}, {4, 2}, {1, 4}
    }).expect(new int[]{2, 1});

}
