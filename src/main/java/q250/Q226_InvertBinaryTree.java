package q250;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/invert-binary-tree/
 *
 * Invert a binary tree.
 *
 * Example:
 *
 * Input:
 *
 * >       4
 * >     /   \
 * >    2     7
 * >   / \   / \
 * >  1   3 6   9
 *
 * Output:
 *
 * >       4
 * >     /   \
 * >    7     2
 * >   / \   / \
 * >  9   6 3   1
 *
 * Trivia:
 * This problem was inspired by this original tweet by Max Howell:
 *
 * Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a
 * whiteboard so f*** off.
 */
@RunWith(LeetCodeRunner.class)
public class Q226_InvertBinaryTree {

    @Answer
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode left = root.left;
            root.left = invertTree(root.right);
            root.right = invertTree(left);
        }
        return root;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(4, 2, 7, 1, 3, 6, 9))
            .expect(TreeNode.createByLevel(4, 7, 2, 9, 6, 3, 1));

}
