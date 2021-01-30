package q1550;

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
 * [Medium] 1519. Number of Nodes in the Sub-Tree With the Same Label
 * https://leetcode.com/problems/number-of-nodes-in-the-sub-tree-with-the-same-label/
 *
 * Given a tree (i.e. a connected, undirected graph that has no cycles) consisting of n nodes numbered from 0 to n - 1
 * and exactly n - 1 edges. The root of the tree is the node 0, and each node of the tree has a label which is a
 * lower-case character given in the string labels (i.e. The node with the number i has the label labels[i]).
 *
 * The edges array is given on the form edges[i] = [ai, bi], which means there is an edge between nodes ai and bi in the
 * tree.
 *
 * Return an array of size n where ans[i] is the number of nodes in the subtree of the ith node which have the same
 * label as node i.
 *
 * A subtree of a tree T is the tree consisting of a node in T and all of its descendant nodes.
 *
 * Example 1:
 * <img src="./Q1519_PIC1.png">
 * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], labels = "abaedcd"
 * Output: [2,1,1,1,1,1,1]
 * Explanation: Node 0 has label 'a' and its sub-tree has node 2 with label 'a' as well, thus the answer is 2. Notice
 * that any node is part of its sub-tree.
 * Node 1 has a label 'b'. The sub-tree of node 1 contains nodes 1,4 and 5, as nodes 4 and 5 have different labels than
 * node 1, the answer is just 1 (the node itself).
 *
 * Example 2:
 * <img src="./Q1519_PIC2.png">
 * Input: n = 4, edges = [[0,1],[1,2],[0,3]], labels = "bbbb"
 * Output: [4,2,1,1]
 * Explanation: The sub-tree of node 2 contains only node 2, so the answer is 1.
 * The sub-tree of node 3 contains only node 3, so the answer is 1.
 * The sub-tree of node 1 contains nodes 1 and 2, both have label 'b', thus the answer is 2.
 * The sub-tree of node 0 contains nodes 0, 1, 2 and 3, all with label 'b', thus the answer is 4.
 *
 * Example 3:
 * <img src="./Q1519_PIC3.png">
 * Input: n = 5, edges = [[0,1],[0,2],[1,3],[0,4]], labels = "aabab"
 * Output: [3,2,1,1,1]
 *
 * Example 4:
 *
 * Input: n = 6, edges = [[0,1],[0,2],[1,3],[3,4],[4,5]], labels = "cbabaa"
 * Output: [1,2,1,1,2,1]
 *
 * Example 5:
 *
 * Input: n = 7, edges = [[0,1],[1,2],[2,3],[3,4],[4,5],[5,6]], labels = "aaabaaa"
 * Output: [6,5,4,1,3,2,1]
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * ai != bi
 * labels.length == n
 * labels is consisting of only of lower-case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1519_NumberOfNodesInTheSubTreeWithTheSameLabel {

    @Answer
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            nodes[i].c = labels.charAt(i) - 'a';
            nodes[i].idx = i;
        }
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            nodes[from].children.add(nodes[to]);
            nodes[to].children.add(nodes[from]);
        }

        int[] res = new int[n];
        List<Integer>[] parents = new List[26];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = new ArrayList<>();
        }
        dfs(res, nodes[0]);
        return res;
    }

    private static class Node {

        List<Node> children = new ArrayList<>();

        int c, idx;

        boolean visited;

    }

    private int[] dfs(int[] counts, Node node) {
        node.visited = true;
        int[] res = new int[26];
        for (Node child : node.children) {
            if (!child.visited) {
                int[] arr = dfs(counts, child);
                for (int i = 0; i < 26; i++) {
                    res[i] += arr[i];
                }
            }
        }
        res[node.c]++;
        counts[node.idx] = res[node.c];
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(7, new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}}, "abaedcd")
            .expect(new int[]{2, 1, 1, 1, 1, 1, 1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(4, new int[][]{{0, 1}, {1, 2}, {0, 3}}, "bbbb")
            .expect(new int[]{4, 2, 1, 1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(5, new int[][]{{0, 1}, {0, 2}, {1, 3}, {0, 4}}, "aabab")
            .expect(new int[]{3, 2, 1, 1, 1});

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(6, new int[][]{{0, 1}, {0, 2}, {1, 3}, {3, 4}, {4, 5}}, "cbabaa")
            .expect(new int[]{1, 2, 1, 1, 2, 1});

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(7, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}}, "aaabaaa")
            .expect(new int[]{6, 5, 4, 1, 3, 2, 1});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(4, new int[][]{{0, 2}, {0, 3}, {1, 2}}, "aeed")
            .expect(new int[]{1, 1, 2, 1});

    private TestDataFile testDataFile = new TestDataFile();

    /**
     * 100000 个节点, 99999 条边, 是从节点 0 ~ 99999 的一条链表.
     */
    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(100000,
                    TestDataFileHelper.read(testDataFile, 1, int[][].class),
                    TestDataFileHelper.read(testDataFile, 2, String.class))
            .expect(TestDataFileHelper.read(testDataFile, 3, int[].class));

}
