package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 2049. Count Nodes With the Highest Score
 * https://leetcode.com/problems/count-nodes-with-the-highest-score/
 *
 * There is a binary tree rooted at 0 consisting of n nodes. The nodes are labeled from 0 to n - 1. You are given a
 * 0-indexed integer array parents representing the tree, where parents[i] is the parent of node i. Since node 0 is the
 * root, parents[0] == -1.
 *
 * Each node has a score. To find the score of a node, consider if the node and the edges connected to it were removed.
 * The tree would become one or more non-empty subtrees. The size of a subtree is the number of the nodes in it. The
 * score of the node is the product of the sizes of all those subtrees.
 *
 * Return the number of nodes that have the highest score.
 *
 * Example 1:
 * (图Q2049_PIC1.png)
 * Input: parents = [-1,2,0,2,0]
 * Output: 3
 * Explanation:
 * - The score of node 0 is: 3 * 1 = 3
 * - The score of node 1 is: 4 = 4
 * - The score of node 2 is: 1 * 1 * 2 = 2
 * - The score of node 3 is: 4 = 4
 * - The score of node 4 is: 4 = 4
 * The highest score is 4, and three nodes (node 1, node 3, and node 4) have the highest score.
 *
 * Example 2:
 * (图Q2049_PIC2.png)
 * Input: parents = [-1,2,0]
 * Output: 2
 * Explanation:
 * - The score of node 0 is: 2 = 2
 * - The score of node 1 is: 2 = 2
 * - The score of node 2 is: 1 * 1 = 1
 * The highest score is 2, and two nodes (node 0 and node 1) have the highest score.
 *
 * Constraints:
 *
 * n == parents.length
 * 2 <= n <= 10^5
 * parents[0] == -1
 * 0 <= parents[i] <= n - 1 for i != 0
 * parents represents a valid binary tree.
 */
@RunWith(LeetCodeRunner.class)
public class Q2049_CountNodesWithTheHighestScore {

    @Answer
    public int countHighestScoreNodes(int[] parents) {
        final int n = parents.length;
        // 构造树
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 1; i < n; i++) {
            if (nodes[parents[i]].a == null) {
                nodes[parents[i]].a = nodes[i];
            } else {
                nodes[parents[i]].b = nodes[i];
            }
        }
        nodes[0].initialSize();

        // 找出结果
        int res = 0;
        long max = 0;
        for (Node node : nodes) {
            long product = 1;
            if (node != nodes[0]) {
                product = nodes[0].size - node.size;
            }
            if (node.a != null) {
                product *= node.a.size;
            }
            if (node.b != null) {
                product *= node.b.size;
            }
            if (product > max) {
                res = 1;
                max = product;
            } else if (product == max) {
                res++;
            }
        }
        return res;
    }

    private static class Node {

        Node a, b;

        int size;

        void initialSize() {
            size = 1;
            if (a != null) {
                a.initialSize();
                size += a.size;
            }
            if (b != null) {
                b.initialSize();
                size += b.size;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{-1, 2, 0, 2, 0}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{-1, 2, 0}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(TestDataFileHelper.read(int[].class)).expect(1);

}
