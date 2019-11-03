package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/balanced-binary-tree/
 *
 * Given a binary tree, determine if it is height-balanced.
 *
 * For this problem, a height-balanced binary tree is defined as:
 *
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 *
 *
 *
 * Example 1:
 *
 * Given the following tree [3,9,20,null,null,15,7]:
 *
 * >    3
 * >   / \
 * >  9  20
 * >    /  \
 * >   15   7
 *
 * Return true.
 *
 * Example 2:
 *
 * Given the following tree [1,2,2,3,3,null,null,4,4]:
 *
 * >       1
 * >      / \
 * >     2   2
 * >    / \
 * >   3   3
 * >  / \
 * > 4   4
 *
 * Return false.
 */
@RunWith(LeetCodeRunner.class)
public class Q110_BalancedBinaryTree {

    @Answer
    public boolean isBalanced(TreeNode root) {
        return dfs(root) >= 0;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }
        return 1 + Math.max(left, right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, 3, 4, null, null, 4, null, null, 3, null, null, 2))
            .expect(false);

    @TestData
    public DataExpectation border = DataExpectation
            .create(TreeNode.createByPreOrderTraversal())
            .expect(true);

    /**
     *               1
     *            /    \
     *          2       2
     *        /  \     /  \
     *       3    3   3    3
     *      /\   /\  /\
     *     4 4  4 4 4 4
     *    /\
     *   5  5
     *
     *   LeetCode 认为这个是平衡二叉树
     */
    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode
                    .createByPreOrderTraversal(1, 2, 3, 4, 5, null, null, 5, null, null, 4, null, null, 3, 4, null,
                            null, 4, null, null, 2, 3, 4, null, null, 4, null, null, 3))
            .expect(true);

}
