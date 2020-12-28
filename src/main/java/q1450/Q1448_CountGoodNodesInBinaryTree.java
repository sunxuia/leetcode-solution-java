package q1450;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1448. Count Good Nodes in Binary Tree
 * https://leetcode.com/problems/count-good-nodes-in-binary-tree/
 *
 * Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a
 * value greater than X.
 *
 * Return the number of good nodes in the binary tree.
 *
 * Example 1:
 * <img src="./Q1448_PIC1.png">
 * Input: root = [3,1,4,3,null,1,5]
 * Output: 4
 * Explanation: Nodes in blue are good.
 * Root Node (3) is always a good node.
 * Node 4 -> (3,4) is the maximum value in the path starting from the root.
 * Node 5 -> (3,4,5) is the maximum value in the path
 * Node 3 -> (3,1,3) is the maximum value in the path.
 *
 * Example 2:
 * <img src="./Q1448_PIC2.png">
 * Input: root = [3,3,null,4,2]
 * Output: 3
 * Explanation: Node 2 -> (3, 3, 2) is not good, because "3" is higher than it.
 *
 * Example 3:
 *
 * Input: root = [1]
 * Output: 1
 * Explanation: Root is considered as good.
 *
 * Constraints:
 *
 * The number of nodes in the binary tree is in the range [1, 10^5].
 * Each node's value is between [-10^4, 10^4].
 */
@RunWith(LeetCodeRunner.class)
public class Q1448_CountGoodNodesInBinaryTree {

    @Answer
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }

    private int dfs(TreeNode node, int max) {
        if (node == null) {
            return 0;
        }
        if (node.val >= max) {
            return 1 + dfs(node.left, node.val)
                    + dfs(node.right, node.val);
        } else {
            return dfs(node.left, max)
                    + dfs(node.right, max);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(3, 1, 4, 3, null, 1, 5))
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(3, 3, null, 4, 2))
            .expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(1))
            .expect(1);

}
