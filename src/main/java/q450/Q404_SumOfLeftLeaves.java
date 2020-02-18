package q450;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sum-of-left-leaves/
 *
 * Find the sum of all left leaves in a given binary tree.
 *
 * Example:
 *
 * >      3
 * >     / \
 * >    9  20
 * >      /  \
 * >     15   7
 *
 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
 */
@RunWith(LeetCodeRunner.class)
public class Q404_SumOfLeftLeaves {

    @Answer
    public int sumOfLeftLeaves(TreeNode root) {
        return root == null ? 0 : dfs(root, false);
    }

    private int dfs(TreeNode node, boolean isLeft) {
        if (node.left == null && node.right == null) {
            return isLeft ? node.val : 0;
        }
        int res = 0;
        if (node.left != null) {
            res = dfs(node.left, true);
        }
        if (node.right != null) {
            res += dfs(node.right, false);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(3, 9, 20, null, null, 15, 7))
            .expect(24);

}
