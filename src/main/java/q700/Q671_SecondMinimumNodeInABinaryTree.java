package q700;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/
 *
 * Given a non-empty special binary tree consisting of nodes with the non-negative value, where each node in this
 * tree has exactly two or zero sub-node. If the node has two sub-nodes, then this node's value is the smaller value
 * among its two sub-nodes. More formally, the property root.val = min(root.left.val, root.right.val) always holds.
 *
 * Given such a binary tree, you need to output the second minimum value in the set made of all the nodes' value in
 * the whole tree.
 *
 * If no such second minimum value exists, output -1 instead.
 *
 * Example 1:
 *
 * Input:
 * >     2
 * >    / \
 * >   2   5
 * >      / \
 * >     5   7
 *
 * Output: 5
 * Explanation: The smallest value is 2, the second smallest value is 5.
 *
 *
 *
 * Example 2:
 *
 * Input:
 * >     2
 * >    / \
 * >   2   2
 *
 * Output: -1
 * Explanation: The smallest value is 2, but there isn't any second smallest value.
 */
@RunWith(LeetCodeRunner.class)
public class Q671_SecondMinimumNodeInABinaryTree {

    @Answer
    public int findSecondMinimumValue(TreeNode root) {
        return dfs(root, root.val);
    }

    private int dfs(TreeNode node, int val) {
        if (node == null) {
            return -1;
        }
        if (node.val > val) {
            return node.val;
        }
        int left = dfs(node.left, val);
        int right = dfs(node.right, val);
        return left == -1 || right == -1
                ? Math.max(left, right)
                : Math.min(left, right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(2, 2, 5, null, null, 5, 7)).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(TreeNode.createByLevel(2, 2, 2)).expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(TreeNode.createByLevel(5, 8, 5)).expect(8);

}
