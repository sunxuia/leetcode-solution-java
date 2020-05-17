package q750;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/insert-into-a-binary-search-tree/
 *
 * Given the root node of a binary search tree (BST) and a value to be inserted into the tree, insert the value into
 * the BST. Return the root node of the BST after the insertion. It is guaranteed that the new value does not exist
 * in the original BST.
 *
 * Note that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion
 * . You can return any of them.
 *
 * For example,
 *
 * Given the tree:
 * >         4
 * >        / \
 * >       2   7
 * >      / \
 * >     1   3
 * And the value to insert: 5
 *
 * You can return this binary search tree:
 *
 * >          4
 * >        /   \
 * >       2     7
 * >      / \   /
 * >     1   3 5
 *
 * This tree is also valid:
 *
 * >          5
 * >        /   \
 * >       2     7
 * >      / \
 * >     1   3
 * >          \
 * >           4
 *
 *
 *
 * Constraints:
 *
 * The number of nodes in the given tree will be between 0 and 10^4.
 * Each node will have a unique integer value from 0 to -10^8, inclusive.
 * -10^8 <= val <= 10^8
 * It's guaranteed that val does not exist in the original BST.
 */
@RunWith(LeetCodeRunner.class)
public class Q701_InsertIntoABinarySearchTree {

    @Answer
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (root.val < val) {
            root.right = insertIntoBST(root.right, val);
        } else {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

    // 循环方式的解法
    @Answer
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode node = root;
        while (true) {
            if (node.val < val) {
                if (node.right == null) {
                    node.right = new TreeNode(val);
                    return root;
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    node.left = new TreeNode(val);
                    return root;
                } else {
                    node = node.left;
                }
            }
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(4, 2, 7, 1, 3))
            .addArgument(5)
            .expect(TreeNode.createByLevel(4, 2, 7, 1, 3, 5))
            .orExpect(TreeNode.createByLevel(5, 2, 7, 1, 3, null, null, null, null, null, 4))
            .build();

}
