package q1800;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1766. Tree of Coprimes
 * https://leetcode.com/problems/tree-of-coprimes/
 *
 * There is a tree (i.e., a connected, undirected graph that has no cycles) consisting of n nodes numbered from 0 to n -
 * 1 and exactly n - 1 edges. Each node has a value associated with it, and the root of the tree is node 0.
 *
 * To represent this tree, you are given an integer array nums and a 2D array edges. Each nums[i] represents the ith
 * node's value, and each edges[j] = [uj, vj] represents an edge between nodes uj and vj in the tree.
 *
 * Two values x and y are coprime if gcd(x, y) == 1 where gcd(x, y) is the greatest common divisor of x and y.
 *
 * An ancestor of a node i is any other node on the shortest path from node i to the root. A node is not considered an
 * ancestor of itself.
 *
 * Return an array ans of size n, where ans[i] is the closest ancestor to node i such that nums[i] and nums[ans[i]] are
 * coprime, or -1 if there is no such ancestor.
 *
 * Example 1:
 * <img src="./Q1766_PIC1.png">
 * Input: nums = [2,3,3,2], edges = [[0,1],[1,2],[1,3]]
 * Output: [-1,0,0,1]
 * Explanation: In the above figure, each node's value is in parentheses.
 * - Node 0 has no coprime ancestors.
 * - Node 1 has only one ancestor, node 0. Their values are coprime (gcd(2,3) == 1).
 * - Node 2 has two ancestors, nodes 1 and 0. Node 1's value is not coprime (gcd(3,3) == 3), but node 0's
 * value is (gcd(2,3) == 1), so node 0 is the closest valid ancestor.
 * - Node 3 has two ancestors, nodes 1 and 0. It is coprime with node 1 (gcd(3,2) == 1), so node 1 is its
 * closest valid ancestor.
 *
 * Example 2:
 * <img src="./Q1766_PIC2.png">
 * Input: nums = [5,6,10,2,3,6,15], edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]]
 * Output: [-1,0,-1,0,0,0,-1]
 *
 * Constraints:
 *
 * nums.length == n
 * 1 <= nums[i] <= 50
 * 1 <= n <= 10^5
 * edges.length == n - 1
 * edges[j].length == 2
 * 0 <= uj, vj < n
 * uj != vj
 */
@RunWith(LeetCodeRunner.class)
public class Q1766_TreeOfCoprimes {

    @Answer
    public int[] getCoprimes(int[] nums, int[][] edges) {
        final int n = nums.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            nodes[i].no = i;
            nodes[i].num = nums[i];
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            nodes[u].neighbors.add(nodes[v]);
            nodes[v].neighbors.add(nodes[u]);
        }

        int[] res = new int[n];
        Arrays.fill(res, -1);
        dfs(res, nodes[0], new Node[LIMIT]);
        return res;
    }

    private static class Node {

        /**
         * no: 节点的编号;
         * num: 节点的数字;
         * depth: 节点的深度(0节点深度为0);
         */
        int no, num, depth;

        // 节点的邻居, 遍历的时候会把父节点删掉.
        Set<Node> neighbors = new HashSet<>();
    }

    private void dfs(int[] res, Node node, Node[] numNodes) {
        // 遍历与当前节点互质的数字, 找出最深节点
        int depth = -1;
        for (int num : COPRIMES[node.num]) {
            Node prev = numNodes[num];
            if (prev != null && prev.depth > depth) {
                res[node.no] = prev.no;
                depth = prev.depth;
            }
        }
        // 替换数字对应的最深节点
        Node prev = numNodes[node.num];
        numNodes[node.num] = node;

        for (Node child : node.neighbors) {
            child.depth = node.depth + 1;
            child.neighbors.remove(node);
            dfs(res, child, numNodes);
        }
        // 还原数字对应的最深节点
        numNodes[node.num] = prev;
    }

    private static final int LIMIT = 51;

    /**
     * COPRIMES[num] 表示 [1, LIMIT] 中与num 互质的数字
     */
    private static final int[][] COPRIMES = new int[LIMIT][];

    static {
        int[] cache = new int[LIMIT];
        for (int i = 1; i < LIMIT; i++) {
            int len = 0;
            for (int j = 1; j < LIMIT; j++) {
                if (gcd(i, j) == 1) {
                    cache[len++] = j;
                }
            }
            COPRIMES[i] = new int[len];
            System.arraycopy(cache, 0, COPRIMES[i], 0, len);
        }
    }

    private static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 3, 3, 2}, new int[][]{{0, 1}, {1, 2}, {1, 3}})
            .expect(new int[]{-1, 0, 0, 1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 6, 10, 2, 3, 6, 15}, new int[][]{{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}})
            .expect(new int[]{-1, 0, -1, 0, 0, 0, -1});

    private TestDataFile testDataFile = new TestDataFile();

    /**
     * 10万个节点
     */
    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
            TestDataFileHelper.read(testDataFile, 1, int[].class),
            TestDataFileHelper.read(testDataFile, 2, int[][].class)
    ).expect(TestDataFileHelper.read(testDataFile, 3, int[].class));

}
