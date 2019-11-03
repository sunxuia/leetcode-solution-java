package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/minimum-depth-of-binary-tree/
 *
 * Given a binary tree, find its minimum depth.
 *
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given binary tree [3,9,20,null,null,15,7],
 *
 * >    3
 * >   / \
 * >  9  20
 * >    /  \
 * >   15   7
 *
 * return its minimum depth = 2.
 */
@RunWith(LeetCodeRunner.class)
public class Q111_MinimumDepthOfBinaryTree {

    /**
     * 注意这个深度求的是叶子节点到根节点的深度
     */
    @Answer
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        if (left * right == 0) {
            return 1 + Math.max(left, right);
        } else {
            return 1 + Math.min(left, right);
        }
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7))
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2))
            .expect(2);

}
