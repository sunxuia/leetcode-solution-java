package q100;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/validate-binary-search-tree/
 *
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 *
 *
 * Example 1:
 *
 * >    2
 * >   / \
 * >  1   3
 *
 * Input: [2,1,3]
 * Output: true
 *
 * Example 2:
 *
 * >    5
 * >   / \
 * >  1   4
 * >     / \
 * >    3   6
 *
 * Input: [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 */
@RunWith(LeetCodeRunner.class)
public class Q098_ValidateBinarySearchTree {

    @Answer
    public boolean isValidBST(TreeNode root) {
        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean dfs(TreeNode node, long lowerLimit, long upperLimit) {
        if (node == null) {
            return true;
        }
        if (node.val <= lowerLimit || upperLimit <= node.val) {
            return false;
        }
        return dfs(node.left, lowerLimit, node.val)
                && dfs(node.right, node.val, upperLimit);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(2, 1, null, null, 3))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(5, 1, null, null, 4, 3, null, null, 6))
            .expect(false);

    @TestData
    public DataExpectation border = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal())
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(1, 1))
            .expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(10, 5, null, null, 15, 6, null, null, 20))
            .expect(false);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(Integer.MAX_VALUE))
            .expect(true);

}
