package q600;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-tree-tilt/
 *
 * Given a binary tree, return the tilt of the whole tree.
 *
 * The tilt of a tree node is defined as the absolute difference between the sum of all left subtree node values and
 * the sum of all right subtree node values. Null node has tilt 0.
 *
 * The tilt of the whole tree is defined as the sum of all nodes' tilt.
 *
 * Example:
 *
 * Input:
 * >          1
 * >        /   \
 * >       2     3
 * Output: 1
 * Explanation:
 * Tilt of node 2 : 0
 * Tilt of node 3 : 0
 * Tilt of node 1 : |2-3| = 1
 * Tilt of binary tree : 0 + 0 + 1 = 1
 *
 * Note:
 *
 * 1. The sum of node values in any subtree won't exceed the range of 32-bit integer.
 * 2. All the tilt values won't exceed the range of 32-bit integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q563_BinaryTreeTilt {

    @Answer
    public int findTilt(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return sum(root)[1];
    }

    private int[] sum(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] left = sum(node.left);
        int[] right = sum(node.right);
        int sum = node.val + left[0] + right[0];
        int tilt = Math.abs(left[0] - right[0]) + left[1] + right[1];
        return new int[]{sum, tilt};
    }

    @TestData
    public DataExpectation example = DataExpectation.create(TreeNode.createByLevel(1, 2, 3)).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(TreeNode.createByLevel(1, 2, 3, 4, null, 5)).expect(11);

}
