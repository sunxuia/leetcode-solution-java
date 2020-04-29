package q550;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/diameter-of-binary-tree/
 *
 * Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is
 * the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
 *
 * Example:
 * Given a binary tree
 *
 * >           1
 * >          / \
 * >         2   3
 * >        / \
 * >       4   5
 *
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 *
 * Note: The length of path between two nodes is represented by the number of edges between them.
 */
@RunWith(LeetCodeRunner.class)
public class Q543_DiameterOfBinaryTree {

    @Answer
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // diameter 的值等于元素数量-1
        return dfs(root)[0] - 1;
    }

    // 返回数组的值分别是 最大长度的元素数量和最大深度
    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        int num = Math.max(left[0], right[0]);
        num = Math.max(num, left[1] + right[1] + 1);
        int depth = Math.max(left[1], right[1]) + 1;
        return new int[]{num, depth};
    }

    @TestData
    public DataExpectation example = DataExpectation.create(TreeNode.createByLevel(1, 2, 3, 4, 5)).expect(3);

    @TestData
    public DataExpectation border = DataExpectation.create(null).expect(0);

}
