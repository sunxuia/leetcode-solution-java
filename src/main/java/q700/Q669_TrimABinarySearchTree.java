package q700;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/trim-a-binary-search-tree/
 *
 * Given a binary search tree and the lowest and highest boundaries as L and R, trim the tree so that all its
 * elements lies in [L, R] (R >= L). You might need to change the root of the tree, so the result should return the
 * new root of the trimmed binary search tree.
 *
 * Example 1:
 *
 * Input:
 * >     1
 * >    / \
 * >   0   2
 *
 * L = 1
 * R = 2
 *
 * Output:
 * >     1
 * >       \
 * >        2
 *
 * Example 2:
 *
 * Input:
 * >     3
 * >    / \
 * >   0   4
 * >    \
 * >     2
 * >    /
 * >   1
 *
 * L = 1
 * R = 3
 *
 * Output:
 * >       3
 * >      /
 * >    2
 * >   /
 * >  1
 */
@RunWith(LeetCodeRunner.class)
public class Q669_TrimABinarySearchTree {

    @Answer
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return null;
        }
        if (root.val < L) {
            return trimBST(root.right, L, R);
        }
        if (R < root.val) {
            return trimBST(root.left, L, R);
        }
        root.left = trimBST(root.left, L, R);
        root.right = trimBST(root.right, L, R);
        return root;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 0, 2), 1, 2)
            .expect(TreeNode.createByLevel(1, null, 2));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(3, 0, 4, null, 2, null, null, 1), 1, 3)
            .expect(TreeNode.createByPreOrderTraversal(3, 2, 1));

}
